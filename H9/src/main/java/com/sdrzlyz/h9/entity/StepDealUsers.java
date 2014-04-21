package com.sdrzlyz.h9.entity;

import java.util.List;

/**
 * Created by sdrzlyz on 14-3-26.
 */
public class StepDealUsers extends MessagesInfo {
    private static final long serialVersionUID = 1L;
    private List<WorkFlowNextStep> stepUser;

    public List<WorkFlowNextStep> getStepUser() {
        return this.stepUser;
    }

    public void setStepUser(List<WorkFlowNextStep> paramList) {
        this.stepUser = paramList;
    }
}
