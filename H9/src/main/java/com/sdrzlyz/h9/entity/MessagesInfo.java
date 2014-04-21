package com.sdrzlyz.h9.entity;

import java.io.Serializable;

/**
 * Created by sdrzlyz on 14-3-26.
 */
public class MessagesInfo implements Serializable {
    private String mes;
    private int success = 0;

    public String getMes() {
        return this.mes;
    }

    public void setMes(String paramString) {
        this.mes = paramString;
    }

    public int getSuccess() {
        return this.success;
    }

    public void setSuccess(int paramInt) {
        this.success = paramInt;
    }

}
