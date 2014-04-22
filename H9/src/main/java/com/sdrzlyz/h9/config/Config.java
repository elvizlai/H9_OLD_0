package com.sdrzlyz.h9.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.sdrzlyz.h9.util.Encrypt;

/**
 * Created by sdrzlyz on 14-4-13.
 */
public class Config {

    private static Config config = new Config();
    private SharedPreferences usrPreference;
    private SharedPreferences sysPreference;
    private String defaultServiceUrl = "http://192.168.5.220/h9/JHSoft.WCF/POSTServiceForAndroid.svc";
    //private String defaultServiceUrl = "http://h9.huagai.com/h9/JHSoft.WCF/POSTServiceForAndroid.svc";


    private Config() {
        usrPreference = ApplictionHandle.getContext().getSharedPreferences("UserInfos", Context.MODE_PRIVATE);
        sysPreference = ApplictionHandle.getContext().getSharedPreferences("SystemInfos", Context.MODE_PRIVATE);
    }

    public static Config getInstance() {
        return config;
    }

    public String getServiceUrl() {
        return sysPreference.getString("ServiceUrl", defaultServiceUrl);
    }

    public void setServiceUrl(String url) {
        sysPreference.edit().putString("ServiceUrl", url).commit();
    }

    public String getAccount() {
        return usrPreference.getString("Account", "");
    }

    public void setAccount(String account) {
        usrPreference.edit().putString("Account", account).commit();
    }

    public String getPassword() {
        return usrPreference.getString("Password", "");
    }

    public void setPassword(String psw) {
        usrPreference.edit().putString("Password", psw).commit();
    }

    public String getSign() {
        return getAccount() + "$" + Encrypt.encryptSHA(Encrypt.decryptPsw(getPassword()));
    }

    public boolean getIsRempsw() {
        return usrPreference.getBoolean("isRemPsw", false);
    }

    public void setIsRempsw(boolean isRemPsw) {
        usrPreference.edit().putBoolean("isRemPsw", isRemPsw).commit();
    }

    public boolean getIsAutologin() {
        return usrPreference.getBoolean("isAutoLogin", false);
    }

    public void setIsAutologin(boolean isAutoLogin) {
        usrPreference.edit().putBoolean("isAutoLogin", isAutoLogin).commit();
    }

    public boolean getIsFirstInstall() {
        return usrPreference.getBoolean("isFirstInstall", true);
    }

    public void setIsFirstInstall(boolean isFirstInstall) {
        usrPreference.edit().putBoolean("isFirstInstall", isFirstInstall).commit();
    }
}
