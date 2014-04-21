package com.sdrzlyz.h9.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sdrzlyz.h9.entity.MessagesInfo;
import com.sdrzlyz.h9.entity.WorkFlowButton;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.impl.WorkFlowService;

/**
 * Created by sdrzlyz on 14-4-15.
 */
public class GetWorkFlowDeal extends AsyncTask<Void, Integer, Object> {

    private onLoadCompleteListener loadListener;

    private WorkFlowButton workFlowButton;
    private ProgressDialog progressDialog;
    private Context context;
    private MessagesInfo messagesInfo;

    public GetWorkFlowDeal(Context context, WorkFlowButton workFlowButton) {
        this.context = context;
        this.workFlowButton = workFlowButton;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "正在加载", "请稍等......");
    }

    @Override
    protected Object doInBackground(Void... voids) {
        try {
            return WorkFlowService.getWorkFlowService().getWorkFlowDeal(workFlowButton);
        } catch (POAException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(Object result) {
        progressDialog.dismiss();
        //super.onPostExecute(o);

        if (result != null) {
            messagesInfo = (MessagesInfo) result;
        }

        if (loadListener != null) {
            loadListener.loadComplete();
        }
    }


    public void setOnLoadCompleteListerner(onLoadCompleteListener dataComplete) {
        this.loadListener = dataComplete;
    }

    public MessagesInfo getMessagesInfo() {
        return messagesInfo;
    }

    public int getSuccess() {
        return messagesInfo.getSuccess();
    }

    public String getMes() {
        return messagesInfo.getMes();
    }

    public interface onLoadCompleteListener {
        public void loadComplete();
    }
}
