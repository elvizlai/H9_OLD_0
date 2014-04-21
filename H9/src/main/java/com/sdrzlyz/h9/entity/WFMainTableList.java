package com.sdrzlyz.h9.entity;

import java.util.List;

/**
 * Created by sdrzlyz on 14-3-28.
 */
public class WFMainTableList {
    private List<WorkFlowSlave> appBody;
    private List<WorkFlowSlave> appSlave;
    private WFRecordEntity mainFieldList;

    public List<WorkFlowSlave> getAppBody() {
        return this.appBody;
    }

    public void setAppBody(List<WorkFlowSlave> paramList) {
        this.appBody = paramList;
    }

    public List<WorkFlowSlave> getAppSlave() {
        return this.appSlave;
    }

    public void setAppSlave(List<WorkFlowSlave> paramList) {
        this.appSlave = paramList;
    }

    public WFRecordEntity getMainFieldList() {
        return this.mainFieldList;
    }

    public void setMainFieldList(WFRecordEntity paramWFRecordEntity) {
        this.mainFieldList = paramWFRecordEntity;
    }
}
