package com.sdrzlyz.h9.impl;

import com.sdrzlyz.h9.config.Config;
import com.sdrzlyz.h9.entity.LoginEstResult;
import com.sdrzlyz.h9.entity.MessagesInfo;
import com.sdrzlyz.h9.entity.UnReadCountNew;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.net.HttpClient;
import com.sdrzlyz.h9.util.JSONUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sdrzlyz on 14-4-17.
 * 主要用来登录、登出、获取消息及工作流数量数量、服务器时间、网络版本号等信息
 */
public class LoginService {
    private static LoginService loginService = new LoginService();
    private static String serviceUrl;
    private static String sign;
    final private int timeOut = 15000;


    private LoginService() {
        serviceUrl = Config.getInstance().getServiceUrl();
        sign = Config.getInstance().getSign();
    }

    public static LoginService getLoginService() {
        if (!sign.equals(Config.getInstance().getSign()) || !serviceUrl.equals(Config.getInstance().getServiceUrl())) {
            return loginService = new LoginService();
        } else {
            return loginService;
        }
    }

    /**
     * @return 服务器中存储的版本号
     * @throws POAException 网络异常
     */
    public String getServerVison() throws POAException {
        String url = Config.getInstance().getServiceUrl().replace("POSTServiceForAndroid.svc", "ClientInfo/VersionInfo.txt");
        try {
            byte[] dataFromURL = new HttpClient().getDataFromURL(url);
            if (dataFromURL.length > 0) {
                return new String(dataFromURL);
            } else {
                return "";
            }
        } catch (POAException exp) {
            throw exp;
        }
    }

    /**
     * @param widthPixels  屏幕宽度
     * @param heightPixels 屏幕高度
     * @return 登录成功后的返回信息
     * @throws POAException
     */
    public MessagesInfo LoginEst(int widthPixels, int heightPixels) throws POAException {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sign", sign);
            jsonObject.put("widthPixels", widthPixels + "");
            jsonObject.put("heightPixels", heightPixels + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpClient httpClient = new HttpClient();
        httpClient.setTimeout(timeOut);
        try {
            String resFromService = httpClient.request(serviceUrl + "/LoginEst", jsonObject.toString());
            return JSONUtil.parse(resFromService, LoginEstResult.class);
        } catch (POAException e) {
            throw e;
        }
    }

    /**
     * @param lastCallTime 上次获取寻呼的时间
     * @param lastWkTime   上次获取工作流的时间
     * @return 时间段内的未读寻呼数、待办流程数量、总未读寻呼数、总待办流程数量、服务器当前时间
     * @throws POAException
     */
    public MessagesInfo getUnreadNew(String lastCallTime, String lastWkTime) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sign", sign);
            jsonObject.put("lastCallTime", lastCallTime);
            jsonObject.put("lastWkTime", lastWkTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpClient httpClient = new HttpClient();
        httpClient.setTimeout(timeOut);

        try {
            String resFromService = httpClient.request(serviceUrl + "/getUnreadNew", jsonObject.toString());
            return JSONUtil.parse(resFromService, UnReadCountNew.class);
        } catch (POAException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @return 退出登录成功的信息
     * @throws POAException
     */
    public MessagesInfo ExitLogin() throws POAException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sign", sign);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpClient httpClient = new HttpClient();
        httpClient.setTimeout(timeOut);

        try {
            String resFromService = httpClient.request(serviceUrl + "/ExitLogin", jsonObject.toString());
            return JSONUtil.parse(resFromService, MessagesInfo.class);
        } catch (POAException e) {
            throw e;
        }
    }

}