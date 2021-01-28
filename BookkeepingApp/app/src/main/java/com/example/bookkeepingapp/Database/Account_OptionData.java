package com.example.bookkeepingapp.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.bookkeepingapp.TestApplication;

import java.util.ArrayList;
import java.util.List;

public class Account_OptionData {

    public static List<String> account_option = new ArrayList<>();

    private static boolean isFirstTime = true;

    public static void init(){

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = TestApplication.getContext().getSharedPreferences(
                "Account_OptionData", Context.MODE_PRIVATE).edit();
        SharedPreferences pref = TestApplication.getContext().getSharedPreferences(
                "Account_OptionData", Context.MODE_PRIVATE);
        isFirstTime = pref.getBoolean("isFirstTime", true);
        //第一次打开程序
        if(isFirstTime) {
            editor.putString("0","支付宝");
            editor.putString("1","微信");
            editor.putString("2","储蓄卡");
            editor.putString("3","信用卡");
            editor.putString("4","余额宝");
            editor.putInt("size", 5);
            editor.putBoolean("isFirstTime", false);
            editor.apply();
            isFirstTime = false;
        }

        //从本地读取Account数据
        int size = pref.getInt("size", 0);
        account_option.clear();
        for(int i = 0; i < size; i++){
            account_option.add(pref.getString(String.valueOf(i), ""));
        }

    }

    public static void add(String account){
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = TestApplication.getContext().getSharedPreferences(
                "Account_OptionData", Context.MODE_PRIVATE).edit();
        SharedPreferences pref = TestApplication.getContext().getSharedPreferences(
                "Account_OptionData", Context.MODE_PRIVATE);

        for(String string : account_option){
            if(string.equals(account)){
                Toast.makeText(TestApplication.getContext(), "账户已存在", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //存入本地
        int size = pref.getInt("size", 0);
        editor.putInt("size", size+1);
        editor.putString(String.valueOf(size), account);
        editor.apply();
        //存入list
        account_option.add(account);
        Toast.makeText(TestApplication.getContext(), "账户添加成功", Toast.LENGTH_SHORT).show();
    }

    public static List<String> getList(){
        return account_option;
    }

    public static int getSize(){
        return account_option.size();
    }
}
