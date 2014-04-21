package com.sdrzlyz.h9.config;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

/**
 * Created by sdrzlyz on 14-3-24.
 */
public class Preferences extends Activity {

    private static SharedPreferences sysPreference;
    private static SharedPreferences usrPreference;


    private Preferences() {
    }

    public static void close() {
        if (usrPreference != null)
            usrPreference = null;
        if (sysPreference != null)
            sysPreference = null;
    }

    public static SharedPreferences getSystemInstance(Context context) {
        if (sysPreference == null)
            sysPreference = (new ContextWrapper(context.getApplicationContext())).getSharedPreferences("SystemInfos", 0);
        return sysPreference;
    }

    public static SharedPreferences getUserInstance(Context context) {
        if (usrPreference == null)
            usrPreference = (new ContextWrapper(context.getApplicationContext())).getSharedPreferences("UserInfos", 0);
        return usrPreference;
    }

}
