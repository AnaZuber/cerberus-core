/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redcats.tst.entity;

/**
 * @author bcivel
 */
public class TestCaseStepActionControlExecution {

    private long id;
    private String test;
    private String testCase;
    private int step;
    private int sequence;
    private int control;
    private String returnCode;
    private String returnMessage;
    private String controlType;
    private String controlProperty;
    private String controlValue;
    private String fatal;
    private long start;
    private long end;
    private long startLong;
    private long endLong;
    private String screenshotFilename;
    /**
     *
     */
    private TestCaseStepActionExecution testCaseStepActionExecution;
    private MessageEvent controlResultMessage;
    private MessageGeneral executionResultMessage;
    private boolean stopExecution;

    public MessageEvent getControlResultMessage() {
        return controlResultMessage;
    }

    public void setControlResultMessage(MessageEvent controlResultMessage) {
        this.controlResultMessage = controlResultMessage;
        if (controlResultMessage != null) {
            this.setReturnCode(controlResultMessage.getCodeString());
            this.setReturnMessage(controlResultMessage.getDescription());
        }
    }

    public MessageGeneral getExecutionResultMessage() {
        return executionResultMessage;
    }

    public void setExecutionResultMessage(MessageGeneral executionResultMessage) {
        this.executionResultMessage = executionResultMessage;
    }

    public boolean isStopExecution() {
        return stopExecution;
    }

    public void setStopExecution(boolean stopExecution) {
        this.stopExecution = stopExecution;
    }

    public TestCaseStepActionExecution getTestCaseStepActionExecution() {
        return testCaseStepActionExecution;
    }

    public void setTestCaseStepActionExecution(TestCaseStepActionExecution testCaseStepActionExecution) {
        this.testCaseStepActionExecution = testCaseStepActionExecution;
    }

    public int getControl() {
        return control;
    }

    public void setControl(int control) {
        this.control = control;
    }

    public String getControlProperty() {
        return controlProperty;
    }

    public void setControlProperty(String controlProperty) {
        this.controlProperty = controlProperty;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public String getControlValue() {
        return controlValue;
    }

    public void setControlValue(String controlValue) {
        this.controlValue = controlValue;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getEndLong() {
        return endLong;
    }

    public void setEndLong(long endLong) {
        this.endLong = endLong;
    }

    public String getFatal() {
        return fatal;
    }

    public void setFatal(String fatal) {
        this.fatal = fatal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getScreenshotFilename() {
        return screenshotFilename;
    }

    public void setScreenshotFilename(String screenshotFilename) {
        this.screenshotFilename = screenshotFilename;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStartLong() {
        return startLong;
    }

    public void setStartLong(long startLong) {
        this.startLong = startLong;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTestCase() {
        return testCase;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }
}
