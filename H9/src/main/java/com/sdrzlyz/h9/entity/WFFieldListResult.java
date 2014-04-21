package com.sdrzlyz.h9.entity;

import java.util.List;

/**
 * Created by sdrzlyz on 14-3-28.
 */
public class WFFieldListResult extends MessagesInfo {
    private static final long serialVersionUID = 1L;
    private WFMainTableList mainTableList;
    private List<WFSubTableList> subTableList;

    public static long getSerialversionuid() {
        return 1L;
    }

    public WFMainTableList getMainTableList() {
        return this.mainTableList;
    }

    public void setMainTableList(WFMainTableList paramWFMainTableList) {
        this.mainTableList = paramWFMainTableList;
    }

    public List<WFSubTableList> getSubTableList() {
        return this.subTableList;
    }

    public void setSubTableList(List<WFSubTableList> paramList) {
        this.subTableList = paramList;
    }
}
