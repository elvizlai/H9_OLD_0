package com.sdrzlyz.h9.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import com.sdrzlyz.h9.config.ApplictionHandle;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.impl.ContactService;

/**
 * 主要用于联系人数据库写入
 * Created by sdrzlyz on 14-4-18.
 */
public class ContactsDBService {
    private static SQLiteDatabase contacts_db;
    //private static ContactsDBService contactsDBService = new ContactsDBService();

    private ContactsDBService() {
        contacts_db = ApplictionHandle.getContext().openOrCreateDatabase("hg_contacts.db", Context.MODE_PRIVATE, null);
        createContactsDB();
    }

    public static ContactsDBService getContactsDBService() {
        return new ContactsDBService();
    }

    public SQLiteDatabase getContactsDB() {
        return contacts_db;
    }

    private void createContactsDB() {
        /**
         * 公司各个机构
         */
        String create_Organization = "CREATE TABLE IF NOT EXISTS Organization (" +
                "ID integer PRIMARY KEY AUTOINCREMENT," +
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
                "ServerID varchar(32)," +
                "Name varchar(40)," +
                "Duty varchar(60)," +
                "headthumb varchar(60)," +
                "headPhoto varchar(60)," +
                "SIGNNAME varchar(32)," +
                "Mobile varchar(40)," +
                "Homephone varchar(40)," +
                "Department varchar(64)," +
                "DepartmentID varchar(128)," +
                "Phone varchar(40)," +
                "Pinyin_Name varchar(32)," +
                "UserMail varchar(32)," +
                "FirstLetter_Name varchar(16)," +
                "BelongID varchar(32)," +
                "IsCollected integer," +
                "NickName varchar(128)," +
                "ThirdId varchar(128)," +
                "Birthday varchar(32)," +
                "MainDepart integer(8)," +
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

        contacts_db.execSQL(create_Organization);
        contacts_db.execSQL(create_CompanyContacts);
        contacts_db.execSQL(create_PublicContacts);
        contacts_db.execSQL(create_CustomContacts);
        contacts_db.execSQL(create_OwnGroups);
        contacts_db.execSQL(create_OwnContactsGroup);
        contacts_db.execSQL(create_OwnContacts);
        //contacts_db.close();
        Log.d("TAG", "contacts_db created succeed and closed!");
    }

    public void deleteContactsDB() {

    }

    public void updateContacts() {

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("hahaha");

            }
        };

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
                //super.onPostExecute(o);
                if (result != null) {
                    new Thread(runnable).start();
                }

            }
        }.execute();


    }

}
