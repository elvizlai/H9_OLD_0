package com.sdrzlyz.h9.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sdrzlyz.h9.entity.WFFieldListResult;
import com.sdrzlyz.h9.entity.WorkFlowButton;
import com.sdrzlyz.h9.entity.WorkFlowDetailInfoNew;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.impl.WorkFlowService;
import com.sdrzlyz.h9.util.ToastUtil;

import java.util.List;

/**
 * Created by sdrzlyz on 14-4-15.
 */
public class GetWFDetail extends AsyncTask<Void, Integer, Object> {

    private onLoadCompleteListener loadListener;

    private WorkFlowDetailInfoNew workFlowDetailInfoNew;

    private String appID;
    private Context context;
    private ProgressDialog progressDialog;


    public GetWFDetail(Context context, String appID) {
        this.context = context;
        this.appID = appID;
    }


    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "请耐心等待", "正在加载......");
    }

    @Override
    protected Object doInBackground(Void... voids) {
        try {
            return WorkFlowService.getWorkFlowService().getWFDetailInfoNew(appID);
        } catch (POAException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        progressDialog.dismiss();
        if (result != null) {
            workFlowDetailInfoNew = (WorkFlowDetailInfoNew) result;
        } else {
            ToastUtil.showMsg("获取工单详情失败");
        }

        if (loadListener != null) {
            loadListener.loadComplete();
        }
    }

    /**
     * 使用下面的方法时，要先使用该方法来判断是否获取成功
     *
     * @return
     */
    public WorkFlowDetailInfoNew getWorkFlowDetailInfoNew() {
        return workFlowDetailInfoNew;
    }

    public int getSuccess() {
        return workFlowDetailInfoNew.getSuccess();
    }

    public String getMes() {
        return workFlowDetailInfoNew.getMes();
    }

    public String getAppTitle() {
        return workFlowDetailInfoNew.getAppTitle();
    }


    public String getCondition() {
        return workFlowDetailInfoNew.getCondition();
    }

    public String getFlowInstanceID() {
        return workFlowDetailInfoNew.getFlowInstanceID();
    }

    public String getFlowVersion() {
        return workFlowDetailInfoNew.getFlowVersion();
    }

    public String getIsAvailability() {
        return workFlowDetailInfoNew.getIsAvailability();
    }

    public String getGroupCode() {
        return workFlowDetailInfoNew.getGroupCode();
    }

    public List<WorkFlowButton> getAppButton() {
        return workFlowDetailInfoNew.getAppButton();
    }

    public WFFieldListResult getWfFieldListResult() {
        return workFlowDetailInfoNew.getWfFieldListResult();
    }

    public String getHttAppDName() {
        return workFlowDetailInfoNew.getHttAppDName();
    }

    public void setOnLoadCompleteListerner(onLoadCompleteListener dataComplete) {
        this.loadListener = dataComplete;
    }

    public interface onLoadCompleteListener {
        public void loadComplete();
    }
}
