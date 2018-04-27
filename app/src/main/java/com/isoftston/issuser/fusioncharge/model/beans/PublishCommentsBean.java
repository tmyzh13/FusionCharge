package com.isoftston.issuser.fusioncharge.model.beans;

import java.io.Serializable;

/**
 * Created by issuser on 2018/4/25.
 */

public class PublishCommentsBean implements Serializable {
    public long userId;
    public long pileId;
    public String evaluateTypeId;
    public float evaluateScore;
    public String evaluateContent;
    public String orderRecordNum;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPileId() {
        return pileId;
    }

    public void setPileId(long pileId) {
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
