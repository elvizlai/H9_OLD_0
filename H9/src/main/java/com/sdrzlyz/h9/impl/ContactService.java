package com.sdrzlyz.h9.impl;

import com.sdrzlyz.h9.config.Config;
import com.sdrzlyz.h9.entity.MessagesInfo;
import com.sdrzlyz.h9.entity.ReturnContactInfosNew;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.net.HttpClient;
import com.sdrzlyz.h9.util.JSONUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sdrzlyz on 14-4-17.
 * 主要用来处理通讯录部分的问题
 */
public class ContactService {
    private static ContactService contactService = new ContactService();
    private static String serviceUrl;
    private static String sign;

    private ContactService() {
        serviceUrl = Config.getInstance().getServiceUrl();
        sign = Config.getInstance().getSign();
    }

    public static ContactService getContactService() {
        if (!sign.equals(Config.getInstance().getSign()) || !serviceUrl.equals(Config.getInstance().getServiceUrl())) {
            return contactService = new ContactService();
        } else {
            return contactService;
        }
    }

    public MessagesInfo getContactsForCompress() throws POAException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sign", sign);
            jsonObject.put("hasCompanyMore", 1);
            jsonObject.put("hasCustomerMore", 1);
            jsonObject.put("count", 1);
            jsonObject.put("hasDepartMore", 1);
            jsonObject.put("hasPublicMore", 1);
            jsonObject.put("hasOwnMore", 1);
            jsonObject.put("size", 500);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpClient httpClient = new HttpClient();
        httpClient.setTimeout(15000);

        try {
            String resFromService = httpClient.request(serviceUrl + "/getContactsForCompress", jsonObject.toString());
            return JSONUtil.parse(resFromService, ReturnContactInfosNew.class);
        } catch (POAException e) {
            throw e;
        }

    }

    public MessagesInfo getContactsNewEst(int hasCompanyMore, int hasCustomerMore, int hasDepartMore, int hasPublicMore, int hasOwnMore, int size) throws POAException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sign", sign);
            jsonObject.put("hasCompanyMore", hasCompanyMore);
            jsonObject.put("hasCustomerMore", hasCustomerMore);
            jsonObject.put("count", 0);
            jsonObject.put("hasDepartMore", hasDepartMore);
            jsonObject.put("hasPublicMore", hasPublicMore);
            jsonObject.put("hasOwnMore", hasOwnMore);
            jsonObject.put("size", size);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpClient httpClient = new HttpClient();
        httpClient.setTimeout(15000);

        try {
            String resFromService = httpClient.request(serviceUrl + "/getContactsNewEst", jsonObject.toString());
            return JSONUtil.parse(resFromService, ReturnContactInfosNew.class);
        } catch (POAException e) {
            throw e;
        }
    }
}
