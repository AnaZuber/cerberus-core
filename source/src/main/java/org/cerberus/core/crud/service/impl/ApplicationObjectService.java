/**
 * Cerberus Copyright (C) 2013 - 2017 cerberustesting
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This file is part of Cerberus.
 *
 * Cerberus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cerberus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.cerberus.core.crud.service.impl;

import org.apache.commons.fileupload.FileItem;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.cerberus.core.crud.dao.IApplicationObjectDAO;
import org.cerberus.core.crud.entity.ApplicationObject;
import org.cerberus.core.crud.service.IApplicationObjectService;
import org.cerberus.core.util.answer.Answer;
import org.cerberus.core.util.answer.AnswerItem;
import org.cerberus.core.util.answer.AnswerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import org.cerberus.core.crud.service.ITestCaseCountryPropertiesService;
import org.cerberus.core.crud.service.ITestCaseService;
import org.cerberus.core.crud.service.ITestCaseStepActionControlService;
import org.cerberus.core.crud.service.ITestCaseStepActionService;
import org.cerberus.core.crud.service.ITestCaseStepService;
import org.cerberus.core.enums.MessageEventEnum;

/**
 *
 * @author foudro
 */
@Service
public class ApplicationObjectService implements IApplicationObjectService {

    @Autowired
    private IApplicationObjectDAO ApplicationObjectDAO;
    @Autowired
    private ITestCaseStepActionService actionService;
    @Autowired
    private ITestCaseStepActionControlService controlService;
    @Autowired
    private ITestCaseStepService stepService;
    @Autowired
    private ITestCaseService testcaseService;
    @Autowired
    private ITestCaseCountryPropertiesService propertiesService;

    private static final Logger LOG = LogManager.getLogger("ApplicationObjectService");

    private final String OBJECT_NAME = "ApplicationObject";

    @Override
    public AnswerItem<ApplicationObject> readByKeyTech(int id) {
        return ApplicationObjectDAO.readByKeyTech(id);
    }

    @Override
    public AnswerItem<ApplicationObject> readByKey(String application, String object) {
        return ApplicationObjectDAO.readByKey(application, object);
    }

    @Override
    public AnswerList<ApplicationObject> readByApplication(String Application) {
        return ApplicationObjectDAO.readByApplication(Application);
    }

    @Override
    public Answer uploadFile(int id, FileItem file) {
        return ApplicationObjectDAO.uploadFile(id, file);
    }

    @Override
    public AnswerList<ApplicationObject> readByCriteria(int startPosition, int length, String columnName, String sort, String searchParameter, Map<String, List<String>> individualSearch) {
        return ApplicationObjectDAO.readByCriteria(startPosition, length, columnName, sort, searchParameter, individualSearch);
    }

    @Override
    public AnswerList<ApplicationObject> readByApplicationByCriteria(String application, int startPosition, int length, String columnName, String sort, String searchParameter, Map<String, List<String>> individualSearch, List<String> systems) {
        return ApplicationObjectDAO.readByApplicationByCriteria(application, startPosition, length, columnName, sort, searchParameter, individualSearch, systems);
    }

    @Override
    public BufferedImage readImageByKey(String application, String object) {
        return ApplicationObjectDAO.readImageByKey(application, object);
    }

    @Override
    public Answer create(ApplicationObject object) {
        return ApplicationObjectDAO.create(object);
    }

    @Override
    public Answer delete(ApplicationObject object) {
        return ApplicationObjectDAO.delete(object);
    }

    @Override
    public Answer update(String originalApplication, String originalObject, ApplicationObject object) {
        Answer resp = ApplicationObjectDAO.update(originalApplication, originalObject, object);
        if (resp.isCodeEquals(MessageEventEnum.DATA_OPERATION_OK.getCode())) {
            if (originalObject != null && !originalObject.equals(object.getObject())) {
                actionService.updateApplicationObject(originalApplication, originalObject, object.getObject());
                controlService.updateApplicationObject(originalApplication, originalObject, object.getObject());
                stepService.updateApplicationObject(originalApplication, originalObject, object.getObject());
                testcaseService.updateApplicationObject(originalApplication, originalObject, object.getObject());
                propertiesService.updateApplicationObject(originalApplication, originalObject, object.getObject());
            }
        }
        return resp;
    }

    @Override
    public AnswerList<String> readDistinctValuesByCriteria(String searchParameter, Map<String, List<String>> individualSearch, String columnName) {
        return ApplicationObjectDAO.readDistinctValuesByCriteria(searchParameter, individualSearch, columnName);
    }

    @Override
    public AnswerList<String> readDistinctValuesByApplicationByCriteria(String Application, String searchParameter, Map<String, List<String>> individualSearch, String columnName) {
        return ApplicationObjectDAO.readDistinctValuesByApplicationByCriteria(Application, searchParameter, individualSearch, columnName);
    }
}
