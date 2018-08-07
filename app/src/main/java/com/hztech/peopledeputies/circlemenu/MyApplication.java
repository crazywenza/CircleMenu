package com.hztech.peopledeputies.circlemenu;

import android.app.Application;

import com.blankj.utilcode.util.Utils;


public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化util
        Utils.init(this);
    }
}
