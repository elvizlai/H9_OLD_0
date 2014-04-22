package com.sdrzlyz.h9.database;

import android.os.AsyncTask;
import com.sdrzlyz.h9.exception.POAException;
import com.sdrzlyz.h9.impl.ContactService;

/**
 * Created by huagai on 14-4-22.
 */
public class ManipulateContacts {


    public void updateContacts(){
        new AsyncTask<Void,Integer,Object>(){

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

            }
        }.execute();
    }

}
