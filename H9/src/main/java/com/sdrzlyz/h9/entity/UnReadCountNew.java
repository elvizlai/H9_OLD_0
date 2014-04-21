package com.sdrzlyz.h9.entity;

/**
 * Created by sdrzlyz on 14-3-27.
 */
public class UnReadCountNew extends MessagesInfo {
    private Long callNumTotal;
    private Long callnum;
    private String serviceTime;
    private Long wfNumTotal;
    private Long wfnum;

    public Long getCallNumTotal() {
        return this.callNumTotal;
    }

    public void setCallNumTotal(Long paramLong) {
        this.callNumTotal = paramLong;
    }

    public Long getCallnum() {
        return this.callnum;
    }

    public void setCallnum(Long paramLong) {
        this.callnum = paramLong;
    }

    public String getServiceTime() {
        return this.serviceTime;
    }

    public void setServiceTime(String paramString) {
        this.serviceTime = paramString;
    }

    public Long getWfNumTotal() {
        return this.wfNumTotal;
    }

    public void setWfNumTotal(Long paramLong) {
        this.wfNumTotal = paramLong;
    }

    public Long getWfnum() {
        return this.wfnum;
    }

    public void setWfnum(Long paramLong) {
        this.wfnum = paramLong;
    }
}
