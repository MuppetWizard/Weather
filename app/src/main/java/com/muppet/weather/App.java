package com.muppet.weather;

import android.app.Application;

import org.xutils.x;

public class App extends Application {

    public static Application application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        x.Ext.init(this);
        //输出debug日志
        x.Ext.setDebug(true);
    }
    //获取项目上下文
    public static Application getAppContext() {
        return application;
    }
}
