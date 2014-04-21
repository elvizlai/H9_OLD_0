package com.sdrzlyz.h9.entity;

import java.util.List;

/**
 * Created by sdrzlyz on 14-3-26.
 */
public class WFNextStepListResult extends MessagesInfo {
    private static final long serialVersionUID = 1L;
    private List<WorkFlowNextStep> wfNextStepList;

    public List<WorkFlowNextStep> getWfNextStepList() {
        return this.wfNextStepList;
    }

    public void setWfNextStepList(List<WorkFlowNextStep> paramList) {
        this.wfNextStepList = paramList;
    }
}
