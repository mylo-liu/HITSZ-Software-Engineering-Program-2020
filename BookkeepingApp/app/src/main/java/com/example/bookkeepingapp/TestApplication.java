package com.example.bookkeepingapp;

import android.app.Application;
import android.content.Context;

import com.example.bookkeepingapp.Database.Account_OptionData;
import com.example.bookkeepingapp.Database.Category_In_OptionData;
import com.example.bookkeepingapp.Database.Category_Out_OptionData;
import com.example.bookkeepingapp.Database.Member_OptionData;

/**
 * Created by hsg on 14/10/2017.
 */

public class TestApplication extends Application {

    private static Context context;

    private static boolean First = true;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //初始化选择器数据
        Account_OptionData.init();
        Member_OptionData.init();
        Category_In_OptionData.init();
        Category_Out_OptionData.init();
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isFirst(){
        return First;
    }

    public static void used(){
        First = false;
    }
}
