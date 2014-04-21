package com.sdrzlyz.h9.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huagai on 14-4-8.
 */
public class CallNew extends MessagesInfo implements Serializable {
    private String callContent;
    private String content;
    private String firstRCContent;
    private String firstRCTime;
    private String firstRCsender;
    private String id;
    private int isRead;
    private String relatedPeopleId;
    private String relatedPeopleName;
    private String senderId;
    private String senderName;
    private List<WorkFlowSlave> slaveList;
    private String startTime;

    public String getCallContent() {
        return this.callContent;
    }

    public void setCallContent(String paramString) {
        this.callContent = paramString;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String paramString) {
        this.content = paramString;
    }

    public String getFirstRCContent() {
        return this.firstRCContent;
    }

    public void setFirstRCContent(String paramString) {
        this.firstRCContent = paramString;
    }

    public String getFirstRCTime() {
        return this.firstRCTime;
    }

    public void setFirstRCTime(String paramString) {
        this.firstRCTime = paramString;
    }

    public String getFirstRCsender() {
        return this.firstRCsender;
    }

    public void setFirstRCsender(String paramString) {
        this.firstRCsender = paramString;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String paramString) {
        this.id = paramString;
    }

    public int getIsRead() {
        return this.isRead;
    }

    public void setIsRead(int paramInt) {
        this.isRead = paramInt;
    }

    public String getRelatedPeopleId() {
        return this.relatedPeopleId;
    }

    public void setRelatedPeopleId(String paramString) {
        this.relatedPeopleId = paramString;
    }

    public String getRelatedPeopleName() {
        return this.relatedPeopleName;
    }

    public void setRelatedPeopleName(String paramString) {
        this.relatedPeopleName = paramString;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String paramString) {
        this.senderId = paramString;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public void setSenderName(String paramString) {
        this.senderName = paramString;
    }

    public List<WorkFlowSlave> getSlaveList() {
        return this.slaveList;
    }

    public void setSlaveList(List<WorkFlowSlave> paramList) {
        this.slaveList = paramList;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String paramString) {
        this.startTime = paramString;
    }
}
