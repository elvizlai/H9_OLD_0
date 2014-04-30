package com.sdrzlyz.h9.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import com.sdrzlyz.h9.config.ApplictionHandle;
import com.sdrzlyz.h9.entity.MessagesInfo;
import com.sdrzlyz.h9.entity.ReturnContactInfosNew;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.impl.ContactService;
import com.sdrzlyz.h9.net.HttpClient;
import com.sdrzlyz.h9.util.PinyinUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.zip.GZIPInputStream;

/**
 * 主要用于联系人数据库写入
 * Created by sdrzlyz on 14-4-18.
 * <p/>
 * 数据库使用方法
 * 1、第一次使用时，应该先创建：ContactsDB.createContactsDB.X
 */
public class ContactsDB {
    private static SQLiteDatabase contacts_db;

    private MessagesInfo organization, companyContacts;

    private ContactsDB() {
        contacts_db = ApplictionHandle.getContext().openOrCreateDatabase("hg_contacts", Context.MODE_PRIVATE, null);
        //createTable();
    }

    /**
     * 对象无需被引用，建立完后就可以被回收掉
     *
     * @return
     */
    public static ContactsDB createContactsDB() {
        return new ContactsDB();
    }

    private static String uncompressToString(byte[] bytes) {
        String str = "";
        if ((bytes == null) || (bytes.length == 0))
            return null;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            str = new String(new HttpClient().readStream(gzipInputStream));
            //System.out.println("解密？" + str);
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
        return str;
    }

    public SQLiteDatabase getContactsDB() {
        return contacts_db;
    }

