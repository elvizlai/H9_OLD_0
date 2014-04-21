package com.sdrzlyz.h9.entity;

import java.io.Serializable;

/**
 * Created by sdrzlyz on 14-3-26.
 */
public class LoginEstResult extends MessagesInfo implements Serializable {
    private String deptId;
    private String firstOrNewPass;
    private int hasCRM;
    private int hasNotify;
    private int hasSiteControls;
    private int isVisited;
    private String jumpPicType;
    private String jumpPicVersion;
    private String logTitle;
    private String loginPicType;
    private String loginPicVersion;
    private int mesCount;
    private String minLenPass;
    private String ownMobileNumber;
    private String userId;
    private String userName;
    private int workCount;


    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String s) {
        deptId = s;
    }

    public String getFirstOrNewPass() {
        return firstOrNewPass;
    }

    public void setFirstOrNewPass(String s) {
        firstOrNewPass = s;
    }

    public int getHasCRM() {
        return hasCRM;
    }

    public int getHasNotify() {
        return hasNotify;
    }

    public void setHasNotify(int i) {
        hasNotify = i;
    }

    public int getHasSiteControls() {
        return hasSiteControls;
    }

    public void setHasSiteControls(int i) {
        hasSiteControls = i;
    }

    public int getIsVisited() {
        return isVisited;
    }

    public void setIsVisited(int i) {
        isVisited = i;
    }

    public String getJumpPicType() {
        return jumpPicType;
    }

    public void setJumpPicType(String s) {
        jumpPicType = s;
    }

    public String getJumpPicVersion() {
        return jumpPicVersion;
    }

    public void setJumpPicVersion(String s) {
        jumpPicVersion = s;
    }

    public String getLogTitle() {
        return logTitle;
    }

    public void setLogTitle(String s) {
        logTitle = s;
    }

    public String getLoginPicType() {
        return loginPicType;
    }

    public void setLoginPicType(String s) {
        loginPicType = s;
    }

//    public void setHasCRM(int i)
//    {
//        hasCRM = i;
//    }

    public String getLoginPicVersion() {
        return loginPicVersion;
    }

    public void setLoginPicVersion(String s) {
        loginPicVersion = s;
    }

    public int getMesCount() {
        return mesCount;
    }

    public void setMesCount(int i) {
        mesCount = i;
    }

    public String getMinLenPass() {
        return minLenPass;
    }

    public void setMinLenPass(String s) {
        minLenPass = s;
    }

    public String getOwnMobileNumber() {
        return ownMobileNumber;
    }

    public void setOwnMobileNumber(String s) {
        ownMobileNumber = s;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String s) {
        userId = s;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String s) {
        userName = s;
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setWorkCount(int i) {
        workCount = i;
    }

}
