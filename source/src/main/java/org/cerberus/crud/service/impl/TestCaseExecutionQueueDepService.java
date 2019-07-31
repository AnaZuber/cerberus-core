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
package org.cerberus.crud.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cerberus.crud.dao.ITestCaseExecutionQueueDepDAO;
import org.cerberus.crud.entity.TestCaseExecution;
import org.cerberus.crud.entity.TestCaseExecutionQueue;
import org.cerberus.crud.entity.TestCaseExecutionQueueDep;
import org.cerberus.crud.service.ITestCaseExecutionQueueDepService;
import org.cerberus.crud.service.ITestCaseExecutionQueueService;
import org.cerberus.exception.CerberusException;
import org.cerberus.util.answer.AnswerItem;
import org.cerberus.util.answer.AnswerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bcivel
 */
@Service
public class TestCaseExecutionQueueDepService implements ITestCaseExecutionQueueDepService {

    @Autowired
    private ITestCaseExecutionQueueDepDAO testCaseExecutionQueueDepDAO;
    @Autowired
    private ITestCaseExecutionQueueService executionQueueService;

    private static final Logger LOG = LogManager.getLogger("TestCaseExecutionQueueDepService");

    private final String OBJECT_NAME = "Test Case Execution Queue Dependency";

    @Override
    public AnswerItem<Integer> insertFromTCDep(long queueId, String env, String country, String tag, String test, String testcase) {
        return testCaseExecutionQueueDepDAO.insertFromTCDep(queueId, env, country, tag, test, testcase);
    }

    @Override
    public AnswerItem<Integer> insertFromQueueExeDep(long queueId, long fromQueueId) {
        return testCaseExecutionQueueDepDAO.insertFromQueueExeDep(queueId, fromQueueId);
    }

    @Override
    public AnswerItem<Integer> updateStatusToRelease(String env, String Country, String tag, String type, String test, String testCase, String comment, long exeId, long queueId) {
        return testCaseExecutionQueueDepDAO.updateStatusToRelease(env, Country, tag, type, test, testCase, comment, exeId, queueId);
    }

    @Override
    public AnswerList<Long> readExeQueueIdByExeId(long exeId) {
        return testCaseExecutionQueueDepDAO.readExeQueueIdByExeId(exeId);
    }

    @Override
    public AnswerList<Long> readExeQueueIdByQueueId(long queueId) {
        return testCaseExecutionQueueDepDAO.readExeQueueIdByQueueId(queueId);
    }

    @Override
    public AnswerItem<Integer> readNbWaitingByExeQueue(long exeQueueId) {
        return testCaseExecutionQueueDepDAO.readNbWaitingByExeQueue(exeQueueId);
    }

    @Override
    public void loadDependenciesOnTestCaseExecution(List<TestCaseExecution> testCaseExecutions) throws CerberusException {
        HashMap<TestCaseExecution, List<TestCaseExecutionQueueDep>> dependenciesByTestCaseExecution = testCaseExecutionQueueDepDAO.readDependenciesByTestCaseExecution(testCaseExecutions);

        // modify directly the parameter variable
        for (Map.Entry<TestCaseExecution, List<TestCaseExecutionQueueDep>> entry : dependenciesByTestCaseExecution.entrySet()) {
            entry.getKey().setTestCaseDep(entry.getValue());
        }
    }

    @Override
    public AnswerItem<Integer> readNbReleasedWithNOKByExeQueue(long exeQueueId) {
        return testCaseExecutionQueueDepDAO.readNbReleasedWithNOKByExeQueue(exeQueueId);
    }

    @Override
    public void manageDependenciesEndOfExecution(TestCaseExecution tCExecution) {
        if (tCExecution != null) {
            LOG.debug("Release dependencies of Execution : " + tCExecution.getId() + ".");

            // Updating all dependencies of type TCEEXEEND and tCExecution.getId() to RELEASED.
            AnswerItem ansNbDep = updateStatusToRelease(tCExecution.getEnvironment(), tCExecution.getCountry(), tCExecution.getTag(),
                    TestCaseExecutionQueueDep.TYPE_TCEXEEND, tCExecution.getTest(), tCExecution.getTestCase(), "", tCExecution.getId(), 0);
            int nbdep = (int) ansNbDep.getItem();
            // Only check status of each Queue Entries if we RELEASED at least 1 entry.
            if (nbdep > 0) {
                // Getting the list of impacted Queue Entries where we released dependencies.
                List<Long> al = new ArrayList<>();
                AnswerList ansQueueId = readExeQueueIdByExeId(tCExecution.getId());
                al = ansQueueId.getDataList();
                // For each exequeue entry we just updated, we move status from QUWITHDEP to QUEUED in case there are no more WAITING dependency.
                for (Long long1 : al) {
                    executionQueueService.checkAndReleaseQueuedEntry(long1, tCExecution.getTag());
                }
            }
        }
    }

    @Override
    public void manageDependenciesEndOfQueueExecution(long idQueue) {
        LOG.debug("Release dependencies of Queue : " + idQueue + ".");

        try {
            //, String environment, String country, String tag, String test, String testCase
            TestCaseExecutionQueue queueEntry = executionQueueService.convert(executionQueueService.readByKey(idQueue));

            // Updating all dependencies of type TCEEXEEND and tCExecution.getId() to RELEASED.
            AnswerItem ansNbDep = updateStatusToRelease(queueEntry.getEnvironment(), queueEntry.getCountry(), queueEntry.getTag(),
                    TestCaseExecutionQueueDep.TYPE_TCEXEEND, queueEntry.getTest(), queueEntry.getTestCase(), "Queue Entry " + idQueue + " in ERROR.", 0, idQueue);
            int nbdep = (int) ansNbDep.getItem();
            // Only check status of each Queue Entries if we RELEASED at least 1 entry.
            if (nbdep > 0) {
                // Getting the list of impacted Queue Entries where we released dependencies.
                List<Long> al = new ArrayList<>();
                AnswerList ansQueueId = readExeQueueIdByQueueId(idQueue);
                al = ansQueueId.getDataList();
                // For each exequeue entry we just updated, we move status from QUWITHDEP to QUEUED in case there are no more WAITING dependency.
                for (Long long1 : al) {
                    executionQueueService.checkAndReleaseQueuedEntry(long1, queueEntry.getTag());
                }
            }
        } catch (CerberusException ex) {
            LOG.error("Exception when release dep from Queue Error.", ex);
        }
    }

}
