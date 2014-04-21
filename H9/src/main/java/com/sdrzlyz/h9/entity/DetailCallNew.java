package com.sdrzlyz.h9.entity;

import java.util.List;

/**
 * Created by huagai on 14-4-8.
 */
public class DetailCallNew extends MessagesInfo {
    private int callType;
    private String content;
    private String id;
    private List<Call> relatedCallList;
    private int relatedCallNum;
    private int relatedPeopleNum;
    private String sendId;
    private String sender;
    private List<WorkFlowSlave> slaveLists;
    private String startTime;

    public int getCallType() {
        return this.callType;
    }

    public void setCallType(int paramInt) {
        this.callType = paramInt;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String paramString) {
        this.content = paramString;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String paramString) {
        this.id = paramString;
    }

    public List<Call> getRelatedCallList() {
        return this.relatedCallList;
    }

    public void setRelatedCallList(List<Call> paramList) {
        this.relatedCallList = paramList;
    }

    public int getRelatedCallNum() {
        return this.relatedCallNum;
    }

    public void setRelatedCallNum(int paramInt) {
        this.relatedCallNum = paramInt;
    }

    public int getRelatedPeopleNum() {
        return this.relatedPeopleNum;
    }

    public void setRelatedPeopleNum(int paramInt) {
        this.relatedPeopleNum = paramInt;
    }

    public String getSendId() {
        return this.sendId;
    }

    public void setSendId(String paramString) {
        this.sendId = paramString;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String paramString) {
        this.sender = paramString;
    }

    public List<WorkFlowSlave> getSlaveLists() {
        return this.slaveLists;
    }

    public void setSlaveLists(List<WorkFlowSlave> paramList) {
        this.slaveLists = paramList;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String paramString) {
        this.startTime = paramString;
    }
}
