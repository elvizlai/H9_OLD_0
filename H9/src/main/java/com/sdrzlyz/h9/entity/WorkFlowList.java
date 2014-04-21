package com.sdrzlyz.h9.entity;

/**
 * Created by sdrzlyz on 14-3-26.
 */
public class WorkFlowList extends MessagesInfo {
    private String appBeginTime;
    private String appCode;
    private String appID;
    private String appTitle;
    private String appType;
    private int isSlaveFlag;

    public String getAppBeginTime() {
        return appBeginTime;
    }

    public void setAppBeginTime(String paramString) {
        this.appBeginTime = paramString;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String paramString) {
        this.appCode = paramString;
    }

    public String getAppID() {
        return this.appID;
    }

    public void setAppID(String paramString) {
        this.appID = paramString;
    }

    public String getAppTitle() {
        return this.appTitle;
    }

    public void setAppTitle(String paramString) {
        this.appTitle = paramString;
    }

    public String getAppType() {
        return this.appType;
    }

    public void setAppType(String paramString) {
        this.appType = paramString;
    }

    public int getIsSlaveFlag() {
        return this.isSlaveFlag;
    }

    public void setIsSlaveFlag(int paramInt) {
        this.isSlaveFlag = paramInt;
    }
}
