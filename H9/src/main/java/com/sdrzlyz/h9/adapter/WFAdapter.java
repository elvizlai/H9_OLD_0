package com.sdrzlyz.h9.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;

import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.entity.WorkFlowList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sdrzlyz on 14-4-13.
 */
public class WFAdapter {
    private Context context;
    private List<WorkFlowList> workFlowList;
    private ArrayList<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

    public WFAdapter(Context context, List<WorkFlowList> workFlowList) {
        this.context = context;
        this.workFlowList = workFlowList;
    }

    public BaseAdapter getAdapter() {
        if (workFlowList == null) {
            return null;
        }

        if (workFlowList.size() == 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.no_msg);
            adapter.add("当前没有待办流程");
            return adapter;
        }

        for (int i = 0; i < workFlowList.size(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            //这种多次获取的直接修改了吧
            item.put("title", workFlowList.get(i).getAppTitle());
            item.put("type", workFlowList.get(i).getAppType() + "-" + workFlowList.get(i).getAppCode());
            item.put("time", workFlowList.get(i).getAppBeginTime());
            myData.add(item);
        }
        return new SimpleAdapter(context, myData, R.layout.workflow_list, new String[]{"title", "type", "time"}, new int[]{R.id.title, R.id.type, R.id.time});
    }

}


