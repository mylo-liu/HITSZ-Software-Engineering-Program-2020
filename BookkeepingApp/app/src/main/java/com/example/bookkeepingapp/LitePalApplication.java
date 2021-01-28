package com.example.bookkeepingapp;

import android.content.Context;

import org.litepal.LitePal;

public class LitePalApplication extends TestApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        mContext = getApplicationContext();
    }

    public static Context getInstance() {
        return mContext;
    }
}
