package com.sdrzlyz.h9.entity;

/**
 * Created by sdrzlyz on 14-3-26.
 */
public class WorkFlowFieldInfo extends MessagesInfo {
    private static final long serialVersionUID = 1L;
    private String appFieldName;
    private String appFieldType;
    private String appFieldValue;

    public String getAppFieldName() {
        return this.appFieldName;
    }

    public void setAppFieldName(String paramString) {
        this.appFieldName = paramString;
    }

    public String getAppFieldType() {
        return this.appFieldType;
    }

    public void setAppFieldType(String paramString) {
        this.appFieldType = paramString;
    }

    public String getAppFieldValue() {
        return this.appFieldValue;
    }

    public void setAppFieldValue(String paramString) {
        this.appFieldValue = paramString;
    }
}
