package com.sdrzlyz.h9.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;

import com.sdrzlyz.h9.entity.CallNew;
import com.sdrzlyz.h9.entity.ReturnCallListNew;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.impl.MessageService;
import com.sdrzlyz.h9.util.ToastUtil;

import java.util.List;

/**
 * Created by sdrzlyz on 14-4-14.
 */
public class GetMsgList extends AsyncTask<Void, Integer, Object> {

    private onLoadCompleteListener loadListener;
    private int msgType;
    private String searchStr;
    private ReturnCallListNew returnCallListNew;
    private ProgressDialog progressDialog;
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;

    public GetMsgList(int msgType, String searchStr) {
        this.msgType = msgType;
        this.searchStr = searchStr;
    }

    public GetMsgList(SwipeRefreshLayout swipeRefreshLayout, int msgType, String searchStr) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.msgType = msgType;
        this.searchStr = searchStr;
    }

    public GetMsgList(Context context, int msgType, String searchStr) {
        this.context = context;
        this.msgType = msgType;
        this.searchStr = searchStr;
    }

    public void setOnLoadCompleteListerner(onLoadCompleteListener dataComplete) {
        this.loadListener = dataComplete;
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
            return MessageService.getMessageService().getCallListNew(msgType, searchStr);
        } catch (POAException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        //super.onPostExecute(result);
        if (context != null)
            progressDialog.dismiss();

        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
        /**
         * result不为null 说明执行成功
         */
        if (result != null) {
            returnCallListNew = (ReturnCallListNew) result;
        } else {
            ToastUtil.showMsg("获取寻呼失败!");
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
    public ReturnCallListNew getReturnCallListNew() {
        return returnCallListNew;
    }

    public List<CallNew> getReturnCallList() {
        return returnCallListNew.getReturnCallList();
    }

    public String getMes() {
        return returnCallListNew.getMes();
    }

    public int getSuccess() {
        return returnCallListNew.getSuccess();
    }

    public String getLastCallId() {
        return returnCallListNew.getLastCallId();
    }

    public long getTotalCall() {
        return returnCallListNew.getTotalCall();
    }

    public interface onLoadCompleteListener {
        public void loadComplete();
    }
}
