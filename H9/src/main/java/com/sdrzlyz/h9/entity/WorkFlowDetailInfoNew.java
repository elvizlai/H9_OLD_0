package com.sdrzlyz.h9.entity;

import java.util.List;

/**
 * Created by sdrzlyz on 14-3-28.
 */
public class WorkFlowDetailInfoNew extends MessagesInfo {

    private List<WorkFlowButton> appButton;
    private String appTitle;
    private String condition;
    private String flowInstanceID;
    private String flowVersion;
    private String groupCode;
    private String httAppDID;
    private String httAppDName;
    private String httAppID;
    private String httAppOID;
    private String httAppTID;
    private String httCurUserID;
    private String httOperationType;
    private String isAvailability;
    private List<WorkFlowDealInfo> wfDealtList;
    private WFFieldListResult wfFieldListResult;

    public List<WorkFlowButton> getAppButton() {
        return this.appButton;
    }

    public void setAppButton(List<WorkFlowButton> paramList) {
        this.appButton = paramList;
    }

    public String getAppTitle() {
        return this.appTitle;
    }

    public void setAppTitle(String paramString) {
        this.appTitle = paramString;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String paramString) {
        this.condition = paramString;
    }

    public String getFlowInstanceID() {
        return this.flowInstanceID;
    }

    public void setFlowInstanceID(String paramString) {
        this.flowInstanceID = paramString;
    }

    public String getFlowVersion() {
        return this.flowVersion;
    }

    public void setFlowVersion(String paramString) {
        this.flowVersion = paramString;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public void setGroupCode(String paramString) {
        this.groupCode = paramString;
    }

    public String getHttAppDID() {
        return this.httAppDID;
    }

    public void setHttAppDID(String paramString) {
        this.httAppDID = paramString;
    }

    public String getHttAppDName() {
        return this.httAppDName;
    }

    public void setHttAppDName(String paramString) {
        this.httAppDName = paramString;
    }

    public String getHttAppID() {
        return this.httAppID;
    }

    public void setHttAppID(String paramString) {
        this.httAppID = paramString;
    }

    public String getHttAppOID() {
        return this.httAppOID;
    }

    public void setHttAppOID(String paramString) {
        this.httAppOID = paramString;
    }

    public String getHttAppTID() {
        return this.httAppTID;
    }

    public void setHttAppTID(String paramString) {
        this.httAppTID = paramString;
    }

    public String getHttCurUserID() {
        return this.httCurUserID;
    }

    public void setHttCurUserID(String paramString) {
        this.httCurUserID = paramString;
    }

    public String getHttOperationType() {
        return this.httOperationType;
    }

    public void setHttOperationType(String paramString) {
        this.httOperationType = paramString;
    }

    public String getIsAvailability() {
        return this.isAvailability;
    }

    public void setIsAvailability(String paramString) {
        this.isAvailability = paramString;
    }

    public List<WorkFlowDealInfo> getWfDealtList() {
        return this.wfDealtList;
    }

    public void setWfDealtList(List<WorkFlowDealInfo> paramList) {
        this.wfDealtList = paramList;
    }

    public WFFieldListResult getWfFieldListResult() {
        return this.wfFieldListResult;
    }

    public void setWfFieldListResult(WFFieldListResult paramWFFieldListResult) {
        this.wfFieldListResult = paramWFFieldListResult;
    }
}
