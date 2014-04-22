package com.sdrzlyz.h9.test;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sdrzlyz.h9.config.ApplictionHandle;

/**
 * Created by huagai on 14-4-22.
 */
public class CreateContactsDB extends SQLiteOpenHelper {
    public CreateContactsDB(){
        super(ApplictionHandle.getContext(),"hg.contacts.db",null,1);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
