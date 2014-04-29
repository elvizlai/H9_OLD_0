package com.sdrzlyz.h9.activity;

import android.app.Activity;

/**
 * Created by sdrzlyz on 14-4-12.
 */
public abstract class BaseActivity extends Activity {

    //恢复数据，一般是本地化存储的模块
    protected abstract void restoreState();

    //初始化界面
    protected abstract void initView();

    //绑定事件监听
    protected abstract void setListener();
}
