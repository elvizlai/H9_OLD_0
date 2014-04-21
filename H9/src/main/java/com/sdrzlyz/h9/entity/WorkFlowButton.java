package com.sdrzlyz.h9.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sdrzlyz on 14-3-26.
 */
public class WorkFlowButton extends MessagesInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appFlag;
    private String appID;
    private String appIdea;
    private String appTID;
    private String appTitle;
    private Object[] businessData;
    private String buttonId;
    private String buttonValue;
    private StepDealUsers callUsers;
    private String copyFlag;
    private List<WorkFlowFieldInfo> distributeUsers;
    private String isJump;
    private String isNewFlag;
    private String isOrder;
    private String maxCount;
    private WFNextStepListResult nextStepList;
    private String promptContent;
    private String smsTransactFlag;
    private String version;

    public static long getSerialversionuid() {
        return 1L;
    }

    public String getAppFlag() {
        return this.appFlag;
    }

    public void setAppFlag(String paramString) {
        this.appFlag = paramString;
    }

    public String getAppID() {
        return this.appID;
    }

    public void setAppID(String paramString) {
        this.appID = paramString;
    }

    public String getAppIdea() {
        return this.appIdea;
    }

    public void setAppIdea(String paramString) {
        this.appIdea = paramString;
    }

    public String getAppTID() {
        return this.appTID;
    }

    public void setAppTID(String paramString) {
        this.appTID = paramString;
    }

    public String getAppTitle() {
        return this.appTitle;
    }

    public void setAppTitle(String paramString) {
        this.appTitle = paramString;
    }

    public Object[] getBusinessData() {
        return this.businessData;
    }

    public void setBusinessData(Object[] paramArrayOfObject) {
        this.businessData = paramArrayOfObject;
    }

    public String getButtonId() {
        return this.buttonId;
    }

    public void setButtonId(String paramString) {
        this.buttonId = paramString;
    }

    public String getButtonValue() {
        return this.buttonValue;
    }

    public void setButtonValue(String paramString) {
        this.buttonValue = paramString;
    }

    public StepDealUsers getCallUsers() {
        return this.callUsers;
    }

    public void setCallUsers(StepDealUsers paramStepDealUsers) {
        this.callUsers = paramStepDealUsers;
    }

    public String getCopyFlag() {
        return this.copyFlag;
    }

    public void setCopyFlag(String paramString) {
        this.copyFlag = paramString;
    }

    public List<WorkFlowFieldInfo> getDistributeUsers() {
        return this.distributeUsers;
    }

    public void setDistributeUsers(List<WorkFlowFieldInfo> paramList) {
        this.distributeUsers = paramList;
    }

    public String getIsJump() {
        return this.isJump;
    }

    public void setIsJump(String paramString) {
        this.isJump = paramString;
    }

    public String getIsNewFlag() {
        return this.isNewFlag;
    }

    public void setIsNewFlag(String paramString) {
        this.isNewFlag = paramString;
    }

    public String getIsOrder() {
        return this.isOrder;
    }

    public void setIsOrder(String paramString) {
        this.isOrder = paramString;
    }

    public String getMaxCount() {
        return this.maxCount;
    }

    public void setMaxCount(String paramString) {
        this.maxCount = paramString;
    }

    public WFNextStepListResult getNextStepList() {
        return this.nextStepList;
    }

    public void setNextStepList(WFNextStepListResult paramWFNextStepListResult) {
        this.nextStepList = paramWFNextStepListResult;
    }

    public String getPromptContent() {
        return this.promptContent;
    }

    public void setPromptContent(String paramString) {
        this.promptContent = paramString;
    }

    public String getSmsTransactFlag() {
        return this.smsTransactFlag;
    }

    public void setSmsTransactFlag(String paramString) {
        this.smsTransactFlag = paramString;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String paramString) {
        this.version = paramString;
    }
}
