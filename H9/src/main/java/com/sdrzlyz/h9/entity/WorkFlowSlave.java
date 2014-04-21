package com.sdrzlyz.h9.entity;

/**
 * Created by sdrzlyz on 14-3-28.
 */
public class WorkFlowSlave extends MessagesInfo {
    private static final long serialVersionUID = 1L;
    private String appSlaveID;
    private String appSlaveName;
    private Long appSlaveSize;
    private String appSlaveUrl;

    public String getAppSlaveID() {
        return this.appSlaveID;
    }

    public void setAppSlaveID(String paramString) {
        this.appSlaveID = paramString;
    }

    public String getAppSlaveName() {
        return this.appSlaveName;
    }

    public void setAppSlaveName(String paramString) {
        this.appSlaveName = paramString;
    }

    public Long getAppSlaveSize() {
        return this.appSlaveSize;
    }

    public void setAppSlaveSize(Long paramLong) {
        this.appSlaveSize = paramLong;
    }

    public String getAppSlaveUrl() {
        return this.appSlaveUrl;
    }

    public void setAppSlaveUrl(String paramString) {
        this.appSlaveUrl = paramString;
    }
}
