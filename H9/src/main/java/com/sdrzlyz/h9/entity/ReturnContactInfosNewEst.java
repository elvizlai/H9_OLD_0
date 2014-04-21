package com.sdrzlyz.h9.entity;

import java.util.List;

/**
 * Created by huagai on 14-4-18.
 */
public class ReturnContactInfosNewEst extends MessagesInfo{
    private List addressListGroupInfos;
    private List companyContacts;
    private List crmContacts;
    private List departs;
    private boolean hasCompanyMore;
    private boolean hasCrmMore;
    private boolean hasDepartMore;
    private boolean hasOWnMore;
    private boolean hasPublicMore;
    private List ownContacts;
    private List positions;
    private List publicContacts;
    private List stations;


    public List getAddressListGroupInfos()
    {
        return addressListGroupInfos;
    }

    public List getCompanyContacts()
    {
        return companyContacts;
    }

    public List getCrmContacts()
    {
        return crmContacts;
    }

    public List getDeparts()
    {
        return departs;
    }

    public List getOwnContacts()
    {
        return ownContacts;
    }

    public List getPositions()
    {
        return positions;
    }

    public List getPublicContacts()
    {
        return publicContacts;
    }

    public List getStations()
    {
        return stations;
    }

    public boolean isHasCompanyMore()
    {
        return hasCompanyMore;
    }

    public boolean isHasCrmMore()
    {
        return hasCrmMore;
    }

    public boolean isHasDepartMore()
    {
        return hasDepartMore;
    }

    public boolean isHasOWnMore()
    {
        return hasOWnMore;
    }

    public boolean isHasPublicMore()
    {
        return hasPublicMore;
    }

    public void setAddressListGroupInfos(List list)
    {
        addressListGroupInfos = list;
    }

    public void setCompanyContacts(List list)
    {
        companyContacts = list;
    }

    public void setCrmContacts(List list)
    {
        crmContacts = list;
    }

    public void setDeparts(List list)
    {
        departs = list;
    }

    public void setHasCompanyMore(boolean flag)
    {
        hasCompanyMore = flag;
    }

    public void setHasCrmMore(boolean flag)
    {
        hasCrmMore = flag;
    }

    public void setHasDepartMore(boolean flag)
    {
        hasDepartMore = flag;
    }

    public void setHasOWnMore(boolean flag)
    {
        hasOWnMore = flag;
    }

    public void setHasPublicMore(boolean flag)
    {
        hasPublicMore = flag;
    }

    public void setOwnContacts(List list)
    {
        ownContacts = list;
    }

    public void setPositions(List list)
    {
        positions = list;
    }

    public void setPublicContacts(List list)
    {
        publicContacts = list;
    }

    public void setStations(List list)
    {
        stations = list;
    }

}
