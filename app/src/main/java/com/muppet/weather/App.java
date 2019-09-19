package com.muppet.weather;

import android.app.Application;

import org.xutils.x;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //输出debug日志
        x.Ext.setDebug(true);
    }
}
