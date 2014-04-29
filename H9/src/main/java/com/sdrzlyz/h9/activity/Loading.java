package com.sdrzlyz.h9.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
                    System.out.println("00000000000000:"+LoginService.getLoginService().getServerVison());

                    messagesInfo = LoginService.getLoginService().LoginEst("", "");
                    return null;
                } catch (POAException e) {
                    e.printStackTrace();
                    Test.LogD("code:" + e.getCode());
                    return e;
                }
            }

            @SuppressWarnings("ggd")
            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);

                //不为null，说明抛出异常
                if (result != null) {
                    POAException e = (POAException) result;
                    if (e.getCode() == 19) {
                        //说明服务器配置不对
                        setResult(19);
                    }
                    if (e.getCode() == 3) {
                        //网络异常，一般是网速太慢，超时
                        setResult(3);
                    }
                    if (e.getCode() == 0) {
                        //说明帐号或密码错误，由于默认返回就是0，此处用0不太合适
                        setResult(-1);
                    }
                    ToastUtil.showMsg(Loading.this, e.getMessage());
                    finish();
                } else if (messagesInfo.getSuccess() == 1) {
                    //说明正常
                    LoginEstResult loginEstResult = (LoginEstResult) messagesInfo;

                    if (!cancleLogin) {
                        //保存登录信息
                        savaPsw();

                        //保存鉴权信息
                        Intent intent = new Intent(Loading.this, H9Main.class);
                        //设置标志，别的activity再操作这个activity，那么就会把它带到前台，而不是新建一个。
                        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        startActivity(intent);
                        finish();
                    }
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
