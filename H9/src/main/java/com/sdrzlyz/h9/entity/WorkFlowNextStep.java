package com.sdrzlyz.h9.entity;

import java.util.List;

/**
 * Created by sdrzlyz on 14-3-26.
 */
public class WorkFlowNextStep extends MessagesInfo {
    private static final long serialVersionUID = 1L;
    private List<WorkFlowFieldInfo> acceptUserInfo;
    private String callFlag;
    private String isMulti;
    private String nextStepID;
    private String nextStepName;
    private String smsFlag;
    private String transactMode;

    public static long getSerialversionuid() {
        return 1L;
    }

    public List<WorkFlowFieldInfo> getAcceptUserInfo() {
        return this.acceptUserInfo;
    }

    public void setAcceptUserInfo(List<WorkFlowFieldInfo> paramList) {
        this.acceptUserInfo = paramList;
    }

    public String getCallFlag() {
        return this.callFlag;
    }

    public void setCallFlag(String paramString) {
        this.callFlag = paramString;
    }

    public String getIsMulti() {
        return this.isMulti;
    }

    public void setIsMulti(String paramString) {
        this.isMulti = paramString;
    }

    public String getNextStepID() {
        return this.nextStepID;
    }

    public void setNextStepID(String paramString) {
        this.nextStepID = paramString;
    }

    public String getNextStepName() {
        return this.nextStepName;
    }

    public void setNextStepName(String paramString) {
        this.nextStepName = paramString;
    }

    public String getSmsFlag() {
        return this.smsFlag;
    }

    public void setSmsFlag(String paramString) {
        this.smsFlag = paramString;
    }

    public String getTransactMode() {
        return this.transactMode;
    }

    public void setTransactMode(String paramString) {
        this.transactMode = paramString;
    }
}
