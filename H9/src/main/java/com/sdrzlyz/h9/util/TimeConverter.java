package com.sdrzlyz.h9.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sdrzlyz on 14-4-10.
 */
public class TimeConverter {
    //todo changed
    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private TimeConverter() {
        //是该类不可被实例化
    }

    /**
     * @param date
     * @return
     */
    public static String Date2dateStr(Date date) {
        return formatter.format(date);
    }

    public static Date DateStr2Date(String dateStr) {
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String Gettime2Str() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(new Date());
    }


}
