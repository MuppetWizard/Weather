package com.muppet.weather;

import androidx.annotation.NonNull;

public class IpAddress {

    private static final String Server_IP = "http://xiaogou.free.idcfengye.com";
    private static final String Img_Server_IP = "http://service.picasso.adesk.com";//壁纸
    private static final String Weather_Server_IP = "http://apis.juhe.cn";//聚合天气

    public static boolean isDebug = false ; //是否为调试模式

    //后台
    public static String getUrl(@NonNull String url) {

        StringBuilder stringBuilder = new StringBuilder(Server_IP);
        if (!url.startsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append(url);
        return stringBuilder.toString();
    }

    //图片
    public static String getImgUrl(@NonNull String url) {
        StringBuilder stringBuilder = new StringBuilder(Img_Server_IP);
        if (!url.startsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append(url);
        return stringBuilder.toString();
    }

    //天气
    public static String getWeatherUrl(@NonNull String url) {
        StringBuilder stringBuilder = new StringBuilder(Weather_Server_IP);
        if (!url.startsWith("/")) {
            stringBuilder.append("/");
        }
        stringBuilder.append(url);
        return stringBuilder.toString();
    }

    //注册
    public static final String REGISTER = "/uregister";

    //默认显示壁纸
    public static final String IMG_NORMAL = "/v1/vertical/vertical";
    //获取手机壁纸类别
    public static final String IMG_CATEGORY = "/v1/vertical/category";

    //天气
    public static final String WEATHER = "/simpleWeather/query";
}
