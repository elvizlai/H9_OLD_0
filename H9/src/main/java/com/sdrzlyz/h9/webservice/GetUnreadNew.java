package com.sdrzlyz.h9.webservice;

import android.os.AsyncTask;

import com.sdrzlyz.h9.entity.UnReadCountNew;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.impl.LoginService;
import com.sdrzlyz.h9.util.ToastUtil;

/**
 * Created by sdrzlyz on 14-4-15.
 */
public class GetUnreadNew extends AsyncTask<Void, Integer, Object> {

    private String lastCallTime = "";
    private String lastWkTime = "";
    private UnReadCountNew unReadCountNew;
    private onLoadCompleteListener loadListener;

    public GetUnreadNew() {

    }

    public GetUnreadNew(String lastCallTime, String lastWkTime) {
        this.lastCallTime = lastCallTime;
        this.lastWkTime = lastWkTime;
    }

    @Override
    protected Object doInBackground(Void... voids) {
        try {
            return LoginService.getLoginService().getUnreadNew("", "");
        } catch (POAException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        //super.onPostExecute(o);
        if (result != null) {
            unReadCountNew = (UnReadCountNew) result;
        } else {
            ToastUtil.showMsg("获取消息条数失败");
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
    public UnReadCountNew getUnReadCountNew() {
        return unReadCountNew;
    }

    public String getMes() {
        return unReadCountNew.getMes();
    }

    public int getSuccess() {
        return unReadCountNew.getSuccess();
    }

    public long getWfnum() {
        return unReadCountNew.getWfnum();
    }

    public long getCallnum() {
        return unReadCountNew.getCallnum();
    }

    public long getCallNumTotal() {
        return unReadCountNew.getCallNumTotal();
    }

    public long getWfNumTotal() {
        return unReadCountNew.getWfNumTotal();
    }

    public String getServiceTime() {
        return unReadCountNew.getServiceTime();
    }

    public void setOnLoadCompleteListerner(onLoadCompleteListener dataComplete) {
        this.loadListener = dataComplete;
    }

    public interface onLoadCompleteListener {
        public void loadComplete();
    }
}
