package com.sdrzlyz.h9.impl;

import com.sdrzlyz.h9.config.Config;
import com.sdrzlyz.h9.entity.DetailCallNew;
import com.sdrzlyz.h9.entity.MessagesInfo;
import com.sdrzlyz.h9.entity.ReturnCallListNew;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.net.HttpClient;
import com.sdrzlyz.h9.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sdrzlyz on 14-4-17.
 * 主要用来处理寻呼部分的问题
 */
public class MessageService {
    private static MessageService messageService = new MessageService();
    private static String serviceUrl;
    private static String sign;
    final private int timeOut = 15000;

    private MessageService() {
        serviceUrl = Config.getInstance().getServiceUrl();
        sign = Config.getInstance().getSign();
    }

    public static MessageService getMessageService() {
        if (!sign.equals(Config.getInstance().getSign()) || !serviceUrl.equals(Config.getInstance().getServiceUrl())) {
            return messageService = new MessageService();
        } else {
            return messageService;
        }
    }

    public MessagesInfo getCallListNew(int hasReaded, String searchStr) throws POAException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sign", sign);
            jsonObject.put("hasReaded", hasReaded);//2表示未读  1表示已读  3表示收藏
            jsonObject.put("count", 20);//一次最多加载的数量 原本是20
            jsonObject.put("type", -1);//不明  -1
            jsonObject.put("searchStr", searchStr);//配合搜索标题栏目进行搜索的时候使用
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient httpClient = new HttpClient();
        httpClient.setTimeout(10000);
        try {
            String resFromService = httpClient.request(serviceUrl + "/getCallListNew", jsonObject.toString());
            return JSONUtil.parse(resFromService, ReturnCallListNew.class);
        } catch (POAException e) {
            throw e;
        }
    }

    public MessagesInfo getDetailCallNew(String id, String fromType) throws POAException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sign", sign);
            jsonObject.put("id", id);
            jsonObject.put("fromType", fromType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpClient httpClient = new HttpClient();
        httpClient.setTimeout(15000);

        try {
            String resFromService = httpClient.request(serviceUrl + "/getDetailCallNew", jsonObject.toString());
            return JSONUtil.parse(resFromService, DetailCallNew.class);
        } catch (POAException e) {
            throw e;
        }
    }

    public MessagesInfo setHasReaded(String id, String callType) throws POAException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sign", sign);
            jsonObject.put("id", id);
            jsonObject.put("callType", callType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpClient httpClient = new HttpClient();
        httpClient.setTimeout(10000);

        try {
            String resFromService = httpClient.request(serviceUrl + "/setHasReaded", jsonObject.toString());
            return JSONUtil.parse(resFromService, MessagesInfo.class);
        } catch (POAException e) {
            throw e;
        }
    }
}
