package com.sdrzlyz.h9.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.sdrzlyz.h9.config.ApplictionHandle;

/**
 * 主要用于联系人数据库写入
 * Created by sdrzlyz on 14-4-18.
 */
public class ContactsDBService {
    private static SQLiteDatabase contacts_db;

    private ContactsDBService() {
        contacts_db = ApplictionHandle.getContext().openOrCreateDatabase("hg_contacts", Context.MODE_PRIVATE, null);
    }

    public static ContactsDBService getContactsDBService() {
        return new ContactsDBService();
    }

    public SQLiteDatabase getContactsDB(){
        return contacts_db;
    }

    public void createContactsDB() {
        /**
         * 公司各个机构
         */
        String create_Organization = "CREATE TABLE IF NOT EXISTS Organization (" +
                "ID integer PRIMARY KEY AUTOINCREMENT," +
                "ServerID varchar(32)," +
                "DepartFlag integer," +
                "ParentDepartID varchar(32)," +
                "DepartName varchar(32)," +
                "Layer varchar(32)," +
                "HasChild integer," +
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
        Log.d("TAG","contacts_db created succ!");
    }

    public void deleteContactsDB(){

    }

}
