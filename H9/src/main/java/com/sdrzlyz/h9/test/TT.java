package com.sdrzlyz.h9.test;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by sdrzlyz on 14-4-14.
 */
public class TT {
    static String str = "{\"mes\":null,\"success\":1,\"addressListGroupInfos\":null,\"companyContacts\":null,\"crmContacts\":null,\"departs\":[{\"deptId\":\"1\",\"deptName\":\"上海华盖科技有限公司\",\"layer\":\"00001\",\"parentId\":\"\"},{\"deptId\":\"1069\",\"deptName\":\"华盖软件公司\",\"layer\":\"0000100001\",\"parentId\":\"1\"},{\"deptId\":\"1077\",\"deptName\":\"项目市场部\",\"layer\":\"000010000100001\",\"parentId\":\"1069\"},{\"deptId\":\"1078\",\"deptName\":\"产品研发部\",\"layer\":\"000010000100002\",\"parentId\":\"1069\"}],\"hasCompanyMore\":false,\"hasCrmMore\":false,\"hasDepartMore\":false,\"hasOWnMore\":false,\"hasPublicMore\":false,\"ownContacts\":null,\"publicContacts\":null}";

    public static void main(String[] args) throws IOException{
        JsonReader jsonReader = new JsonReader(new StringReader(str));
        JsonToken jsonToken = jsonReader.peek();
        if (jsonToken==JsonToken.BEGIN_OBJECT){
            System.out.println("1");
        }
        System.out.println("end");
    }
}
