package com.sdrzlyz.h9.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.entity.StepDealUsers;
import com.sdrzlyz.h9.entity.WFNextStepListResult;
import com.sdrzlyz.h9.entity.WorkFlowButton;
import com.sdrzlyz.h9.entity.WorkFlowNextStep;
import com.sdrzlyz.h9.webservice.GetWorkFlowDeal;

import java.util.List;

/**
 * Created by sdrzlyz on 14-4-16.
 */
public class WFFinish extends BaseActivity {
    private WorkFlowButton workFlowButton;
    private Button finishBtn;
    private EditText notice_comments;
    private LinearLayout otherRecContainer;
    private CheckBox copyFlag, smsTransactFlag;


    private List<WorkFlowNextStep> workFlowNextStepList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wf_finish);

        restoreState();
        initView();
        setListener();
        showOtherReceiver();
    }

    @Override
    protected void initView() {
        finishBtn = (Button) findViewById(R.id.finishBtn);
        notice_comments = (EditText) findViewById(R.id.notice_comments);
        otherRecContainer = (LinearLayout) findViewById(R.id.otherRecContainer);
        copyFlag = (CheckBox) findViewById(R.id.copyFlag);
        smsTransactFlag = (CheckBox) findViewById(R.id.smsTransactFlag);
    }

    @Override
    protected void restoreState() {
        Bundle bundle = getIntent().getExtras();
        try {
            workFlowButton = (WorkFlowButton) bundle.getSerializable("btnInfo");
            String comments = bundle.getString("comments", "");
            workFlowButton.setSuccess(1);
            workFlowButton.setAppIdea(comments);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void setListener() {
        finishBtn.setOnClickListener(new btnClickedHandler());
    }

    private void showOtherReceiver() {
        StepDealUsers callUsers = workFlowButton.getCallUsers();

        workFlowNextStepList = callUsers.getStepUser();

        int size = workFlowNextStepList.size();


        //workFlowNextStepList.get(i).setCallFlag();

        for (int i = 0; i < size; i++) {
            CheckBox checkBox = new CheckBox(WFFinish.this);
            checkBox.setText(workFlowNextStepList.get(i).getAcceptUserInfo().get(0).getAppFieldValue() + "        " + workFlowNextStepList.get(i).getNextStepName());
            workFlowNextStepList.get(i).setSuccess(1);
            workFlowNextStepList.get(i).getAcceptUserInfo().get(0).setSuccess(1);
            final int finalI = i;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    String flag = "0";
                    if (isChecked) {
                        flag = "1";
                    }
                    workFlowNextStepList.get(finalI).setCallFlag(flag);
                }
            });
            otherRecContainer.addView(checkBox);
        }

    }

    private class btnClickedHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //设置是否允许拷贝
            workFlowButton.setCopyFlag("" + copyFlag.isChecked());

            //是否短信通知
            workFlowButton.setSmsTransactFlag("" + smsTransactFlag.isChecked());

            //通知内容
            workFlowButton.setPromptContent(notice_comments.getText().toString());

            //是否短信通知相关人
            WFNextStepListResult wfNextStepListResult = new WFNextStepListResult();

            wfNextStepListResult.setWfNextStepList(workFlowNextStepList);

            workFlowButton.setNextStepList(wfNextStepListResult);

            final GetWorkFlowDeal getWorkFlowDeal = new GetWorkFlowDeal(WFFinish.this, workFlowButton);
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
    }
}
