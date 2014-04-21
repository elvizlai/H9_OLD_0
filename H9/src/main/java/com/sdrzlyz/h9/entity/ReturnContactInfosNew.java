package com.sdrzlyz.h9.entity;

import java.io.Serializable;

/**
 * Created by huagai on 14-4-17.
 */
public class ReturnContactInfosNew extends MessagesInfo implements Serializable {
    private byte[] contactInfo;

    public byte[] getContactInfo() {
        return this.contactInfo;
    }

    public void setContactInfo(byte[] paramArrayOfByte) {
        this.contactInfo = paramArrayOfByte;
    }
}
