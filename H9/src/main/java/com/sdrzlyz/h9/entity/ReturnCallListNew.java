package com.sdrzlyz.h9.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huagai on 14-4-8.
 */
public class ReturnCallListNew extends MessagesInfo implements Serializable {
    private String lastCallId;
    private List<CallNew> returnCallList;
    private long totalCall;

    public String getLastCallId() {
        return this.lastCallId;
    }

    public void setLastCallId(String paramString) {
        this.lastCallId = paramString;
    }

    public List<CallNew> getReturnCallList() {
        return this.returnCallList;
    }

    public void setReturnCallList(List<CallNew> paramList) {
        this.returnCallList = paramList;
    }

    public long getTotalCall() {
        return this.totalCall;
    }

    public void setTotalCall(long paramLong) {
        this.totalCall = paramLong;
    }
}
