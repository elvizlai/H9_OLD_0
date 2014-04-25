package com.sdrzlyz.h9.test;

import android.util.JsonReader;
import android.util.JsonToken;
import com.sdrzlyz.h9.database.ContactsDB;
import com.sdrzlyz.h9.util.PinyinUtil;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by sdrzlyz on 14-4-14.
 */
public class TT {

    public static void main(String[] args) {
        String str0 = PinyinUtil.getPinYin("单");
        String str = PinyinUtil.getFirstLetter("单");
        System.out.println("Str:"+str);
        System.out.println("Str0:"+str0);

        ContactsDB.createContactsDB().createTable();
    }

}
