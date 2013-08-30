package com.redcats.tst.dao.impl;

import com.redcats.tst.dao.ITestCaseStepActionControlExecutionDAO;
import com.redcats.tst.database.DatabaseSpring;
import com.redcats.tst.entity.TestCaseStepActionControlExecution;
import com.redcats.tst.entity.TestCaseStepActionExecution;
import com.redcats.tst.factory.IFactoryTestCaseStepActionControlExecution;
import com.redcats.tst.log.MyLogger;
import com.redcats.tst.util.ParameterParserUtil;
import com.redcats.tst.util.StringUtil;
import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * {Insert class description here}
 *
 * @author Tiago Bernardes
 * @version 1.0, 02/01/2013
 * @since 2.0.0
 */
@Repository
public class TestCaseStepActionControlExecutionDAO implements ITestCaseStepActionControlExecutionDAO {

    /**
     * Description of the variable here.
     */
    @Autowired
    private DatabaseSpring databaseSpring;
    @Autowired
    private IFactoryTestCaseStepActionControlExecution factoryTestCaseStepActionControlExecution;

    /**
     * Short one line description.
     * <p/>
     * Longer description. If there were any, it would be here. <p> And even
     * more explanations to follow in consecutive paragraphs separated by HTML
     * paragraph breaks.
     *
     * @param variable Description text text text.
     */
    @Override
    public void insertTestCaseStepActionControlExecution(TestCaseStepActionControlExecution testCaseStepActionControlExecution) {

        final String query = "INSERT INTO testcasestepactioncontrolexecution(id, step, sequence, control, returncode, controltype, "
                + "controlproperty, controlvalue, fatal, start, END, startlong, endlong, returnmessage, test, testcase, screenshotfilename) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preStat = this.databaseSpring.connect().prepareStatement(query);
            preStat.setLong(1, testCaseStepActionControlExecution.getId());
            preStat.setInt(2, testCaseStepActionControlExecution.getStep());
            preStat.setInt(3, testCaseStepActionControlExecution.getSequence());
            preStat.setInt(4, testCaseStepActionControlExecution.getControl());
            preStat.setString(5, ParameterParserUtil.parseStringParam(testCaseStepActionControlExecution.getReturnCode(), ""));
            preStat.setString(6, StringUtil.getLeftString(testCaseStepActionControlExecution.getControlType(), 200));
            preStat.setString(7, StringUtil.getLeftString(testCaseStepActionControlExecution.getControlProperty(), 2500));
            preStat.setString(8, StringUtil.getLeftString(testCaseStepActionControlExecution.getControlValue(), 200));
            preStat.setString(9, testCaseStepActionControlExecution.getFatal());
            if (testCaseStepActionControlExecution.getStart() != 0) {
                preStat.setTimestamp(10, new Timestamp(testCaseStepActionControlExecution.getStart()));
            } else {
                preStat.setString(10, "0000-00-00 00:00:00");
            }
            if (testCaseStepActionControlExecution.getEnd() != 0) {
                preStat.setTimestamp(11, new Timestamp(testCaseStepActionControlExecution.getEnd()));
            } else {
                preStat.setString(11, "0000-00-00 00:00:00");
            }
            preStat.setString(12, new SimpleDateFormat("yyyyMMddHHmmssSSS").format(testCaseStepActionControlExecution.getStart()));
            preStat.setString(13, new SimpleDateFormat("yyyyMMddHHmmssSSS").format(testCaseStepActionControlExecution.getEnd()));
            preStat.setString(14, StringUtil.getLeftString(ParameterParserUtil.parseStringParam(testCaseStepActionControlExecution.getReturnMessage(), ""), 500));
            preStat.setString(15, testCaseStepActionControlExecution.getTest());
            preStat.setString(16, testCaseStepActionControlExecution.getTestCase());
            preStat.setString(17, testCaseStepActionControlExecution.getScreenshotFilename());

            try {
                preStat.executeUpdate();

            } catch (SQLException exception) {
                MyLogger.log(TestCaseStepActionControlExecutionDAO.class.getName(), Level.ERROR, exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            MyLogger.log(TestCaseStepActionControlExecutionDAO.class.getName(), Level.ERROR, exception.toString());
        } finally {
            this.databaseSpring.disconnect();
        }
    }
    
    /**
     * Short one line description.
     * <p/>
     * Longer description. If there were any, it would be here. <p> And even
     * more explanations to follow in consecutive paragraphs separated by HTML
     * paragraph breaks.
     *
     * @param variable Description text text text.
     */
    @Override
    public void updateTestCaseStepActionControlExecution(TestCaseStepActionControlExecution testCaseStepActionControlExecution) {

        final String query = "UPDATE testcasestepactioncontrolexecution SET returncode = ?, controltype = ?, "
                + "controlproperty = ?, controlvalue = ?, fatal = ?, start = ?, END = ?, startlong = ?, endlong = ?"
                + ", returnmessage = ?, screenshotfilename = ? "
                + "WHERE id = ? and test = ? and testcase = ? and step = ? and sequence = ? and control = ? ";

        try {
            PreparedStatement preStat = this.databaseSpring.connect().prepareStatement(query);
            preStat.setString(1, ParameterParserUtil.parseStringParam(testCaseStepActionControlExecution.getReturnCode(), ""));
            preStat.setString(2, StringUtil.getLeftString(testCaseStepActionControlExecution.getControlType(), 200));
            preStat.setString(3, StringUtil.getLeftString(testCaseStepActionControlExecution.getControlProperty(), 2500));
            preStat.setString(4, StringUtil.getLeftString(testCaseStepActionControlExecution.getControlValue(), 200));
            preStat.setString(5, testCaseStepActionControlExecution.getFatal());
            if (testCaseStepActionControlExecution.getStart() != 0) {
                preStat.setTimestamp(6, new Timestamp(testCaseStepActionControlExecution.getStart()));
            } else {
                preStat.setString(6, "0000-00-00 00:00:00");
            }
            if (testCaseStepActionControlExecution.getEnd() != 0) {
                preStat.setTimestamp(7, new Timestamp(testCaseStepActionControlExecution.getEnd()));
            } else {
                preStat.setString(7, "0000-00-00 00:00:00");
            }
            preStat.setString(8, new SimpleDateFormat("yyyyMMddHHmmssSSS").format(testCaseStepActionControlExecution.getStart()));
            preStat.setString(9, new SimpleDateFormat("yyyyMMddHHmmssSSS").format(testCaseStepActionControlExecution.getEnd()));
            preStat.setString(10, StringUtil.getLeftString(ParameterParserUtil.parseStringParam(testCaseStepActionControlExecution.getReturnMessage(), ""), 500));
            preStat.setString(11, testCaseStepActionControlExecution.getScreenshotFilename());

            preStat.setLong(12, testCaseStepActionControlExecution.getId());
            preStat.setString(13, testCaseStepActionControlExecution.getTest());
            preStat.setString(14, testCaseStepActionControlExecution.getTestCase());
            preStat.setInt(15, testCaseStepActionControlExecution.getStep());
            preStat.setInt(16, testCaseStepActionControlExecution.getSequence());
            preStat.setInt(17, testCaseStepActionControlExecution.getControl());
            try {
                preStat.executeUpdate();

            } catch (SQLException exception) {
                MyLogger.log(TestCaseStepActionControlExecutionDAO.class.getName(), Level.ERROR, exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            MyLogger.log(TestCaseStepActionControlExecutionDAO.class.getName(), Level.ERROR, exception.toString());
        } finally {
            this.databaseSpring.disconnect();
        }
    }
    
    @Override
    public List<TestCaseStepActionControlExecution> findTestCaseStepActionControlExecutionByCriteria(long id, String test, String testCase, int step, int sequence){
        List<TestCaseStepActionControlExecution> result = null;
        TestCaseStepActionControlExecution resultData;
        boolean throwEx = false;
        final String query = "SELECT * FROM TestCaseStepActionControlExecution WHERE id = ? and test = ? and testcase = ? and step = ? and sequence = ? ORDER BY control";

        try {
            PreparedStatement preStat = this.databaseSpring.connect().prepareStatement(query);
            preStat.setString(1, String.valueOf(id));
            preStat.setString(2, test);
            preStat.setString(3, testCase);
            preStat.setInt(4, step);
            preStat.setInt(5, sequence);

            try {
                ResultSet resultSet = preStat.executeQuery();
                result = new ArrayList<TestCaseStepActionControlExecution>();
                try {
                    while (resultSet.next()) {
                        int control = resultSet.getInt("control");
                        String returnCode = resultSet.getString("returncode");
                        String returnMessage = resultSet.getString("returnmessage");
                        String controlType = resultSet.getString("controlType");
                        String controlProperty = resultSet.getString("ControlProperty");
                        String controlValue = resultSet.getString("controlValue");
                        String fatal = resultSet.getString("fatal");
                        long start = resultSet.getLong("start");
                        long end = resultSet.getLong("end");
                        long startlong = resultSet.getLong("startlong");
                        long endlong = resultSet.getLong("endlong");
                        String screenshot = resultSet.getString("ScreenshotFilename");
                        resultData = factoryTestCaseStepActionControlExecution.create(id, test, testCase, step, sequence, control, returnCode, returnMessage, controlType, controlProperty, controlValue, fatal, start, end, startlong, endlong, screenshot, null, null);
                        result.add(resultData);
                    }
                } catch (SQLException exception) {
                    MyLogger.log(TestCaseExecutionDataDAO.class.getName(), Level.ERROR, exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                MyLogger.log(TestCaseExecutionDataDAO.class.getName(), Level.ERROR, exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            MyLogger.log(TestCaseExecutionDataDAO.class.getName(), Level.ERROR, exception.toString());
        } finally {
            this.databaseSpring.disconnect();
        }
        return result;
    }
    
    
}
