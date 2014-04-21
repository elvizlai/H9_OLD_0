package com.sdrzlyz.h9.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.sdrzlyz.h9.entity.WorkFlowList;

import java.util.List;

/**
 * Created by sdrzlyz on 14-4-15.
 */
public class WFItemClickedHandler implements AdapterView.OnItemClickListener {

    private Context context;
    private List<WorkFlowList> workFlowList;

    public WFItemClickedHandler(Context context, List<WorkFlowList> workFlowList) {
        this.context = context;
        this.workFlowList = workFlowList;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        String id = workFlowList.get(position).getAppID();
        Intent intent = new Intent();

        //context.startaf

    }


}
