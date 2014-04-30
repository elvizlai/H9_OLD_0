package com.sdrzlyz.h9.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.entity.LoginEstResult;
import com.sdrzlyz.h9.entity.MessagesInfo;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.impl.LoginService;
import com.sdrzlyz.h9.test.Test;
import com.sdrzlyz.h9.util.ToastUtil;

/**
 * Created by sdrzlyz on 14-4-12.
 */
public class Loading extends Activity {
    private Button btn_cancle;
    private MessagesInfo messagesInfo;
    private boolean cancleLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);

        initView();
        bindEvent();
        begin2Login();
    }


    protected void initView() {
        btn_cancle = (Button) findViewById(R.id.cancle);
    }


    protected void bindEvent() {
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消登录
                cancleLogin = true;
                finish();
            }
        });
    }

    private void begin2Login() {
        new AsyncTask<Void, Integer, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    String version = LoginService.getLoginService().getServerVison();

                    //这个不需要权限，如果这个不能获取，说明服务器地址配置的不对吧
                    if (version != null) {
                        System.out.println("服务器版本号:" + version);
                    }

                    try {
                        String currentV = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                        System.out.println("本地版本号:" + currentV);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int width = displayMetrics.widthPixels;
                    int height = displayMetrics.heightPixels;

                    Test.LogD("width:" + width + ",height:" + height);

                    messagesInfo = LoginService.getLoginService().LoginEst(width, height);
                    return null;
                } catch (POAException e) {
                    //e.printStackTrace();
                    Test.LogD("error code:" + e.getCode());
                    Test.LogD("error mes:" + e.getMessage());
                    return e;
                }
            }

            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);

                //不为null，说明抛出异常
                if (result != null) {
                    POAException e = (POAException) result;
                    if (e.getCode() == 19) {
                        //说明服务器配置不对
                        setResult(19);
                    } else if (e.getCode() == 3) {
                        //网络异常，一般是网速太慢，超时
                        setResult(3);
                    } else if (e.getCode() == 0) {
                        //可以与服务器通信，但是返回的值确实错误的，比如密码不对，账户不存在等信息
                        setResult(-1);
                    }
                    ToastUtil.showMsg(e.getMessage());
                    finish();
                } else if (messagesInfo.getSuccess() == 1 && !cancleLogin) {
                    //说明正常
                    LoginEstResult loginEstResult = (LoginEstResult) messagesInfo;


                    //保存登录信息
                    //savaPsw();

                    //保存鉴权信息
                    Intent intent = new Intent(Loading.this, H9Main.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("loginEstResult", loginEstResult);
                    intent.putExtras(bundle);
                    //设置标志，存在就拉倒前台，不存在就新建一个
                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                    finish();
                }
            }
        }.execute();
    }

    private void savaPsw() {
//        if (Config.getInstance().getIsRempsw()) {
//            Config.getInstance().setPassword(Encrypt.encryptPsw(password));
//        }
        //这里如果设置else清除的话，会导致后面不能获取鉴权
    }

    //屏蔽返回按键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }

}
