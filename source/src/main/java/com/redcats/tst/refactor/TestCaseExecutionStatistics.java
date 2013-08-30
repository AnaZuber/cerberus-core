/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redcats.tst.refactor;

/**
 *
 * @author bcivel
 */
public class TestCaseExecutionStatistics {

    private String build;
    private String revision;
    private int days;
    private int numberOfTestcaseExecuted;
    private int numberOfApplicationExecuted;
    private int total;
    private int numberOfOK;
    private int numberOfKO;
    private int numberOfExecPerTc;
    private int numberOfExecPerTcPerDay;
    private int percentageOfOK;

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getNumberOfExecPerTc() {
        return numberOfExecPerTc;
    }

    public void setNumberOfExecPerTc(int numberOfExecPerTc) {
        this.numberOfExecPerTc = numberOfExecPerTc;
    }

    public int getNumberOfExecPerTcPerDay() {
        return numberOfExecPerTcPerDay;
    }

    public void setNumberOfExecPerTcPerDay(int numberOfExecPerTcPerDay) {
        this.numberOfExecPerTcPerDay = numberOfExecPerTcPerDay;
    }

    public int getNumberOfKO() {
        return numberOfKO;
    }

    public void setNumberOfKO(int numberOfKO) {
        this.numberOfKO = numberOfKO;
    }

    public int getNumberOfOK() {
        return numberOfOK;
    }

    public void setNumberOfOK(int numberOfOK) {
        this.numberOfOK = numberOfOK;
    }

    public int getNumberOfTestcaseExecuted() {
        return numberOfTestcaseExecuted;
    }

    public void setNumberOfTestcaseExecuted(int numberOfTestcaseExecuted) {
        this.numberOfTestcaseExecuted = numberOfTestcaseExecuted;
    }

    public int getNumberOfApplicationExecuted() {
        return numberOfApplicationExecuted;
    }

    public void setNumberOfApplicationExecuted(int numberOfApplicationExecuted) {
        this.numberOfApplicationExecuted = numberOfApplicationExecuted;
    }

    public float getPercentageOfOK() {
        return percentageOfOK;
    }

    public void setPercentageOfOK(int percentageOfOK) {
        this.percentageOfOK = percentageOfOK;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}