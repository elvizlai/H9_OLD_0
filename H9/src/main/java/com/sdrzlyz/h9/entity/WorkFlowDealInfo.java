package com.sdrzlyz.h9.entity;

/**
 * Created by sdrzlyz on 14-3-28.
 */
public class WorkFlowDealInfo extends MessagesInfo {

    private String appDealTime;
    private String appDname;
    private String appDuserName;
    private String appIcontent;

    public String getAppDealTime() {
        return this.appDealTime;
    }

    public void setAppDealTime(String paramString) {
        this.appDealTime = paramString;
    }

    public String getAppDname() {
        return this.appDname;
    }

    public void setAppDname(String paramString) {
        this.appDname = paramString;
    }

    public String getAppDuserName() {
        return this.appDuserName;
    }

    public void setAppDuserName(String paramString) {
        this.appDuserName = paramString;
    }

    public String getAppIcontent() {
        return this.appIcontent;
    }

    public void setAppIcontent(String paramString) {
        this.appIcontent = paramString;
    }
}
