package com.sdrzlyz.h9.test;

import android.util.JsonReader;
import android.util.JsonToken;
import com.sdrzlyz.h9.database.ContactsDB;
import com.sdrzlyz.h9.util.PinyinUtil;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sdrzlyz on 14-4-14.
 */
public class TT {

    public static void main(String[] args) {
        String str0 = PinyinUtil.getPinYin("单lyz22!@");
        String str = PinyinUtil.getFirstLetter("单lyz23!@");
        System.out.println("Str0:"+str0);
        System.out.println("Str1:"+str);


//        //ContactsDB.createContactsDB().createTable();
//        Map<String,String> map = new HashMap<String, String>();
//        map.put("a","aaa");
//        map.put("b","bbb");
//        map.put("c","ccc");
//        map.put("d","ddd");
//        map.put("e","eee");
//        map.put("f","fff");
//
//        System.out.println(map.keySet());
//
//        List<String> list = new ArrayList<String>();
//
//        for (String s:map.values()){
//            list.add(s);
//        }
//
//        for (String s:list){
//            System.out.println(s);
//        }

    }

}
