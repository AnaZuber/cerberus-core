/* Cerberus Copyright (C) 2013 - 2017 cerberustesting
DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

This file is part of Cerberus.

Cerberus is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Cerberus is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.cerberus.crud.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cerberus.crud.entity.ScheduleEntry;
import org.cerberus.util.answer.AnswerItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.cerberus.crud.dao.IScheduleEntryDAO;
import org.cerberus.crud.entity.CountryEnvironmentParameters;
import org.cerberus.crud.service.IScheduleEntryService;
import org.cerberus.engine.entity.MessageEvent;
import org.cerberus.engine.scheduler.SchedulerInit;
import org.cerberus.enums.MessageEventEnum;
import org.cerberus.util.answer.Answer;
import org.cerberus.util.answer.AnswerUtil;

/**
 *
 * @author cdelage
 */
@Service
public class ScheduleEntryService implements IScheduleEntryService {

    @Autowired
    IScheduleEntryDAO schedulerDao;
    private static final Logger LOG = LogManager.getLogger(ScheduleEntryService.class);

    @Override
    public AnswerItem<ScheduleEntry> readbykey(Integer id) {
        AnswerItem<ScheduleEntry> ans = new AnswerItem();
        ans = schedulerDao.readByKey(id);
        return ans;
    }

    @Override
    public AnswerItem<List> readAllActive() {
        AnswerItem<List> ans = new AnswerItem();
        ans = schedulerDao.readAllActive();
        return ans;
    }

    @Override
    public AnswerItem<Integer> create(ScheduleEntry scheduleentry) {
        LOG.debug("scheduleentryservice.create");
        AnswerItem<Integer> response = new AnswerItem();
        response = schedulerDao.create(scheduleentry);
        return response;
    }

    @Override
    public Answer update(ScheduleEntry scheduleentry) {
        Answer response = new Answer();
        Boolean validCron = org.quartz.CronExpression.isValidExpression(scheduleentry.getCronDefinition());
        if (scheduleentry.getName().isEmpty() || scheduleentry.getCronDefinition().isEmpty() || !validCron) {
            MessageEvent msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
            msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", "Unvalid SchedulerEntry data"));
            response.setResultMessage(msg);
        } else {
            response = schedulerDao.update(scheduleentry);

        }

        return response;
    }

    @Override
    public Answer delete(ScheduleEntry object) {
        Answer response = new Answer();
        response = schedulerDao.delete(object);
        return response;
    }

    ;
    
    @Override
    public AnswerItem<List> readByName(String name) {
        AnswerItem<List> response = new AnswerItem();
        response = schedulerDao.readByName(name);
        return response;
    }

    @Override
    public Answer deleteListSched(List<ScheduleEntry> objectList) {
        Answer ans = new Answer(null);
        for (ScheduleEntry objectToDelete : objectList) {
            LOG.debug("object to delete" + objectToDelete.getID());
            ans = schedulerDao.delete(objectToDelete);
        }
        return ans;
    }

    @Override
    public Answer deleteByCampaignName(String name) {
        Answer ans = new Answer(null);
        List<ScheduleEntry> objectList = new ArrayList<ScheduleEntry>();
        objectList = this.readByName(name).getItem();
        for (ScheduleEntry objectToDelete : objectList){
            ans = this.delete(objectToDelete);
            if(!ans.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode()) ){
            return ans;
            }
        }
        return ans;
    }

    @Override
    public Answer createListSched(List<ScheduleEntry> objectList) {
        Answer ans = new Answer(null);
        LOG.debug("createListSched" + objectList);
        if (objectList.isEmpty()) {
            MessageEvent msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
            msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", "Unvalid SchedulerEntry data"));
            ans.setResultMessage(msg);
            return ans;
        } else {
            for (ScheduleEntry objectToCreate : objectList) {
                Boolean validCron = org.quartz.CronExpression.isValidExpression(objectToCreate.getCronDefinition());
                if (objectToCreate.getName().isEmpty() || objectToCreate.getCronDefinition().isEmpty() || !validCron) {
                    MessageEvent msg = new MessageEvent(MessageEventEnum.DATA_OPERATION_ERROR_UNEXPECTED);
                    msg.setDescription(msg.getDescription().replace("%DESCRIPTION%", "Unvalid SchedulerEntry data"));
                    ans.setResultMessage(msg);
                    return ans;
                } else {
                    LOG.debug("ScheduleEntry Created for : " + objectToCreate.getName() + "with Cron expression : " + objectToCreate.getCronDefinition());
                    ans = schedulerDao.create(objectToCreate);
                }
            }
        }
        return ans;
    }

    @Override
    public Answer compareSchedListAndUpdateInsertDeleteElements(String campaign, List<ScheduleEntry> newList) {
        Answer ans = new Answer();

        MessageEvent msg1 = new MessageEvent(MessageEventEnum.GENERIC_OK);
        Answer finalAnswer = new Answer(msg1);

        List<ScheduleEntry> oldList = new ArrayList();
        oldList = schedulerDao.readByName(campaign).getItem();
        List<ScheduleEntry> listToUpdateOrInsert = new ArrayList<>(newList);
        listToUpdateOrInsert.removeAll(oldList);
        List<ScheduleEntry> listToUpdateOrInsertToIterate = new ArrayList<>(listToUpdateOrInsert);

        /**
         * Update and Create all objects database Objects from newList
         */
        for (ScheduleEntry objectDifference : listToUpdateOrInsertToIterate) {
            for (ScheduleEntry objectInDatabase : oldList) {
                if (objectDifference.schedHasSameKey(objectInDatabase)) {
                    ans = this.update(objectDifference);
                    finalAnswer = AnswerUtil.agregateAnswer(finalAnswer, (Answer) ans);
                    listToUpdateOrInsert.remove(objectDifference);

                }
            }
        }

        /**
         * Delete all objects database Objects that do not exist from newList
         */
        List<ScheduleEntry> listToDelete = new ArrayList<>(oldList);
        listToDelete.removeAll(newList);
        List<ScheduleEntry> listToDeleteToIterate = new ArrayList<>(listToDelete);

        for (ScheduleEntry scheDifference : listToDeleteToIterate) {
            for (ScheduleEntry scheInPage : newList) {
                if (scheDifference.schedHasSameKey(scheInPage)) {
                    listToDelete.remove(scheDifference);
                }
            }
        }
        if (!listToDelete.isEmpty()) {
            ans = this.deleteListSched(listToDelete);
            finalAnswer = AnswerUtil.agregateAnswer(finalAnswer, (Answer) ans);
        }

        // We insert only at the end (after deletion of all potencial enreg)
        if (!listToUpdateOrInsert.isEmpty()) {
            ans = this.createListSched(listToUpdateOrInsert);
            finalAnswer = AnswerUtil.agregateAnswer(finalAnswer, (Answer) ans);
        }
        return finalAnswer;
    }
    
    @Override
    public Answer updateLastExecution(Integer schedulerId, Timestamp lastExecution){
    Answer ans = new Answer();
    ans = schedulerDao.updateLastExecution(schedulerId, lastExecution);
    return ans;
    }

}
