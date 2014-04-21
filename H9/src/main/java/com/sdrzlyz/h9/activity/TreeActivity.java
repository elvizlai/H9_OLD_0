package com.sdrzlyz.h9.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.tree.Node;
import com.sdrzlyz.h9.tree.ToolbarAdapter;
import com.sdrzlyz.h9.tree.TreeAdapter;

import java.util.List;

/**
 * Created by huagai on 14-4-18.
 */
public class TreeActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView code_list;
    TreeActivity oThis = this;
    Node node = null;
    private LinearLayout toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sys_tree_main);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        toolBar = (LinearLayout) findViewById(R.id.toolBar);
        code_list = (ListView) findViewById(R.id.code_list);
        code_list.setOnItemClickListener(this);
        setToolBar(new String[]{"确定", "", "", "退出"}, new int[]{0, 3});
        this.node = (Node) bundle.getSerializable("node");
        setPreson();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub

        // 这句话写在最后面
        ((TreeAdapter) parent.getAdapter()).ExpandOrCollapse(position);
    }

    // 设置节点,可以通过循环或递归方式添加节点
    private void setPreson() {
        TreeAdapter ta = new TreeAdapter(oThis, node);
        // 设置整个树是否显示复选框
        ta.setCheckBox(true);
        // 设置展开和折叠时图标
        ta.setExpandedCollapsedIcon(R.drawable.sys_tree_tree_ex, R.drawable.sys_tree_tree_ec);
        // 设置默认展开级别
        ta.setExpandLevel(2);
        code_list.setAdapter(ta);
    }

    // 设置底部工具栏
    private void setToolBar(String[] name_array, int[] pos_array) {
        toolBar.removeAllViews();

        GridView toolbarGrid = new GridView(this);
        toolbarGrid.setNumColumns(4);// 设置每行列数
        toolbarGrid.setGravity(Gravity.CENTER);// 位置居中
        ToolbarAdapter adapter = new ToolbarAdapter(this, name_array, null, pos_array);
        toolbarGrid.setAdapter(adapter);
        toolbarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                switch (arg2) {
                    case 0:// 显示选中结果
                        List<Node> nodes = ((TreeAdapter) code_list.getAdapter()).getSeletedNodes();
                        String msg = "";
                        for (int i = 0; i < nodes.size(); i++) {
                            Node n = nodes.get(i);
                            if (n.isLeaf()) {
                                if (!msg.equals(""))
                                    msg += ",";
                                msg += n.getText() + ":" + n.getValue();
                            }
                            //break;
                        }
                        if (msg.equals("")) {
                            Toast.makeText(oThis, "没有选中任何项", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(oThis, msg, Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case 3:// 返回
                        oThis.finish();
                        break;
                }
            }
        });
        toolBar.addView(toolbarGrid);
    }

}
