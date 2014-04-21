package com.sdrzlyz.h9.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.View;
import android.widget.Button;
import com.sdrzlyz.h9.R;
import com.sdrzlyz.h9.database.ContactsDBService;
import com.sdrzlyz.h9.entity.ReturnContactInfosNew;
import com.sdrzlyz.h9.entity.ReturnContactInfosNewEst;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.impl.ContactService;
import com.sdrzlyz.h9.net.HttpClient;
import com.sdrzlyz.h9.tree.Node;
import com.sdrzlyz.h9.util.JSONUtil;
import net.sourceforge.pinyin4j.PinyinHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by sdrzlyz on 14-4-17.
 */
public class TEST extends Activity {
    private Button test_btn;
    private ReturnContactInfosNewEst returnContactInfosNewEst;

    public static String uncompressToString(byte[] bytes) {
        String str = "";
        if ((bytes == null) || (bytes.length == 0))
            return null;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            str = new String(new HttpClient().readStream(gzipInputStream));
            System.out.println("解密？" + str);
            return str;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            return str;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        test_btn = (Button) findViewById(R.id.test_contacts);
        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TEST.this, TreeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("node", test());//节点传送
                intent.putExtra("bundle", bundle);
                TEST.this.startActivity(intent);
            }
        });
        init();
        testpinyin();
        // other();
        ContactsDBService.getContactsDBService().createContactsDB();


        String sql = "select name from CompanyContacts where DepartmentID='1077'";

        Cursor cursor = ContactsDBService.getContactsDBService().getContactsDB().rawQuery(sql,null);

        int id = cursor.getColumnIndex("Name");
        System.out.println(id+" id");

        if(cursor.moveToFirst()){
            int idx = cursor.getColumnIndex("Name");
            System.out.println(idx+" id");

        }



        //System.out.println(cursor.getString(0));

//        while (cursor.moveToNext()) {
//            System.out.println("1111");
//            String name1 = cursor.getString(0); //获取第一列的值,第一列的索引从0开始
//            String name2 = cursor.getString(1);//获取第二列的值
//            System.out.println(name1+" "+name2);
//            //int age = cursor.getInt(2);//获取第三列的值
//        }
        cursor.close();



    }

    private void init() {
        new AsyncTask<Void, Integer, Object>() {

            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return ContactService.getContactService().getContactsNewEst();
                } catch (POAException e) {
                    e.printStackTrace();
                    return null;
                }

            }

            @Override
            protected void onPostExecute(Object result) {
                if (result != null) {

                    String str = uncompressToString(((ReturnContactInfosNew) result).getContactInfo());

                    try {
                        returnContactInfosNewEst = (ReturnContactInfosNewEst) JSONUtil.parse(str,ReturnContactInfosNewEst.class);
                    } catch (POAException e) {
                        e.printStackTrace();
                    }

                    List xx = returnContactInfosNewEst.getDeparts();

                    for (Object x:xx){
                        System.out.println(x.toString());
                    }

                    JsonReader jsonReader = new JsonReader(new StringReader(str));
                    try {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()){
                            JsonToken jsonToken = jsonReader.peek();
                            if (jsonToken==JsonToken.NAME){

                                System.out.println("name");
                               
                            }
                        }
                        jsonReader.endObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        }.execute();
    }


    private void other() {
        new AsyncTask<Void, Integer, Object>() {

            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return ContactService.getContactService().getContactsForCompress();
                } catch (POAException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Object result) {
                if (result != null) {
                    uncompressToString(((ReturnContactInfosNew) result).getContactInfo());
                }
            }
        }.execute();
    }


    /**
     * 假设拼接的数据
     */
    private Node test()//假设数据
    {
        Node root = null;
        //根节点
        root = new Node("xxx", "0");//设置参数  KEY VALUE
        root.setIcon(R.drawable.ic_launcher);// 设置图标
        root.setCheckBox(false);//是否显示复选框  默认为true

        ArrayList<Node> list = new ArrayList<Node>();
        for (int i = 0; i < 5; i++)//第一层节点数据
        {
            Node n1 = new Node("namei:" + i, "idi:" + i);//设置 key  value
            n1.setCheckBox(false);//是否拥有复选框
            n1.setIcon(R.drawable.sys_tree_folder);//节点图标
            n1.setParent(root);//设置父节点
            root.add(n1);//父节点添加当前节点
            for (int j = 0; j < 5; j++)//第二层节点数据
            {
                Node n2 = new Node("namej:" + j, "idj:" + j);
                n2.setCheckBox(false);
                n2.setIcon(R.drawable.sys_tree_folder);
                n2.setParent(n1);
                n1.add(n2);
                for (int k = 0; k < 5; k++)//第三层节点数据    ，第四层，第五层。。。等都可以
                {
                    Node n3 = new Node("namek:" + k, "idk:" + k);
                    n3.setIcon(R.drawable.sys_tree_file);
                    n3.setParent(n2);
                    n2.add(n3);
                }
            }
        }
        return root;
    }

    public void testpinyin(){
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray('单');
        for (String x:pinyinArray){
            System.out.println(x);
        }
    }
}
