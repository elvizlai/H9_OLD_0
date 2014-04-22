package com.sdrzlyz.h9.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huagai on 14-4-22.
 */
public class YY extends SQLiteOpenHelper {

    public YY(Context paramContext){
        super(paramContext, "jhcs.contacts.db", null, 9);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
