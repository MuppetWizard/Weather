package com.muppet.weather;

import androidx.annotation.NonNull;

public class IpAddress {

    public static final String Server_IP = "http://192.168.0.51:8019";

    public static boolean isDebug = false ; //是否为调试模式

    public static String getUrl(@NonNull String url) {

        StringBuilder stringBuilder = new StringBuilder(Server_IP);
        if (!url.startsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append(url);
        return stringBuilder.toString();
    }
    //登录
    public static final String LOGIN = "/apiuser/login";
}
