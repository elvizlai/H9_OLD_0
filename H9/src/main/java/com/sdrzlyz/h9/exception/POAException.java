package com.sdrzlyz.h9.exception;

import java.util.Properties;

/**
 * Created by sdrzlyz on 14-3-25.
 */
public class POAException extends Exception {


    public static final int ERROR_CATALOG_ERROR = 5;
    public static final int ERROR_DATAIlLEGAL_ERROR = 24;
    public static final int ERROR_EDIT_SERVICE_ERROR = 8;
    public static final int ERROR_FILENOTEXIST_ERROR = 22;
    public static final int ERROR_GETPIC_ERROR = 1;
    public static final int ERROR_JSONFROMAT_ERROR = 32;
    public static final int ERROR_JSONPARSE_ERROR = 25;
    public static final int ERROR_LOACTION_ERROR = 6;
    public static final int ERROR_NETWORK_ERROR = 3;
    public static final int ERROR_NOTEXIST_ERROR = 4;
    public static final int ERROR_OAUNBIND_ERROR = 23;
    public static final int ERROR_PICOUTOFMEMORY_ERROR = 16;
    public static final int ERROR_Response_ERROR = 20;
    public static final int ERROR_SSOSIGN_ERROR = 17;
    public static final int ERROR_TIMEOUT_CONNET_ERROR = 33;
    public static final int ERROR_TIMEOUT_ERROR = 21;
    public static final int ERROR_UPLOADFILE_ERROR = 9;
    public static final int ERROR_URL_ERROR = 19;
    public static final int ERROR_USER_ERROR = 18;
    public static final int ERROR_XMLPARSE_ERROR = 2;
    public static final int SUCCESS = 0;
    private static final long serialVersionUID = 1L;
    private static Properties codeProperties = new Properties();
    private int code = 0;
    private String errormessage;

    static {
        codeProperties.put(Integer.valueOf(0), "操作成功");
        codeProperties.put(Integer.valueOf(1), "图片获取异常");
        codeProperties.put(Integer.valueOf(2), "XML解析异常");
        codeProperties.put(Integer.valueOf(3), "网络异常");
        codeProperties.put(Integer.valueOf(4), "记录不存在");
        codeProperties.put(Integer.valueOf(5), "栏目名称异常");
        codeProperties.put(Integer.valueOf(20), "服务器返回错误");
        codeProperties.put(Integer.valueOf(6), "获取位置异常");
        codeProperties.put(Integer.valueOf(8), "保存异常服务问题");
        codeProperties.put(Integer.valueOf(9), "上传文件错误");
        codeProperties.put(Integer.valueOf(16), "图片太大");
        codeProperties.put(Integer.valueOf(17), "获取授权码失败");
        codeProperties.put(Integer.valueOf(18), "用户登录失败");
        codeProperties.put(Integer.valueOf(19), "服务器地址错误");
        codeProperties.put(Integer.valueOf(21), "操作超时错误");
        codeProperties.put(Integer.valueOf(22), "文件不存在");
        codeProperties.put(Integer.valueOf(24), "数据不合法");
        codeProperties.put(Integer.valueOf(25), "json解析异常");
        codeProperties.put(Integer.valueOf(32), "json格式化异常");
        codeProperties.put(Integer.valueOf(33), "连接超时");
    }

    public POAException(int paramInt) {
        this.code = paramInt;
    }

    public POAException(int paramInt, String paramString) {
        this.errormessage = paramString;
        this.code = paramInt;
    }

    public POAException(String paramString) {
        this.errormessage = paramString;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        if (this.code != 0)
            return (String) codeProperties.get(Integer.valueOf(this.code));
        return this.errormessage;
    }

}
