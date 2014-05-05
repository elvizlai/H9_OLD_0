package com.sdrzlyz.h9.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.adapter.MsgAdapter;
import com.sdrzlyz.h9.adapter.ViewPagerAdapter;
import com.sdrzlyz.h9.adapter.WFAdapter;
import com.sdrzlyz.h9.config.Config;
import com.sdrzlyz.h9.database.ContactsDB;
import com.sdrzlyz.h9.entity.CallNew;
import com.sdrzlyz.h9.entity.WorkFlowList;
import com.sdrzlyz.h9.service.MainService;
import com.sdrzlyz.h9.test.Test;
import com.sdrzlyz.h9.view.RefreshableView;
import com.sdrzlyz.h9.webservice.GetMsgList;
import com.sdrzlyz.h9.webservice.GetWFList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdrzlyz on 14-4-12.
 */
public class H9Main extends BaseActivity {
    final private String STATE_FROM_ACTIVITY = "com.sdrzlyz.h9.activity.H9Main";
    final private String STATE_FROM_MAINSERVICE = "com.sdrzlyz.h9.service.MainService";
    private long mExitTime;
    private GetMsgList getMsgList;
    private GetWFList getWFList;
    private String searchStr = "";

    private Button unReadMsgBtn, acceptMsgBtn, collectMsgBtn;//三个按钮对应未读、已读、收藏

    private RefreshableView wf_refreshableView;//工作流部分的刷新
    private SwipeRefreshLayout msg_refreshable_view;//消息部分的刷新
    private List<View> viewList;//3个页面的view
    private List<String> titleList;//3个页面对应的标题

    private StateChangedReceiver stateChangedReceiver;//广播


    private int currentItem = 1;//viewPager加载时候的默认显示页
    private int msgType = 2;//消息页面默认显示的消息类型，2表示未读消息 1表示已读消息 3表示收藏消息

    private ViewPager mainView;//主页面
    private ViewPagerAdapter viewPagerAdapter;//
    private ListView msgListView, wfListView;//消息页面与工作流页面

    /**
     * 新消息的条数
     */
    private long newMsgNum = 0L;
    private long newWfNum = 0L;

    /**
     * 总消息条数
     */
    private long totalMsgNum = 0L;
    private long totalWFNum = 0L;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Test.LogD("requestCode:" + requestCode + ",resultCode:" + resultCode);
        if (requestCode == 1 && resultCode == 1) {
            Test.LogD("说明消息需要刷新一下!,延时1秒后刷新");
            getMsgList();
        }

        if (requestCode == 2 && resultCode == 2) {
            Test.LogD("说明工作流需要刷新一下!");
            getWFList();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
        Test.LogD("onCreate........");

        //第一次安装的话自动获取一次通讯录
        if (Config.getInstance().getIsFirstInstall()) {
            Config.getInstance().setIsFirstInstall(false);
            //todo
            if (ContactsDB.getInstance().createTable()) {
                ContactsDB.getInstance().updateContacts();
            }else {
                System.out.println("不需要更新联系人");
            }
        }


        Intent intent = new Intent(H9Main.this, MainService.class);
        startService(intent);

        stateChangedBroadcastReceiver(true);

        //初始化列表及标题栏
        initViewList();
        initTitleList();
        initView();
        setListener();
        //第一次登录时需要获取信息
        getMsgList();
        getWFList();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Test.LogD("H9Main onNewIntent......");

        currentItem = intent.getIntExtra("currentItem", 1);

        if (currentItem == 0) {
            getMsgList();
        } else if (currentItem == 2) {
            getWFList();
        }
        mainView.setCurrentItem(currentItem);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Test.LogD("H9Main onStart......");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Test.LogD("H9Main onResume......");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Test.LogD("H9Main onStop......");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Test.LogD("H9Main onDestroy......");

        //注销广播监听
        stateChangedBroadcastReceiver(false);
        Intent intent = new Intent(H9Main.this, MainService.class);
        stopService(intent);

        Intent home_screen = new Intent();
        home_screen.setAction(Intent.ACTION_MAIN);
        home_screen.addCategory(Intent.CATEGORY_HOME);
        startActivity(home_screen);

        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        manager.killBackgroundProcesses(getPackageName());
        System.exit(0);

    }

    @Override
    protected void restoreState() {

    }