    /**
     * 创建数据库中的表
     */
    public boolean createTable() {
        boolean isCreatedSucceed = false;
        /**
         * 公司各个机构
         */
        String create_Organization = "CREATE TABLE IF NOT EXISTS Organization (" +
                "_id integer PRIMARY KEY AUTOINCREMENT," +
                "Layer varchar(32)," +
                "DeptId varchar(32)," +
                "DeptName varchar(32)," +
                "ParentId varchar(32)," +
                "HasChild integer," +
                "DepartFlag integer," +
                "BelongID varchar(32)" +
                ");";

        /**
         * 公司所有联系人
         */
        String create_CompanyContacts = "CREATE TABLE IF NOT EXISTS CompanyContacts (" +
                "_id integer PRIMARY KEY AUTOINCREMENT," +
                "ID varchar(32)," +
                "Name varchar(40)," +
                "Duty varchar(60)," +
                "DepartName varchar(64)," +
                "DepartId varchar(128)," +
                "Mobile varchar(40)," +
                "Phone varchar(40)," +
                "Homephone varchar(40)," +
                "NickName varchar(128)," +
                "Pinyin_Name varchar(32)," +
                "FirstLetter_Name varchar(16)," +
                "UserMail varchar(32)," +
                "ThirdId varchar(128)," +
                "Birthday varchar(32)," +
                "MainDep integer(8)," +
                "Headthumb varchar(60)," +
                "Headpic varchar(60)," +
                "SignName varchar(32)," +
                "BelongID varchar(32)," +
                "IsCollected integer," +
                "UserOrder varchar(32)" +
                ");";

        /**
         * 客户联系人（有crm权限的才会有这项吧？）
         */
        String create_CustomContacts = "CREATE TABLE IF NOT EXISTS CustomContacts (" +
                "_id integer PRIMARY KEY AUTOINCREMENT," +
                "ServerID varchar(32)," +
                "Name varchar(40)," +
                "Duty varchar(60)," +
                "Mobile varchar(40)," +
                "Homephone varchar(40)," +
                "Phone varchar(40)," +
                "Pinyin_Name varchar(32)," +
                "UserMail varchar(32)," +
                "SIGNNAME varchar(32)," +
                "Company varchar(32)," +
                "FirstLetter_Name varchar(16)," +
                "BelongID varchar(32)," +
                "IsCollected integer," +
                "NickName varchar(128)," +
                "Birthday varchar(32)" +
                ");";

        /**
         * 自定义群组？
         */
        String create_OwnGroups = "CREATE TABLE IF NOT EXISTS OwnGroups (" +
                "  _id integer PRIMARY KEY AUTOINCREMENT," +
                "  GroupID varchar(32)," +
                "  GroupName varchar(32)," +
                "  BelongID varchar(32)," +
                "  PINYIN varchar(32)" +
                ");" +
                "CREATE INDEX belong_group ON OwnGroups (BelongID, GroupID);";

        /**
         * 自定义分组
         */
        String create_OwnContactsGroup = "CREATE TABLE IF NOT EXISTS OwnContactsGroup (" +
                "_id integer PRIMARY KEY AUTOINCREMENT," +
                "GroupID varchar(32)," +
                "ContactID varchar(32)," +
                "GroupName varchar(32)," +
                "BelongID varchar(32)" +
                ");" +
                "CREATE INDEX belong_group_contact ON OwnContactsGroup (BelongID, GroupID, ContactID);" +
                "CREATE INDEX group_belong_group ON OwnContactsGroup (BelongID, GroupID);" +
                "CREATE INDEX belong_contact ON OwnContactsGroup (BelongID, ContactID);";

        /**
         * 自定义联系人
         */
        String create_OwnContacts = "CREATE TABLE IF NOT EXISTS OwnContacts (" +
                "  _id integer PRIMARY KEY AUTOINCREMENT," +
                "  ServerID varchar(32)," +
                "  Name varchar(40)," +
                "  headthumb varchar(60)," +
                "  headPhoto varchar(60)," +
                "  Duty varchar(60)," +
                "  Phone varchar(40)," +
                "  Mobile varchar(40)," +
                "  HomePhone varchar(40)," +
                "  Pinyin_Name varchar(32)," +
                "  UserMail varchar(32)," +
                "  FirstLetter_Name varchar(16)," +
                "  BelongID varchar(32)," +
                "  SIGNNAME varchar(32)," +
                "  Company varchar(32)," +
                "  NickName varchar(128)," +
                "  IsCollected integer," +
                "  Birthday varchar(32)" +
                ");" +
                "CREATE INDEX belong_server ON OwnContacts (BelongID, ServerID);" +
                "CREATE INDEX belong_id ON OwnContacts (BelongID);";

        /**
         * 自定义公共联系人
         */
        String create_PublicContacts = "CREATE TABLE IF NOT EXISTS PublicContacts (" +
                "  _id integer PRIMARY KEY AUTOINCREMENT," +
                "  ServerID varchar(32)," +
                "  Name varchar(40)," +
                "  headthumb varchar(60)," +
                "  headPhoto varchar(60)," +
                "  Duty varchar(60)," +
                "  Phone varchar(40)," +
                "  Mobile varchar(40)," +
                "  SIGNNAME varchar(32)," +
                "  Company varchar(32)," +
                "  HomePhone varchar(40)," +
                "  Pinyin_Name varchar(32)," +
                "  UserMail varchar(32)," +
                "  FirstLetter_Name varchar(16)," +
                "  BelongID varchar(32)," +
                "  GroupNames varchar(128)," +
                "  NickName varchar(128)," +
                "  IsCollected integer," +
                "  Birthday varchar(32)" +
                ");";

        try {
            contacts_db.execSQL(create_Organization);
            contacts_db.execSQL(create_CompanyContacts);
            contacts_db.execSQL(create_PublicContacts);
            contacts_db.execSQL(create_CustomContacts);
            contacts_db.execSQL(create_OwnGroups);
            contacts_db.execSQL(create_OwnContactsGroup);
            contacts_db.execSQL(create_OwnContacts);
            isCreatedSucceed = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("TAG", "TABLE成功创建");
        return isCreatedSucceed;
    }

    /**
     * 删除数据库中的表
     */
    private boolean deleteTable(String tabName) {
        boolean isDeletedSucceed = false;
        if (contacts_db != null) {
            try {
                contacts_db.execSQL("DROP TABLE IF EXISTS " + tabName);
                isDeletedSucceed = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isDeletedSucceed;
    }

    /**
     * 将数据库关闭
     */
    public void closeContactsDB() {
        if (contacts_db != null) {
            contacts_db.close();
        }
    }

    public void deleteContactsDB() {

    }

    public void updateContacts() {

        new AsyncTask<Void, Integer, Object>() {

            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    organization = ContactService.getContactService().getContactsNewEst(0, 0, 1, 0, 0, 0);
                    companyContacts = ContactService.getContactService().getContactsNewEst(1, 0, 1, 0, 0, 500);
                    return "SUCCESS";
                } catch (POAException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Object result) {
                if (result != null) {
                    String organization2Str = uncompressToString(((ReturnContactInfosNew) organization).getContactInfo());
                    String companyContacts2Str = uncompressToString(((ReturnContactInfosNew) companyContacts).getContactInfo());
                    Thread thread1 = new Thread(new begin2UpdateOrganize(organization2Str));
                    Thread thread2 = new Thread(new begin2UpdateCompanyContacts(companyContacts2Str));
                    thread1.start();
                    try {
                        thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    thread2.start();
                    try {
                        thread2.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   // new Thread(new begin2UpdateCompanyContacts(companyContacts2Str)).start();
                    //contacts_db.close();
                }
            }
        }.execute();


    }

    private void updateDeparts() {

    }

    /**
     * 用于更新公司部门名称
     */
    private class begin2UpdateOrganize implements Runnable {
        String organization;

        begin2UpdateOrganize(String str) {
            organization = str;
        }

        @Override
        public void run() {
            //清空数据库
            ContactsDB.createContactsDB();
            System.out.println("清除TABLE_Organization");
            if (deleteTable("Organization")) {
                System.out.println("新建TABLE_Organization");
                createTable();
            }


            System.out.println("开始更新Organization");
            JsonReader jsonReader = new JsonReader(new StringReader(organization));
            try {
                jsonReader.beginObject();
                JsonToken jsonToken;
                while (jsonReader.hasNext()) {
                    jsonToken = jsonReader.peek();
                    if (jsonToken == JsonToken.NAME) {
                        String name = jsonReader.nextName();
                        if (name.equals("departs")) {
                            jsonToken = jsonReader.peek();
                            if (jsonToken == JsonToken.BEGIN_ARRAY) {
                                jsonReader.beginArray();
                                while (jsonReader.hasNext()) {
                                    ContentValues cv = new ContentValues();
                                    jsonReader.beginObject();
                                    while (jsonReader.hasNext()) {
                                        String key, value = null;
                                        key = jsonReader.nextName();
                                        jsonToken = jsonReader.peek();
                                        if (!(jsonToken == JsonToken.NULL)) {
                                            value = jsonReader.nextString();
                                        } else {
                                            jsonReader.skipValue();
                                        }
                                        cv.put(key, value);
                                    }
                                    jsonReader.endObject();
                                    contacts_db.insert("Organization", null, cv);
                                }
                                jsonReader.endArray();
                            }
                        }
                    } else {
                        jsonReader.skipValue();
                    }
                }
                System.out.println("Organization更新完毕");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用于更新公司联系人
     */
    private class begin2UpdateCompanyContacts implements Runnable {

        String companyContacts;

        begin2UpdateCompanyContacts(String str) {
            companyContacts = str;
        }

        @Override
        public void run() {
            //清空数据库
            System.out.println("清除TABLE_CompanyContacts");
            if (deleteTable("CompanyContacts")) {
                System.out.println("新建TABLE_CompanyContacts");
                createTable();
            }

            System.out.println("开始更新CompanyContacts");
            JsonReader jsonReader = new JsonReader(new StringReader(companyContacts));
            try {
                jsonReader.beginObject();
                JsonToken jsonToken;
                while (jsonReader.hasNext()) {
                    jsonToken = jsonReader.peek();
                    if (jsonToken == JsonToken.NAME) {
                        String name = jsonReader.nextName();
                        if (name.equals("companyContacts")) {
                            jsonToken = jsonReader.peek();
                            if (jsonToken == JsonToken.BEGIN_ARRAY) {
                                jsonReader.beginArray();
                                while (jsonReader.hasNext()) {
                                    ContentValues cv = new ContentValues();
                                    jsonReader.beginObject();
                                    while (jsonReader.hasNext()) {
                                        String value = null;
                                        String key = jsonReader.nextName();
                                        //System.out.println("key:" + key);
                                        jsonToken = jsonReader.peek();
                                        if (!(jsonToken == JsonToken.NULL)) {
                                            value = jsonReader.nextString();
                                            if (key.equals("name")) {
                                                cv.put("Pinyin_Name", PinyinUtil.getPinYin(value));
                                                cv.put("FirstLetter_Name", PinyinUtil.getFirstLetter(value));
                                                // System.out.println(PinyinUtil.getPinYin(value));
                                                //  System.out.println(PinyinUtil.getFirstLetter(value));
                                            }
                                        } else {
                                            jsonReader.skipValue();
                                        }
                                        cv.put(key, value);
                                    }
                                    jsonReader.endObject();
                                    contacts_db.insert("CompanyContacts", null, cv);
                                }
                                jsonReader.endArray();
                            }
                        }
                    } else {
                        jsonReader.skipValue();
                    }
                }
                System.out.println("CompanyContacts更新完毕");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
