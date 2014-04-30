package com.sdrzlyz.h9.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.activity.H9Main;
import com.sdrzlyz.h9.entity.UnReadCountNew;
import com.sdrzlyz.h9.impl.LoginService;
import com.sdrzlyz.h9.test.Test;

/**
 * Created by sdrzlyz on 14-4-13.
 */
public class MainService extends Service {
    private static Intent mIntent = new Intent();
    final private String STATE_FROM_ACTIVITY = "com.sdrzlyz.h9.activity.H9Main";
    final private String STATE_FROM_MAINSERVICE = "com.sdrzlyz.h9.service.MainService";
    private StateChangedReceiver stateChangedReceiver;
    private UnReadCountNew unReadCountNew;
    private String lastConnect;
    private boolean isThreadRunning = true;
    private long refreshTime = 60000;

    @Override
    public void onCreate() {
        super.onCreate();
        Test.LogD("MainService onCreate");
        //开启广播监听器
        stateChangedBroadcastReceiver(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Test.LogD("MainService onStartCommand");
        new Thread() {
            @Override
            public void run() {
                super.run();
                long callNumTotal = 0L;
                long wfNumTotal = 0L;

                while (isThreadRunning) {
                    unReadCountNew = (UnReadCountNew) LoginService.getLoginService().getUnreadNew(lastConnect, lastConnect);
                    if (unReadCountNew != null) {

                        long callNum = unReadCountNew.getCallnum();
                        long wfnum = unReadCountNew.getWfnum();
                        // long temp_callNumTotal = unReadCountNew.getCallNumTotal();
                        //  long temp_wfNumTotal = unReadCountNew.getWfNumTotal();

                        if (callNum != 0) {
                            System.out.println("有新寻呼");
                            showNewMsgNotification(callNum);
                        }
                        if (wfnum != 0) {
                            System.out.println("有新待办流程");
                            showNewWFNotification(wfnum);
                        }

                        callNumTotal = unReadCountNew.getCallNumTotal();
                        wfNumTotal = unReadCountNew.getWfNumTotal();

                        if (lastConnect == null) {
                            //是空的说明服务第一次启动，需要先通知一下前台消息数量
                            mIntent.putExtra("NEED_REFRESH", true);
                            mIntent.putExtra("CallNumTotal", callNumTotal);
                            mIntent.putExtra("WfNumTotal", wfNumTotal);
                            mIntent.setAction(STATE_FROM_MAINSERVICE);
                            sendBroadcast(mIntent);
                        }
                        lastConnect = unReadCountNew.getServiceTime();
                    } else {
                        System.out.println("网络异常");
                    }
                    try {
                        sleep(refreshTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stateChangedBroadcastReceiver(false);
    }


    private void showNewMsgNotification(long msgNum) {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_launcher, "H9提醒：您有新寻呼", System.currentTimeMillis());
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;

        Intent notificationIntent = new Intent(MainService.this, H9Main.class);
        notificationIntent.putExtra("currentItem", 0);//未读消息的类型，如果是0表示是消息，2表示待办流程
        //设置标志，存在就拉倒前台，不存在就新建一个
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //id的作用是用来鉴别是哪个通知，可以用来删除消息
        //int notifacation_id = 0x8215579;
        //nm.notify(notifacation_id, notification);

        //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        PendingIntent contentIntent = PendingIntent.getActivity(MainService.this, R.string.app_name, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        notification.setLatestEventInfo(MainService.this, "H9", "当前您有" + msgNum + "条新寻呼", contentIntent);
        nm.notify(R.string.app_name, notification);
    }


    private void showNewWFNotification(long wfNum) {

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_launcher, "H9提醒：您有新待办流程", System.currentTimeMillis());
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        Intent intent = new Intent(MainService.this, H9Main.class);
        intent.putExtra("currentItem", 2);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(MainService.this, R.string.app_name, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setLatestEventInfo(MainService.this, "H9", "当前您有" + wfNum + "条新待办流程", contentIntent);
        nm.notify(R.string.app_name, notification);
    }


    private void stateChangedBroadcastReceiver(boolean startOrClose) {
        if (stateChangedReceiver == null) {
            stateChangedReceiver = new StateChangedReceiver();
        }

        if (!startOrClose) {
            //注销监听器
            unregisterReceiver(stateChangedReceiver);
        } else {
            System.out.println("---------------------->MainService 开始监听广播");
            IntentFilter filter = new IntentFilter(STATE_FROM_ACTIVITY);
            registerReceiver(stateChangedReceiver, filter);
        }
    }


    private class StateChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("后台收到广播：" + context + "," + intent);
        }
    }
}
