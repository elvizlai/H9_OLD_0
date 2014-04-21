package com.sdrzlyz.h9.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sdrzlyz.h9.entity.WFNextStepListResult;
import com.sdrzlyz.h9.entity.WorkFlowNextStep;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.impl.WorkFlowService;
import com.sdrzlyz.h9.util.ToastUtil;

import java.util.List;

/**
 * Created by sdrzlyz on 14-4-15.
 */
public class GetWFNextStepListResult extends AsyncTask<Void, Integer, Object> {

    private onLoadCompleteListener loadListener;
    private Context context;
    private String appID;
    private String btnType;
    private ProgressDialog progressDialog;
    private WFNextStepListResult wfNextStepListResult;

    public GetWFNextStepListResult(Context context, String appID, String btnType) {
        this.context = context;
        this.appID = appID;
        this.btnType = btnType;

    }


    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "请耐心等待", "正在加载......");
    }

    @Override
    protected Object doInBackground(Void... voids) {
        try {
            return WorkFlowService.getWorkFlowService().getWFStepInfo(appID, btnType);
        } catch (POAException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        //super.onPostExecute(o);
        progressDialog.dismiss();

        if (result != null) {
            wfNextStepListResult = (WFNextStepListResult) result;

        } else {
            ToastUtil.showMsg("操作失败!");
        }

        if (loadListener != null) {
            loadListener.loadComplete();
        }

    }


    public WFNextStepListResult getWfNextStepListResult() {
        return wfNextStepListResult;
    }

    public List<WorkFlowNextStep> getWfNextStepList() {
        return wfNextStepListResult.getWfNextStepList();
    }

    public int getSuccess() {
        return wfNextStepListResult.getSuccess();
    }

//    public int getNextStepName(){
//        return wfNextStepListResult.getNextStepName();
//    }
//
//    public int getSuccess(){
//        return wfNextStepListResult.getSuccess();
//    }


    public void setOnLoadCompleteListerner(onLoadCompleteListener dataComplete) {
        this.loadListener = dataComplete;
    }

    public interface onLoadCompleteListener {
        public void loadComplete();
    }

}
