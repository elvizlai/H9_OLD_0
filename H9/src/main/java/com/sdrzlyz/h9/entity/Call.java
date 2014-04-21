package com.sdrzlyz.h9.entity;

import java.util.List;

/**
 * Created by huagai on 14-4-8.
 */
public class Call extends MessagesInfo {
    private String callContent;
    private String content;
    private String id;
    private int isRead;
    private String sender;
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

    public String getSender() {
        return this.sender;
    }

    public void setSender(String paramString) {
        this.sender = paramString;
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
