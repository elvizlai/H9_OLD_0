package com.sdrzlyz.h9.util;

import android.content.Context;
import android.widget.Toast;

import com.sdrzlyz.h9.config.ApplictionHandle;

/**
 * Created by sdrzlyz on 14-4-12.
 */
public class ToastUtil {
    private static ToastUtil toastUtil;

    private ToastUtil() {

    }

    public static void showMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showMsg(String msg) {
        Toast.makeText(ApplictionHandle.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
