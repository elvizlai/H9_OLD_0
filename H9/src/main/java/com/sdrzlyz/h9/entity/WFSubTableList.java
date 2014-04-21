package com.sdrzlyz.h9.entity;

import java.util.List;

/**
 * Created by sdrzlyz on 14-3-28.
 */
public class WFSubTableList {
    private SeparatorBar separatorBar;
    private List<WFRecordEntity> subFieldList;

    public SeparatorBar getSeparatorBar() {
        return this.separatorBar;
    }

    public void setSeparatorBar(SeparatorBar paramSeparatorBar) {
        this.separatorBar = paramSeparatorBar;
    }

    public List<WFRecordEntity> getSubFieldList() {
        return this.subFieldList;
    }

    public void setSubFieldList(List<WFRecordEntity> paramList) {
        this.subFieldList = paramList;
    }
}
