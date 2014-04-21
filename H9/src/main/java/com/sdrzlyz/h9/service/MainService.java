package com.sdrzlyz.h9.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by sdrzlyz on 14-4-13.
 */
public class MainService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
