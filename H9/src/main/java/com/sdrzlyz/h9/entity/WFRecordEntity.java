package com.sdrzlyz.h9.entity;

import java.util.List;

/**
 * Created by sdrzlyz on 14-3-28.
 */
public class WFRecordEntity {
    private static final long serialVersionUID = 1L;
    private List<WorkFlowFieldInfo> wfRecord;

    public List<WorkFlowFieldInfo> getWfRecord() {
        return this.wfRecord;
    }

    public void setWfRecord(List<WorkFlowFieldInfo> paramList) {
        this.wfRecord = paramList;
    }
}
