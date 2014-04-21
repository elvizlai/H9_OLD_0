package com.sdrzlyz.h9.entity;

/**
 * Created by sdrzlyz on 14-4-18.
 */
public class CompanyContact extends ContactMainInfo {
    private String departId;
    private String departName;
    private int mainDep;
    private int userOrder;

    public String getDepartId()
    {
        return this.departId;
    }

    public String getDepartName()
    {
        return this.departName;
    }

    public int getMainDep()
    {
        return this.mainDep;
    }

    public int getUserOrder()
    {
        return this.userOrder;
    }

    public void setDepartId(String paramString)
    {
        this.departId = paramString;
    }

    public void setDepartName(String paramString)
    {
        this.departName = paramString;
    }

    public void setMainDep(int paramInt)
    {
        this.mainDep = paramInt;
    }

    public void setUserOrder(int paramInt)
    {
        this.userOrder = paramInt;
    }
}
