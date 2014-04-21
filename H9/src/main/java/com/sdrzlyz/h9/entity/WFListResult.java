package com.sdrzlyz.h9.entity;

import java.util.List;

/**
 * Created by sdrzlyz on 14-3-26.
 */
public class WFListResult extends MessagesInfo {
    private List<WorkFlowList> workFlowList;

    public List<WorkFlowList> getWfList() {
        return workFlowList;
    }

    public void setWfList(List<WorkFlowList> wfList) {
        this.workFlowList = wfList;
    }
}
