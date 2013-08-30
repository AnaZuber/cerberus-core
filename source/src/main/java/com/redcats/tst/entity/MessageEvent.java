package com.redcats.tst.entity;

/**
 * {Insert class description here}
 *
 * @author Tiago Bernardes
 * @version 1.0, 19/Dez/2012
 * @since 2.0.0
 */
public class MessageEvent {

    /**
     * Message is used to feedback the result of any Cerberus event. Events
     * could by Property, Action, Control or even Step. For every event, we
     * have: - a number - a 2 digit code that report the status of the event. -
     * a clear message that will be reported to the user. describing what was
     * done or the error that occured. - a boolean that define whether the
     * complete test execution should stop or not. - a boolean that define
     * whether a screenshot will be done in case of problem (only if screenshot
     * option is set to 1). - the corresponding Execution message that will be
     * updated at the execution level.
     */
    private int code;
    private String codeString;
    private String description;
    private boolean stopTest;
    private boolean doScreenshot;
    private MessageGeneralEnum message;

    public MessageEvent(MessageEventEnum tempMessage) {
        this.code = tempMessage.getCode();
        this.codeString = tempMessage.getCodeString();
        this.description = tempMessage.getDescription();
        this.stopTest = tempMessage.isStopTest();
        this.doScreenshot = tempMessage.isDoScreenshot();
        this.message = tempMessage.getMessage();
    }

    public MessageGeneralEnum getMessage() {
        return message;
    }

    public boolean isStopTest() {
        return stopTest;
    }

    public boolean isDoScreenshot() {
        return doScreenshot;
    }

    public int getCode() {
        return this.code;
    }

    public String getCodeString() {
        return codeString;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean equals(MessageEvent msg) {
        return this.code == msg.code;
    }

    /**
     * The only thing that can be changed is the description. All the rest is
     * static.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
