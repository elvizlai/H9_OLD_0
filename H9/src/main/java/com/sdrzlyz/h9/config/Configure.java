package com.sdrzlyz.h9.config;

import android.app.Activity;
import android.content.Context;

import com.sdrzlyz.h9.util.Encrypt;

/**
 * Created by sdrzlyz on 14-3-24.
 */
public class Configure extends Activity {

    private static Configure configure;
    final private String defaultServiceUrl = "http://h9.huagai.com/h9/JHSoft.WCF/POSTServiceForAndroid.svc";
    //final private String defaultServiceUrl = "http://192.168.5.220/h9/JHSoft.WCF/POSTServiceForAndroid.svc";

    private Configure() {
    }

    public static Configure getInstance() {
        if (configure == null) {
            configure = new Configure();
        }
        return configure;
    }

    public void setServiceUrl(Context context, String serviceUrl) {
        Preferences.getSystemInstance(context).edit().putString("ServiceUrl", serviceUrl).commit();
    }

    public String getServiceUrl(Context context) {
        return Preferences.getSystemInstance(context).getString("ServiceUrl", defaultServiceUrl);
    }

    public String getSign(Context context) {
        //return Preferences.getUserInstance(context).getString("Sign", null);
        return getAccount(context) + "$" + Encrypt.encryptSHA(Encrypt.decryptPsw(getPassword(context)));
    }


    public void setAccount(Context context, String account) {
        Preferences.getUserInstance(context).edit().putString("Account", account).commit();
    }

    public String getAccount(Context context) {
        return Preferences.getUserInstance(context).getString("Account", "");
    }


    public void setPassword(Context context, String password) {
        Preferences.getUserInstance(context).edit().putString("Password", password).commit();
    }

    public String getPassword(Context context) {
        return Preferences.getUserInstance(context).getString("Password", "");
    }

    public void setIsFirstInstall(Context context, boolean isFirstInstall) {
        Preferences.getUserInstance(context).edit().putBoolean("isFirstInstall", isFirstInstall).commit();
    }

    public boolean getIsFirstInstall(Context context) {
        return Preferences.getUserInstance(context).getBoolean("isFirstInstall", true);
    }


    public void setIsRempsw(Context context, boolean isRemPsw) {
        Preferences.getUserInstance(context).edit().putBoolean("isRemPsw", isRemPsw).commit();
    }

    public boolean getIsRempsw(Context context) {
        return Preferences.getUserInstance(context).getBoolean("isRemPsw", false);
    }

    public void setIsAutologin(Context context, boolean isAutoLogin) {
        Preferences.getUserInstance(context).edit().putBoolean("isAutoLogin", isAutoLogin).commit();
    }

    public boolean getIsAutologin(Context context) {
        return Preferences.getUserInstance(context).getBoolean("isAutoLogin", false);
    }

    public boolean getIsAbnormalExit(Context context) {
        return Preferences.getUserInstance(context).getBoolean("isAbnormalExit", false);
    }

    public void setIsAbnormalExit(Context context, boolean isAbnormalExit) {
        Preferences.getUserInstance(context).edit().putBoolean("isAbnormalExit", isAbnormalExit).commit();
    }

    public long getTimeDiff(Context context) {
        return Preferences.getUserInstance(context).getLong("timeDiff", 0L);
    }

    public void setTimeDiff(Context context, long timeDiff) {
        Preferences.getUserInstance(context).edit().putLong("timeDiff", timeDiff);
    }

}