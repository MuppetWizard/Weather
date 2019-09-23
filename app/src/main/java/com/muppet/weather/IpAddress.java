package com.muppet.weather;

import androidx.annotation.NonNull;

public class IpAddress {

    private static final String Server_IP = "";
    private static final String Img_Server_IP = "http://service.picasso.adesk.com";//壁纸

    public static boolean isDebug = false ; //是否为调试模式

    public static String getUrl(@NonNull String url) {

        StringBuilder stringBuilder = new StringBuilder(Server_IP);
        if (!url.startsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append(url);
        return stringBuilder.toString();
    }

    public static String getImgUrl(@NonNull String url) {
        StringBuilder stringBuilder = new StringBuilder(Img_Server_IP);
        if (!url.startsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append(url);
        return stringBuilder.toString();
    }

    //登录
    public static final String LOGIN = "/apiuser/login";

    //默认显示壁纸
    public static final String IMG_NORMAL = "/v1/vertical/vertical";
    //获取手机壁纸类别
    public static final String IMG_CATEGORY = "/v1/vertical/category";
}
