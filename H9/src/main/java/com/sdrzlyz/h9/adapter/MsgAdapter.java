package com.sdrzlyz.h9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.entity.CallNew;

import java.util.List;

/**
 * Created by sdrzlyz on 14-4-13.
 */
public class MsgAdapter extends BaseAdapter {
    List<CallNew> callNew;
    int msgType;
    private Context context;

    public MsgAdapter(Context context, List<CallNew> callNew, int msgType) {
        this.context = context;
        this.callNew = callNew;
        this.msgType = msgType;
    }

    @Override
    public int getCount() {
        if (callNew != null) {
            return callNew.size();
        } else {
            return 1;
        }
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (callNew == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.no_msg, null);
            TextView no_msg = (TextView) convertView.findViewById(R.id.no_msg);
            if (msgType == 1) {
                no_msg.setText("当前没有已收寻呼");
            } else if (msgType == 2) {
                no_msg.setText("当前没有未阅寻呼");
            } else if (msgType == 3) {
                no_msg.setText("当前没有保留寻呼");
            }
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.msg_list, null);
            TextView senderName = (TextView) convertView.findViewById(R.id.senderName);
            TextView startTime = (TextView) convertView.findViewById(R.id.startTime);
            TextView msg_content = (TextView) convertView.findViewById(R.id.msg_content);
            LinearLayout relative_container = (LinearLayout) convertView.findViewById(R.id.relative_container);

            senderName.setText(callNew.get(position).getSenderName());
            startTime.setText(callNew.get(position).getStartTime());
            msg_content.setText(callNew.get(position).getContent());

            if (callNew.get(position).getRelatedPeopleName() != null && !callNew.get(position).getRelatedPeopleName().equals("")) {
                relative_container.setVisibility(View.VISIBLE);
                TextView related_people = (TextView) convertView.findViewById(R.id.related_people);
                related_people.setText(callNew.get(position).getRelatedPeopleName());
            }
        }
        return convertView;
    }
}
