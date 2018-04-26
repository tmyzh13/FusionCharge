package com.isoftston.issuser.fusioncharge.model.beans;

import java.io.Serializable;

/**
 * Created by issuser on 2018/4/25.
 */

public class PublishCommentsBean implements Serializable {
    public int userId;
    public int pileId;
    public String evaluateTypeId;
    public float evaluateScore;
    public String evaluateContent;
    public String orderRecordNum;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPileId() {
        return pileId;
    }

    public void setPileId(int pileId) {
        this.pileId = pileId;
    }

    public String getEvaluateTypeId() {
        return evaluateTypeId;
    }

    public void setEvaluateTypeId(String evaluateTypeId) {
        this.evaluateTypeId = evaluateTypeId;
    }

    public float getEvaluateScore() {
        return evaluateScore;
    }

    public void setEvaluateScore(float evaluateScore) {
        this.evaluateScore = evaluateScore;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public String getOrderRecordNum() {
        return orderRecordNum;
    }

    public void setOrderRecordNum(String orderRecordNum) {
        this.orderRecordNum = orderRecordNum;
    }
}