    public void initViewList() {
        /**
         * 在此处加载3个页面  并初始化内容
         */
        View view_left, view_midddle, view_right;

        LayoutInflater layoutInflater = getLayoutInflater().from(this);

        view_left = layoutInflater.inflate(R.layout.view_left, null);
        view_midddle = layoutInflater.inflate(R.layout.view_middle, null);
        view_right = layoutInflater.inflate(R.layout.view_right, null);


        unReadMsgBtn = (Button) view_left.findViewById(R.id.unRead);
        acceptMsgBtn = (Button) view_left.findViewById(R.id.accept);
        collectMsgBtn = (Button) view_left.findViewById(R.id.collect);


        Button test = (Button) view_midddle.findViewById(R.id.test);


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(H9Main.this, Contacts.class);
                startActivity(intent);
            }
        });

        Button voice = (Button) view_midddle.findViewById(R.id.voice);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(H9Main.this, Voice.class);
                startActivity(intent);
            }
        });

        Button quit = (Button) view_midddle.findViewById(R.id.quit);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                H9Main.this.onDestroy();
            }
        });

        msgListView = (ListView) view_left.findViewById(R.id.msgList);

        msg_refreshable_view = (SwipeRefreshLayout) view_left.findViewById(R.id.msg_refreshable_view);

        msg_refreshable_view.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        msg_refreshable_view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMsgList(msg_refreshable_view);
            }
        });

        wfListView = (ListView) view_right.findViewById(R.id.wfList);
        wf_refreshableView = (RefreshableView) view_right.findViewById(R.id.wf_refreshable_view);

        //TODO
        wf_refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                getWFList();
            }
        }, 2);

        viewList = new ArrayList<View>();
        viewList.add(view_left);
        viewList.add(view_midddle);
        viewList.add(view_right);
    }

    public void initTitleList() {
        /**
         * 此处加载3个页面对应的标题栏
         */

        if (titleList == null)
            titleList = new ArrayList<String>();
        titleList.clear();

        if (msgType == 1 || msgType == 3) {
            titleList.add(msgType == 1 ? "已收寻呼" : "保留寻呼");
        } else {
            titleList.add("未阅寻呼(" + totalMsgNum + ")");
        }

        titleList.add("主页");
        titleList.add("(" + totalWFNum + ")" + "待办流程");
    }

    @Override
    protected void initView() {
        mainView = (ViewPager) findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(viewList, titleList);
        mainView.setAdapter(viewPagerAdapter);
        mainView.setCurrentItem(currentItem);
    }

    @Override
    protected void setListener() {
        unReadMsgBtn.setOnClickListener(new btnClickedHandler());
        acceptMsgBtn.setOnClickListener(new btnClickedHandler());
        collectMsgBtn.setOnClickListener(new btnClickedHandler());
    }


    private void getMsgList() {
        getMsgList = new GetMsgList(H9Main.this, msgType, searchStr);
        getMsgList.setOnLoadCompleteListerner(new GetMsgList.onLoadCompleteListener() {
            @Override
            public void loadComplete() {
                List<CallNew> returnCallList = null;
                if (getMsgList.getReturnCallListNew() != null) {
                    returnCallList = getMsgList.getReturnCallListNew().getReturnCallList();
                }
                MsgAdapter msgAdapter = new MsgAdapter(H9Main.this, returnCallList, msgType);
                msgListView.setAdapter(msgAdapter);
                msgListView.setOnItemClickListener(new msgItemClickedHandler(returnCallList));
                msg_refreshable_view.setRefreshing(false);
            }
        });
        getMsgList.execute();
    }

    private void getMsgList(SwipeRefreshLayout swipeRefreshLayout) {
        getMsgList = new GetMsgList(swipeRefreshLayout, msgType, searchStr);
        getMsgList.setOnLoadCompleteListerner(new GetMsgList.onLoadCompleteListener() {
            @Override
            public void loadComplete() {

                List<CallNew> returnCallList = null;
                if (getMsgList.getReturnCallListNew() != null) {
                    returnCallList = getMsgList.getReturnCallListNew().getReturnCallList();
                }
                MsgAdapter msgAdapter = new MsgAdapter(H9Main.this, returnCallList, msgType);
                msgListView.setAdapter(msgAdapter);
                msgListView.setOnItemClickListener(new msgItemClickedHandler(returnCallList));
                msg_refreshable_view.setRefreshing(false);
            }
        });
        getMsgList.execute();
    }

    private void getWFList() {
        getWFList = new GetWFList(wf_refreshableView);
        getWFList.setOnLoadCompleteListerner(new GetWFList.onLoadCompleteListener() {
            @Override
            public void loadComplete() {
                List<WorkFlowList> workFlowList = null;
                if (getWFList.getWfListResult() != null) {
                    workFlowList = getWFList.getWorkFlowList();
                }
                WFAdapter wfAdapter;
                wfAdapter = new WFAdapter(H9Main.this, workFlowList);
                wfListView.setAdapter(wfAdapter.getAdapter());
                wfListView.setOnItemClickListener(new wfItemClickedHandler(workFlowList));
            }
        });
        getWFList.execute();
    }

    private void setClickedBtnEnable() {
        if (msgType == 1) {
            acceptMsgBtn.setEnabled(false);
            unReadMsgBtn.setEnabled(true);
            collectMsgBtn.setEnabled(true);
        }
        if (msgType == 2) {
            acceptMsgBtn.setEnabled(true);
            unReadMsgBtn.setEnabled(false);
            collectMsgBtn.setEnabled(true);
        }
        if (msgType == 3) {
            acceptMsgBtn.setEnabled(true);
            unReadMsgBtn.setEnabled(true);
            collectMsgBtn.setEnabled(false);
        }
    }


    private void stateChangedBroadcastReceiver(boolean startOrClose) {
        if (stateChangedReceiver == null) {
            stateChangedReceiver = new StateChangedReceiver();
        }

        if (!startOrClose) {
            //注销监听器
            unregisterReceiver(stateChangedReceiver);
        } else {
            System.out.println("------------------------>H9Main 开始监听广播");
            IntentFilter filter = new IntentFilter(STATE_FROM_MAINSERVICE);
            H9Main.this.registerReceiver(stateChangedReceiver, filter);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class btnClickedHandler implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.unRead:
                    msgType = 2;
                    setClickedBtnEnable();
                    getMsgList();
                    break;
                case R.id.accept:
                    msgType = 1;
                    setClickedBtnEnable();
                    getMsgList();
                    break;
                case R.id.collect:
                    msgType = 3;
                    setClickedBtnEnable();
                    getMsgList();
                    break;
                default:
                    break;
            }
            initTitleList();
            viewPagerAdapter.notifyDataSetChanged();
        }
    }

    private class msgItemClickedHandler implements AdapterView.OnItemClickListener {
        List<CallNew> returnCallList;

        private msgItemClickedHandler(List<CallNew> returnCallList) {
            this.returnCallList = returnCallList;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            String fromType = "unRead";
            if (msgType == 1) {
                fromType = "accept";
            } else if (msgType == 3) {
                fromType = "collect";
            }
            if (returnCallList == null) {
                return;
            }
            String id = returnCallList.get(position).getId();
            Intent intent = new Intent();
            intent.putExtra("id", id);
            intent.putExtra("fromType", fromType);
            intent.setClass(H9Main.this, MsgDetail.class);
            startActivityForResult(intent, 1);
        }
    }

    private class wfItemClickedHandler implements AdapterView.OnItemClickListener {
        private List<WorkFlowList> workFlowList;

        private wfItemClickedHandler(List<WorkFlowList> workFlowList) {
            this.workFlowList = workFlowList;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            if (workFlowList.size() == 0) {
                return;
            }
            Intent intent = new Intent(H9Main.this, WFDetail.class);
            intent.putExtra("appID", workFlowList.get(position).getAppID());
            startActivityForResult(intent, 2);
        }
    }

    private class StateChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("前台收到广播:" + context + "," + intent);
            Bundle bundle = intent.getExtras();
            if (bundle.getBoolean("NEED_REFRESH", false)) {
                totalMsgNum = bundle.getLong("CallNumTotal", 0L);
                totalWFNum = bundle.getLong("WfNumTotal", 0L);
                System.out.println("寻呼:" + totalMsgNum + ",待办流程:" + totalWFNum + " 需要更新");
                msgType = 2;
                initTitleList();
                viewPagerAdapter.notifyDataSetChanged();
            }
        }
    }


}