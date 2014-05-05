package com.sdrzlyz.h9.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.database.ContactsDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sdrzlyz on 14-5-5.
 * 思路如下：先从数据库中加载联系人为列表
 */
public class Contacts extends BaseActivity {
    final private String DEPTNAME = "DeptName";
    final private String DEPTID = "DeptId";
    final private String NAME = "Name";
    ArrayList<String> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        restoreState();
        initView();
        setListener();
    }

    @Override
    protected void restoreState() {
        String main_sql = "select DeptId,DeptName from Organization where layer='00001'";
        Cursor main_cursor = ContactsDB.getInstance().getContactsDB().rawQuery(main_sql, new String[]{});

        main_cursor.moveToFirst();
        String main_DeptId = main_cursor.getString(0);
        String main_Deptname = main_cursor.getString(1);
        main_cursor.close();

        System.out.println(main_DeptId + " " + main_Deptname);

        Map<String, String> map = getDeptList(main_DeptId);

        System.out.println(map.keySet());
        System.out.println(map.values());

        for (String s : map.values()) {
            System.out.println(s);
        }

    }

    @Override
    protected void initView() {

        ListView list2 = (ListView) findViewById(R.id.view2);
        contactsList = (ArrayList<String>) getNameList();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList);
        list2.setAdapter(arrayAdapter);
        list2.setOnItemClickListener(new contactsItemClickedHandler());

        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("分组",
                getResources().getDrawable(R.drawable.ic_launcher)).setContent(
                R.id.view1));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("联系人")
                .setContent(R.id.view2));
    }

    @Override
    protected void setListener() {

    }

    private Map<String, String> getDeptList(String id) {
        //List<String> deptname_list = new ArrayList<String>();
        String selsect_dept = "select * from Organization where ParentId=" + id;
        Cursor cursor = ContactsDB.getInstance().getContactsDB().rawQuery(selsect_dept, new String[]{});
        Map<String, String> map = new HashMap<String, String>();
        while (cursor.moveToNext()) {
            String deptId = cursor.getString(cursor.getColumnIndex(DEPTID));
            String deptName = cursor.getString(cursor.getColumnIndex(DEPTNAME));
            map.put(deptId, deptName);
            //deptname_list.add(deptName);
        }
        cursor.close();
        return map;
    }

    private List<String> getNameList() {
        String sql = "select * from CompanyContacts order by Pinyin_Name";
        List<String> list = new ArrayList<String>();
        Cursor cursor = ContactsDB.getInstance().getContactsDB().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            list.add(name);
            System.out.println(name);
        }
        return list;
    }

    private class contactsItemClickedHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Intent intent = new Intent(Contacts.this, ContactDetail.class);
            intent.putExtra("NAME", contactsList.get(position));
            startActivityForResult(intent, 1);
        }
    }
}
