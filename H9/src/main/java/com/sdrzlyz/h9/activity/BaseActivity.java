package com.sdrzlyz.h9.activity;

import android.app.Activity;

/**
 * Created by sdrzlyz on 14-4-12.
 */
public abstract class BaseActivity extends Activity {

    protected abstract void restoreState();

    protected abstract void initView();

    protected abstract void setListener();
}
