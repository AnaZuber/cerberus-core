package com.redcats.tst.entity;

/**
 * {Insert class description here}
 *
 * @author Tiago Bernardes
 * @version 1.0, 05/03/2013
 * @since 2.0.0
 */
public class StatisticSummary {
    private int totNbHits;
    private int totTps;
    private int totSize;
    private int nbRc2xx;
    private int nbRc3xx;
    private int nbRc4xx;
    private int nbRc5xx;
    private int imgNb;
    private int imgTps;
    private int imgSizeTot;
    private int imgSizeMax;
    private String imgSizeMaxUrl;
    private int jsNb;
    private int jsTps;
    private int jsSizeTot;
    private int jsSizeMax;
    private String jsSizeMaxUrl;
    private int cssNb;
    private int cssTps;
    private int cssSizeTot;
    private int cssSizeMax;
    private String cssSizeMaxUrl;

    public StatisticSummary() {
        this.totNbHits = 0;
        this.totTps = 0;
        this.totSize = 0;
        this.nbRc2xx = 0;
        this.nbRc3xx = 0;
        this.nbRc4xx = 0;
        this.nbRc5xx = 0;
        this.imgNb = 0;
        this.imgTps = 0;
        this.imgSizeTot = 0;
        this.imgSizeMax = 0;
        this.imgSizeMaxUrl = "";
        this.jsNb = 0;
        this.jsTps = 0;
        this.jsSizeTot = 0;
        this.jsSizeMax = 0;
        this.jsSizeMaxUrl = "";
        this.cssNb = 0;
        this.cssTps = 0;
        this.cssSizeTot = 0;
        this.cssSizeMax = 0;
        this.cssSizeMaxUrl = "";
    }

    public int getTotNbHits() {
        return this.totNbHits;
    }

    public void addTotNbHits() {
        this.totNbHits++;
    }

    public int getTotTps() {
        return this.totTps;
    }

    public void addTotTps(int tempTotTps) {
        this.totTps += tempTotTps;
    }

    public int getTotSize() {
        return this.totSize;
    }

    public void addTotSize(int tempTotSize) {
        this.totSize += tempTotSize;
    }

    public int getNbRc2xx() {
        return this.nbRc2xx;
    }

    public void addNbRc2xx() {
        this.nbRc2xx++;
    }

    public int getNbRc3xx() {
        return this.nbRc3xx;
    }

    public void addNbRc3xx() {
        this.nbRc3xx++;
    }

    public int getNbRc4xx() {
        return this.nbRc4xx;
    }

    public void addNbRc4xx() {
        this.nbRc4xx++;
    }

    public int getNbRc5xx() {
        return this.nbRc5xx;
    }

    public void addNbRc5xx() {
        this.nbRc5xx++;
    }

    public int getImgNb() {
        return this.imgNb;
    }

    public void addImgNb() {
        this.imgNb++;
    }

    public int getImgTps() {
        return this.imgTps;
    }

    public void addImgTps(int tempImgTps) {
        this.imgTps += tempImgTps;
    }

    public int getImgSizeTot() {
        return this.imgSizeTot;
    }

    public void addImgSizeTot(int tempImgSizeTot) {
        this.imgSizeTot += tempImgSizeTot;
    }

    public int getImgSizeMax() {
        return this.imgSizeMax;
    }

    public void setImgSizeMax(int imgSizeMax) {
        this.imgSizeMax = imgSizeMax;
    }

    public String getImgSizeMaxUrl() {
        return this.imgSizeMaxUrl;
    }

    public void setImgSizeMaxUrl(String imgSizeMaxUrl) {
        this.imgSizeMaxUrl = imgSizeMaxUrl;
    }

    public int getJsNb() {
        return this.jsNb;
    }

    public void addJsNb() {
        this.jsNb++;
    }

    public int getJsTps() {
        return this.jsTps;
    }

    public void addJsTps(int tempJsTps) {
        this.jsTps += tempJsTps;
    }

    public int getJsSizeTot() {
        return this.jsSizeTot;
    }

    public void addJsSizeTot(int tempJsSizeTot) {
        this.jsSizeTot += tempJsSizeTot;
    }

    public int getJsSizeMax() {
        return this.jsSizeMax;
    }

    public void setJsSizeMax(int jsSizeMax) {
        this.jsSizeMax = jsSizeMax;
    }

    public String getJsSizeMaxUrl() {
        return this.jsSizeMaxUrl;
    }

    public void setJsSizeMaxUrl(String jsSizeMaxUrl) {
        this.jsSizeMaxUrl = jsSizeMaxUrl;
    }

    public int getCssNb() {
        return this.cssNb;
    }

    public void addCssNb() {
        this.cssNb++;
    }

    public int getCssTps() {
        return this.cssTps;
    }

    public void addCssTps(int tempCssTps) {
        this.cssTps += tempCssTps;
    }

    public int getCssSizeTot() {
        return this.cssSizeTot;
    }

    public void addCssSizeTot(int tempCssSizeTot) {
        this.cssSizeTot += tempCssSizeTot;
    }

    public int getCssSizeMax() {
        return this.cssSizeMax;
    }

    public void setCssSizeMax(int cssSizeMax) {
        this.cssSizeMax = cssSizeMax;
    }

    public String getCssSizeMaxUrl() {
        return this.cssSizeMaxUrl;
    }

    public void setCssSizeMaxUrl(String cssSizeMaxUrl) {
        this.cssSizeMaxUrl = cssSizeMaxUrl;
    }
}
