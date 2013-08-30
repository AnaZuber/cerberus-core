package com.redcats.tst.dao.impl;

import com.redcats.tst.dao.ITestCaseStepDAO;
import com.redcats.tst.database.DatabaseSpring;
import com.redcats.tst.entity.TestCaseStep;
import com.redcats.tst.factory.IFactoryTestCaseStep;
import com.redcats.tst.log.MyLogger;
import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * {Insert class description here}
 *
 * @author Tiago Bernardes
 * @version 1.0, 29/12/2012
 * @since 2.0.0
 */
@Repository
public class TestCaseStepDAO implements ITestCaseStepDAO {

    /**
     * Description of the variable here.
     */
    @Autowired
    private DatabaseSpring databaseSpring;
    @Autowired
    private IFactoryTestCaseStep factoryTestCaseStep;

    /**
     * Short one line description.
     * <p/>
     * Longer description. If there were any, it would be here. <p> And even
     * more explanations to follow in consecutive paragraphs separated by HTML
     * paragraph breaks.
     *
     * @param variable Description text text text.
     * @return Description text text text.
     */
    @Override
    public List<TestCaseStep> findTestCaseStepByTestCase(String test, String testcase) {
        List<TestCaseStep> list = null;
        final String query = "SELECT * FROM testcasestep WHERE test = ? AND testcase = ?";

        try {
            PreparedStatement preStat = this.databaseSpring.connect().prepareStatement(query);
            preStat.setString(1, test);
            preStat.setString(2, testcase);
            try {
                ResultSet resultSet = preStat.executeQuery();
                list = new ArrayList<TestCaseStep>();
                try {
                    while (resultSet.next()) {
                        int step = resultSet.getInt("Step");
                        String description = resultSet.getString("Description");
                        list.add(factoryTestCaseStep.create(test, testcase, step, description));
                    }
                } catch (SQLException exception) {
                    MyLogger.log(TestCaseStepDAO.class.getName(), Level.ERROR, exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                MyLogger.log(TestCaseStepDAO.class.getName(), Level.ERROR, exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            MyLogger.log(TestCaseStepDAO.class.getName(), Level.ERROR, exception.toString());
        } finally {
            this.databaseSpring.disconnect();
        }
        return list;
    }

    @Override
    public List<String> getLoginStepFromTestCase(String countryCode, String application) {
        List<String> list = null;
        final String query = "SELECT tc.testcase FROM TestCaseCountry t, TestCase tc WHERE t.country = ? AND t.test = 'Pre Testing' "
                + "AND tc.application = ? AND tc.tcActive = 'Y' AND t.test = tc.test AND t.testcase = tc.testcase ORDER BY testcase ASC";

        try {
            PreparedStatement preStat = this.databaseSpring.connect().prepareStatement(query);
            preStat.setString(1, countryCode);
            preStat.setString(2, application);
            try {
                ResultSet resultSet = preStat.executeQuery();
                list = new ArrayList<String>();
                try {
                    while (resultSet.next()) {
                        MyLogger.log(TestCaseStepDAO.class.getName(), Level.DEBUG, "Found active Pretest : " + resultSet.getString("testcase"));
                        list.add(resultSet.getString("testcase"));
                    }
                } catch (SQLException exception) {
                    MyLogger.log(TestCaseStepDAO.class.getName(), Level.ERROR, exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                MyLogger.log(TestCaseStepDAO.class.getName(), Level.ERROR, exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            MyLogger.log(TestCaseStepDAO.class.getName(), Level.ERROR, exception.toString());
        } finally {
            this.databaseSpring.disconnect();
        }
        return list;
    }
}
