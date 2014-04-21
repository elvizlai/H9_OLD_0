package com.sdrzlyz.h9.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.entity.WFNextStepListResult;
import com.sdrzlyz.h9.entity.WorkFlowButton;
import com.sdrzlyz.h9.entity.WorkFlowFieldInfo;
import com.sdrzlyz.h9.entity.WorkFlowNextStep;
import com.sdrzlyz.h9.test.Test;
import com.sdrzlyz.h9.webservice.GetWFNextStepListResult;
import com.sdrzlyz.h9.webservice.GetWorkFlowDeal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdrzlyz on 14-4-15.
 */
public class WFNextStep extends BaseActivity {

    private List<WorkFlowNextStep> workFlowNextSteps;
    private WorkFlowButton workFlowButton;
    private TextView next_step_head_title, next_step_title, next_step_name, retreat_people;
    private Button doneBtn;
    private GetWFNextStepListResult getWFNextStepListResult;
    private String appID;
    private String btnType;
    private RadioGroup staff_list;
    private String comments;
    private EditText stepHint;
    private CheckBox msg_notity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wf_nextstep);

        restoreState();
        initView();
        setListener();
        showWFNextStepInfo();
    }

    @Override
    protected void initView() {
        next_step_head_title = (TextView) findViewById(R.id.next_step_head_title);
        doneBtn = (Button) findViewById(R.id.doneBtn);
        next_step_title = (TextView) findViewById(R.id.next_step_title);
        next_step_name = (TextView) findViewById(R.id.next_step_name);
        staff_list = (RadioGroup) findViewById(R.id.staff_list);
        retreat_people = (TextView) findViewById(R.id.retreat_people);
        stepHint = (EditText) findViewById(R.id.stepHint);
        msg_notity = (CheckBox) findViewById(R.id.msg_notity);
    }

    @Override
    protected void restoreState() {
        Bundle bundle = getIntent().getExtras();

        workFlowButton = (WorkFlowButton) bundle.getSerializable("btnInfo");
        comments = bundle.getString("comments", "");

        Test.LogD("comments:" + comments);

        if (comments.equals("")) {
            comments = "意见为空";
        }
        workFlowButton.setAppIdea(comments);

        appID = workFlowButton.getAppID();
        btnType = workFlowButton.getButtonId();

    }

    @Override
    protected void setListener() {
        doneBtn.setOnClickListener(new btnClickedListener());
    }

    private void showWFNextStepInfo() {
        getWFNextStepListResult = new GetWFNextStepListResult(WFNextStep.this, appID, btnType);
        getWFNextStepListResult.setOnLoadCompleteListerner(new GetWFNextStepListResult.onLoadCompleteListener() {
            @Override
            public void loadComplete() {
                if (getWFNextStepListResult.getWfNextStepListResult() != null) {
                    workFlowNextSteps = getWFNextStepListResult.getWfNextStepList();
                    Test.LogD("返回成功");
                    changeTitle();
                } else {
                    finish();
                }

            }
        });
        getWFNextStepListResult.execute();
    }

    private void changeTitle() {
        if (btnType.equals("5")) {
            next_step_head_title.setText("退回");
            next_step_title.setText("退回步骤: ");
            retreat_people.setVisibility(View.VISIBLE);
        } else {
            next_step_head_title.setText("转下一步");
            next_step_title.setText("步骤名称: ");
        }
        next_step_name.setText(workFlowNextSteps.get(0).getNextStepName());

        List<WorkFlowFieldInfo> workFlowFieldInfo = workFlowNextSteps.get(0).getAcceptUserInfo();

        int size = workFlowFieldInfo.size();
        int btnID = 0;
        for (int i = 0; i < size; i++) {
            RadioButton staffName = new RadioButton(this);
            staffName.setText(workFlowFieldInfo.get(i).getAppFieldValue());
            staffName.setId(btnID);
            if (btnID == 0) {
                staffName.setChecked(true);
            }
            btnID++;
            staff_list.addView(staffName);
        }
    }


    private class btnClickedListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //获取radioButton选择了谁  0表示第一个人，一次类推
            //workFlowNextSteps 列表的大小好像一直为1
            int selected = staff_list.getCheckedRadioButtonId();

            //办理提示
            String stepHintStr = stepHint.getText().toString();
            workFlowButton.setPromptContent(stepHintStr);

            //这个对应 wfNextStepList
            WorkFlowNextStep workFlowFieldInfo = workFlowNextSteps.get(0);

            //是否寻呼通知,默认不通知
            String callFlag = "0";
            if (msg_notity.isChecked()) {
                callFlag = "1";
            }

            workFlowFieldInfo.setCallFlag(callFlag);

            List<WorkFlowFieldInfo> workFlowFieldInfoList = new ArrayList<WorkFlowFieldInfo>();
            workFlowFieldInfoList.add(workFlowFieldInfo.getAcceptUserInfo().get(selected));

            workFlowFieldInfo.setAcceptUserInfo(workFlowFieldInfoList);

            WFNextStepListResult wfNextStepListResult = new WFNextStepListResult();

            ArrayList arrayList = new ArrayList();
            arrayList.add(workFlowFieldInfo);
            wfNextStepListResult.setWfNextStepList(arrayList);
            workFlowButton.setNextStepList(wfNextStepListResult);

            workFlowFieldInfo.setSuccess(1);
            wfNextStepListResult.setSuccess(1);
            workFlowButton.setSuccess(1);

            final GetWorkFlowDeal getWorkFlowDeal = new GetWorkFlowDeal(WFNextStep.this, workFlowButton);
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
