package com.sdrzlyz.h9.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.sdrzlyz.h9.entity.WFListResult;
import com.sdrzlyz.h9.entity.WorkFlowList;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.impl.WorkFlowService;
import com.sdrzlyz.h9.util.ToastUtil;
import com.sdrzlyz.h9.view.RefreshableView;

import java.util.List;

/**
 * Created by sdrzlyz on 14-4-14.
 */
public class GetWFList extends AsyncTask<Void, Integer, Object> {

    private onLoadCompleteListener loadListener;

    private Context context;
    private ProgressDialog progressDialog;
    private String lastTime = "";
    private int flag = 1;
    private RefreshableView refreshableView;
    private WFListResult wfListResult;

    public GetWFList() {

    }

    public GetWFList(RefreshableView refreshableView) {
        this.refreshableView = refreshableView;
    }

    public GetWFList(String lastTime) {
        this.lastTime = lastTime;
    }

    public GetWFList(int flag, String lastTime) {
        this(lastTime);
        this.flag = flag;
    }

    public GetWFList(Context context, int flag, String lastTime) {
        this(flag, lastTime);
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        if (context != null)
            progressDialog = ProgressDialog.show(context, "正在加载", "请稍等......");
    }

    @Override
    protected Object doInBackground(Void... voids) {
        try {
            return WorkFlowService.getWorkFlowService().getWFList(flag, lastTime);
        } catch (POAException e) {
            if (refreshableView != null) {
                refreshableView.finishRefreshing();
            }
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(Object result) {
        //super.onPostExecute(o);
        if (context != null)
            progressDialog.dismiss();
        /**
         * result不为null 说明执行成功，成功的话才把接口内的内容打开
         */
        if (result != null) {
            wfListResult = (WFListResult) result;
        } else {
            ToastUtil.showMsg("获取寻呼失败!");
        }
        refreshableView.finishRefreshing();
        if (loadListener != null) {
            loadListener.loadComplete();
        }
    }

    /**
     * 使用下面的方法时，要先使用该方法来判断是否获取成功
     *
     * @return
     */
    public WFListResult getWfListResult() {
        return wfListResult;
    }

    public List<WorkFlowList> getWorkFlowList() {
        return wfListResult.getWfList();
    }

    public String getMes() {
        return wfListResult.getMes();
    }

    public int getSuccess() {
        return wfListResult.getSuccess();
    }

    public void setOnLoadCompleteListerner(onLoadCompleteListener dataComplete) {
        this.loadListener = dataComplete;
    }

    public interface onLoadCompleteListener {
        public void loadComplete();
    }
}
