package com.sdrzlyz.h9.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.entity.WorkFlowButton;
import com.sdrzlyz.h9.entity.WorkFlowDealInfo;
import com.sdrzlyz.h9.entity.WorkFlowFieldInfo;
import com.sdrzlyz.h9.test.Test;
import com.sdrzlyz.h9.util.ToastUtil;
import com.sdrzlyz.h9.webservice.GetWFDetail;
import com.sdrzlyz.h9.webservice.GetWorkFlowDeal;

import java.util.Iterator;
import java.util.List;

/**
 * Created by sdrzlyz on 14-4-15.
 */
public class WFDetail extends BaseActivity {
    List<WorkFlowButton> workFlowButtonList;
    private String appID;
    private GetWFDetail getWFDetail;
    private LinearLayout btnListContainer, detailsViewContainer, historysViewContainer;
    private EditText wf_comments;
    private TextView wf_title_and_step;
    private Button show_details_btn, show_history_btn;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 2) {
            setResult(2);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wf_detail);

        restoreState();
        initView();
        getWFDetail(appID);
        setListener();
    }

    @Override
    protected void initView() {
        btnListContainer = (LinearLayout) findViewById(R.id.btnListContainer);
        wf_comments = (EditText) findViewById(R.id.wf_comments);
        wf_title_and_step = (TextView) findViewById(R.id.wf_title_and_step);

        show_details_btn = (Button) findViewById(R.id.show_details_btn);
        detailsViewContainer = (LinearLayout) findViewById(R.id.detailsViewContainer);
        show_history_btn = (Button) findViewById(R.id.show_history_btn);
        historysViewContainer = (LinearLayout) findViewById(R.id.historysViewContainer);
    }

    @Override
    protected void restoreState() {
        Intent intent = getIntent();
        appID = intent.getStringExtra("appID");
    }

    @Override
    protected void setListener() {
        show_details_btn.setOnClickListener(new btnClickedHandler());
        show_history_btn.setOnClickListener(new btnClickedHandler());
    }


    public void getWFDetail(String appID) {
        getWFDetail = new GetWFDetail(WFDetail.this, appID);
        getWFDetail.setOnLoadCompleteListerner(new GetWFDetail.onLoadCompleteListener() {
            @Override
            public void loadComplete() {
                Test.LogD("getWFDetail:" + getWFDetail);
                //看下是否正常获取了
                if (getWFDetail.getWorkFlowDetailInfoNew() != null) {
                    showWFDetail();
                } else {
                    finish();
                }
            }
        });
        getWFDetail.execute();
    }

    private void showWFDetail() {
        showWFDetailBtn();
        showTitleandStepInfo();
        showTodoDetails();
        showHistory();
    }

    private void showWFDetailBtn() {
        workFlowButtonList = getWFDetail.getAppButton();
        int btnSize = workFlowButtonList.size();
        Button[] wfBtn = new Button[btnSize];
        for (int i = 0; i < btnSize; i++) {
            wfBtn[i] = new Button(this);
            wfBtn[i].setId(i);
            wfBtn[i].setTag(workFlowButtonList.get(i).getButtonId());//tag中放入btntype
            wfBtn[i].setText(workFlowButtonList.get(i).getButtonValue());
            wfBtn[i].setTag(workFlowButtonList.get(i).getButtonId());
            //通过这个标签来判断是否是终结状态。
            //应该通过callUsers是否为null来判断是否有下一步动作
            //第二个用来规避xxx todo 还没有完全确定
            if (workFlowButtonList.get(i).getCallUsers() == null) {
                if (workFlowButtonList.get(i).getAppIdea().equals("1")) {
                    wfBtn[i].setOnClickListener(new wfNextStepBtnClickedHandler());
                } else {
                    wfBtn[i].setOnClickListener(new verifyBtnClickedHandler());
                }

            } else {
                wfBtn[i].setOnClickListener(new wfFinishBtnClickedHandler());
            }
            btnListContainer.addView(wfBtn[i]);
        }
    }

    private void showTitleandStepInfo() {
        String title_stepInfo = "<font color='grey'>流程标题：</font>" + "<font color='black'>" + getWFDetail.getAppTitle() + "</font>" + "<br>" + "<font color='grey'>当前步骤：</font>" + "<font color='black'>" + getWFDetail.getHttAppDName() + "</font>";
        TextView wf_title_and_step = (TextView) findViewById(R.id.wf_title_and_step);
        wf_title_and_step.setText(Html.fromHtml(title_stepInfo));
    }


    private void showTodoDetails() {
        List<WorkFlowFieldInfo> workFlowFieldInfos = getWFDetail.getWfFieldListResult().getMainTableList().getMainFieldList().getWfRecord();
        Iterator iterator = workFlowFieldInfos.iterator();
        Resources res = getResources();
        while (iterator.hasNext()) {
            WorkFlowFieldInfo workFlowFieldInfo = (WorkFlowFieldInfo) iterator.next();
            if (!workFlowFieldInfo.getAppFieldValue().equals("")) {
                LinearLayout contains = new LinearLayout(this);
                contains.setOrientation(LinearLayout.HORIZONTAL);
                TextView title = new TextView(this);
                title.setTextSize(res.getDimension(R.dimen.todolist_text_size));
                title.setTextColor(Color.GRAY);
                title.setText(workFlowFieldInfo.getAppFieldName() + "：");
                TextView comments = new TextView(this);
                comments.setTextSize(res.getDimension(R.dimen.todolist_text_size));
                comments.setTextColor(Color.BLACK);
                comments.setText(workFlowFieldInfo.getAppFieldValue());
                contains.addView(title);
                contains.addView(comments);
                detailsViewContainer.addView(contains);
            }
        }
    }

    private void showHistory() {
        List<WorkFlowDealInfo> workFlowDealInfo = getWFDetail.getWorkFlowDetailInfoNew().getWfDealtList();
        int size = workFlowDealInfo.size();
        Resources res = getResources();
        for (int i = 0; i < size - 1; i++) {
            String str = workFlowDealInfo.get(i).getAppIcontent();
            if (str.equals("")) {
                str = "意见为空";
            }
            TextView comments = new TextView(this);
            comments.setText(str);
            comments.setTextSize(res.getDimension(R.dimen.comments_text_size));
            comments.setTextColor(Color.BLACK);

            LinearLayout details = new LinearLayout(this);
            details.setOrientation(LinearLayout.HORIZONTAL);
            TextView setpAndName = new TextView(this);
            setpAndName.setText(workFlowDealInfo.get(i).getAppDname() + "-" + workFlowDealInfo.get(i).getAppDuserName() + "    ");
            setpAndName.setTextColor(Color.GRAY);
            setpAndName.setTextSize(res.getDimension(R.dimen.dealtime_text_size));

            TextView dealTime = new TextView(this);
            dealTime.setText(workFlowDealInfo.get(i).getAppDealTime());
            dealTime.setTextColor(Color.GRAY);
            dealTime.setTextSize(res.getDimension(R.dimen.dealtime_text_size));

            details.addView(setpAndName);
            details.addView(dealTime);
            historysViewContainer.addView(comments);
            historysViewContainer.addView(details);
        }
    }

    private class wfNextStepBtnClickedHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Test.LogD(view.getId() + " ID");

            String comments = wf_comments.getText().toString();
            if (comments.equals("")) {
                ToastUtil.showMsg("你必须填上点什么才可以提交......");
                return;
            }

            Intent intent = new Intent(WFDetail.this, WFNextStep.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("btnInfo", workFlowButtonList.get(view.getId()));
            bundle.putSerializable("comments", comments);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }
    }

    private class wfFinishBtnClickedHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            String comments = wf_comments.getText().toString();
            if (comments.equals("")) {
                ToastUtil.showMsg("你必须填上点什么才可以提交......");
                return;
            }

            Intent intent = new Intent(WFDetail.this, WFFinish.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("btnInfo", workFlowButtonList.get(view.getId()));
            bundle.putSerializable("comments", comments);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);

        }
    }

    private class verifyBtnClickedHandler implements View.OnClickListener {
        @Override
        public void onClick(final View view) {
            //showalert
            Dialog alertDialog = new AlertDialog.Builder(WFDetail.this).
                    setTitle("确认提交？").
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String comments = wf_comments.getText().toString();
                            //这种情况下的貌似很特殊啊
                            WorkFlowButton workFlowButton = workFlowButtonList.get(view.getId());
                            workFlowButton.setPromptContent(comments);
                            workFlowButton.setSuccess(1);
                            final GetWorkFlowDeal getWorkFlowDeal = new GetWorkFlowDeal(WFDetail.this, workFlowButton);
                            getWorkFlowDeal.setOnLoadCompleteListerner(new GetWorkFlowDeal.onLoadCompleteListener() {
                                @Override
                                public void loadComplete() {
                                    if (getWorkFlowDeal.getMessagesInfo() != null) {
                                        setResult(2);
                                        finish();
                                    } else {
                                        finish();
                                    }
                                }
                            });
                            getWorkFlowDeal.execute();

                        }
                    }).
                    setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    }).
                    create();
            alertDialog.show();


        }
    }


    private class btnClickedHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.show_details_btn:
                    detailsViewContainer.setVisibility((detailsViewContainer.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
                    break;
                case R.id.show_history_btn:
                    historysViewContainer.setVisibility((historysViewContainer.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

}