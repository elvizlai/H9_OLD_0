package com.sdrzlyz.h9.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.database.ContactsDB;

/**
 * Created by huagai on 14-5-5.
 */
public class ContactDetail extends BaseActivity {

    final private String ID = "ID";
    final private String NAME = "Name";
    final private String DUTY = "Duty";
    final private String DEPARTNAME = "DepartName";
    final private String DEPARTID = "DepartId";
    final private String MOBILE = "Mobile";
    final private String PHONE = "Phone";
    final private String HOMEPHONE = "Homephone";
    final private String NICKNAME = "NickName";
    final private String USERMAIL = "UserMail";
    final private String BIRTHDAY = "Birthday";


    private String contactName;
    private TextView linshiceshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);


        restoreState();
        initView();
        setListener();
        getContactDetail(contactName);
    }

    @Override
    protected void restoreState() {
        contactName = getIntent().getStringExtra("NAME");
    }

    @Override
    protected void initView() {
        linshiceshi = (TextView) findViewById(R.id.linshiceshi);
    }

    @Override
    protected void setListener() {

    }


    private void getContactDetail(String contactName) {
        String sql = "select * from CompanyContacts where name = ? and maindep = 1";
        Cursor cursor = ContactsDB.getInstance().getContactsDB().rawQuery(sql, new String[]{contactName});

        StringBuilder stringBuilder = new StringBuilder();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ID));
            String name = cursor.getString(cursor.getColumnIndex(NAME));
            String duty = cursor.getString(cursor.getColumnIndex(DUTY));
            String departname = cursor.getString(cursor.getColumnIndex(DEPARTNAME));
            String departid = cursor.getString(cursor.getColumnIndex(DEPARTID));
            String mobile = cursor.getString(cursor.getColumnIndex(MOBILE));
            String phone = cursor.getString(cursor.getColumnIndex(PHONE));
            String homephone = cursor.getString(cursor.getColumnIndex(HOMEPHONE));
            String nickname = cursor.getString(cursor.getColumnIndex(NICKNAME));
            String usermail = cursor.getString(cursor.getColumnIndex(USERMAIL));
            String birthday = cursor.getString(cursor.getColumnIndex(BIRTHDAY));

            stringBuilder.append("ID:" + id + "\n姓名：" + name + "\n职位：" + duty + "\n部门：" + departname + "\n部门ID：" + departid + "\n手机：" + mobile + "\n电话：" + phone + "\n家庭电话：" + homephone + "\n昵称：" + nickname + "\n邮箱：" + usermail + "\n生日：" + birthday);
        }

        linshiceshi.setText(stringBuilder.toString());
    }
}
