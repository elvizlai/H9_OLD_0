package com.sdrzlyz.h9.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.entity.DetailCallNew;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.impl.MessageService;
import com.sdrzlyz.h9.util.ToastUtil;

/**
 * Created by sdrzlyz on 14-4-14.
 */
public class MsgDetail extends BaseActivity {
    private TextView senderName, startTime, msg_content, relatedMsg, related_people;
    private Button reply, replyAll, collectMsg, hasReadedMsg;
    private String id, fromType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_detail);
        initView();
        restoreState();
        setListener();

        getMsgDetail(id, fromType);
    }

    @Override
    protected void initView() {
        senderName = (TextView) findViewById(R.id.senderName);

        startTime = (TextView) findViewById(R.id.startTime);
        msg_content = (TextView) findViewById(R.id.msg_content);
        relatedMsg = (TextView) findViewById(R.id.relatedMsg);
        related_people = (TextView) findViewById(R.id.related_people);

        reply = (Button) findViewById(R.id.reply);
        replyAll = (Button) findViewById(R.id.replyAll);
        collectMsg = (Button) findViewById(R.id.collectMsg);
        hasReadedMsg = (Button) findViewById(R.id.hasReadedMsg);
    }

    @Override
    protected void restoreState() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        fromType = intent.getStringExtra("fromType");

        if (fromType.equals("accept")) {
            hasReadedMsg.setVisibility(View.GONE);
        }
        if (fromType.equals("collect")) {
            collectMsg.setVisibility(View.GONE);
            hasReadedMsg.setText("取消保留");
            //暂时先锁定
            hasReadedMsg.setEnabled(false);
        }
    }

    @Override
    protected void setListener() {
        reply.setOnClickListener(new btnClickedHandler());
        replyAll.setOnClickListener(new btnClickedHandler());
        collectMsg.setOnClickListener(new btnClickedHandler());
        hasReadedMsg.setOnClickListener(new btnClickedHandler());
    }

    private void getMsgDetail(final String id, final String fromType) {
        new AsyncTask<Void, Intent, Object>() {

            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return MessageService.getMessageService().getDetailCallNew(id, fromType);
                } catch (POAException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Object result) {
                if (result != null) {
                    showMsgDetail((DetailCallNew) result);
                }
            }
        }.execute();
    }

    private void showMsgDetail(DetailCallNew detailCallNew) {
        senderName.setText(detailCallNew.getSender());
        startTime.setText(detailCallNew.getStartTime());
        msg_content.setText(detailCallNew.getContent());
        relatedMsg.setText("相关寻呼(" + detailCallNew.getRelatedCallNum() + ")");
        related_people.setText("相关人(" + detailCallNew.getRelatedPeopleNum() + ")");
    }

    private void setHasReadedMsg() {
        new AsyncTask<Void, Integer, Object>() {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(MsgDetail.this, "xxxx", "xxxx");
            }

            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return MessageService.getMessageService().setHasReaded(id, "Call");
                } catch (POAException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Object result) {
                if (result != null) {
                    ToastUtil.showMsg(MsgDetail.this, "已阅");
                    setResult(1);
                    finish();
                } else {
                    ToastUtil.showMsg(MsgDetail.this, "操作失败");
                }
                progressDialog.dismiss();
            }
        }.execute();

    }

    private class btnClickedHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.reply:

                    break;
                case R.id.replyAll:
                    break;
                case R.id.collectMsg:
                    break;
                case R.id.hasReadedMsg:
                    setHasReadedMsg();

                    break;
                default:
                    break;
            }
        }
    }
}
