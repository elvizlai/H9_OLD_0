package com.sdrzlyz.h9.impl;

import com.sdrzlyz.h9.config.Config;
import com.sdrzlyz.h9.entity.MessagesInfo;
import com.sdrzlyz.h9.entity.WFListResult;
import com.sdrzlyz.h9.entity.WFNextStepListResult;
import com.sdrzlyz.h9.entity.WorkFlowButton;
import com.sdrzlyz.h9.entity.WorkFlowDetailInfoNew;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.net.HttpClient;
import com.sdrzlyz.h9.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sdrzlyz on 14-4-10.
 * 主要用来处理工作流部分的问题
 */
public class WorkFlowService {

    private static WorkFlowService requestService = new WorkFlowService();
    private static String serviceUrl;
    private static String sign;

    private WorkFlowService() {
        serviceUrl = Config.getInstance().getServiceUrl();
        sign = Config.getInstance().getSign();
    }


    public static WorkFlowService getWorkFlowService() {
        if (!sign.equals(Config.getInstance().getSign()) || !serviceUrl.equals(Config.getInstance().getServiceUrl())) {
            return requestService = new WorkFlowService();
        } else {
            return requestService;
        }
    }

    /**
     * @param flag
     * @param lastTime
     * @return 得到待办流程列表
     * @throws POAException
     */
    public MessagesInfo getWFList(int flag, String lastTime) throws POAException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sign", sign);
            jsonObject.put("count", 20);//一次最多加载的数量
            jsonObject.put("flag", flag);//不明
            jsonObject.put("lastTime", lastTime);//这个时间用来表明返回这个时间点以前的待办流程
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient httpClient = new HttpClient();
        httpClient.setTimeout(15000);
        try {
            String resFromService = httpClient.request(serviceUrl + "/getWFList", jsonObject.toString());
            return JSONUtil.parse(resFromService, WFListResult.class);
        } catch (POAException e) {
            throw e;
        }
    }

    /**
     * @param appID
     * @return 得到待办流程详情
     * @throws POAException
     */
    public MessagesInfo getWFDetailInfoNew(String appID) throws POAException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sign", sign);
            jsonObject.put("appID", appID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpClient httpClient = new HttpClient();
        httpClient.setTimeout(15000);

        try {
            String resFromService = httpClient.request(serviceUrl + "/getWFDetailInfoNew", jsonObject.toString());
            return JSONUtil.parse(resFromService, WorkFlowDetailInfoNew.class);
        } catch (POAException e) {
            throw e;
        }
    }

    /**
     * @param appID
     * @param btnType
     * @return 得到待办流程下一步的信息
     * @throws POAException
     */
    public MessagesInfo getWFStepInfo(String appID, String btnType) throws POAException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sign", sign);
            jsonObject.put("appID", appID);
            jsonObject.put("ButtonType", btnType);
            jsonObject.put("isNewFlag", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpClient httpClient = new HttpClient();
        httpClient.setTimeout(15000);

        try {
            String resFromService = httpClient.request(serviceUrl + "/GetWFStepInfo", jsonObject.toString());
            return JSONUtil.parse(resFromService, WFNextStepListResult.class);
        } catch (POAException e) {
            throw e;
        }

    }

    /**
     * @param workFlowButton
     * @return 处理待办流程，得到反馈信息
     * @throws POAException
     */
    public MessagesInfo getWorkFlowDeal(WorkFlowButton workFlowButton) throws POAException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sign", sign);
            jsonObject.put("button", JSONUtil.format(workFlowButton));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpClient httpClient = new HttpClient();
        httpClient.setTimeout(15000);

        try {
            String resFromService = httpClient.request(serviceUrl + "/WorkFlowDeal", jsonObject.toString());
            return JSONUtil.parse(resFromService, MessagesInfo.class);
        } catch (POAException e) {
            throw e;
        }
    }

}
