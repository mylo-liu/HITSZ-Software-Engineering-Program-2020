package com.example.bookkeepingapp.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.bookkeepingapp.TestApplication;

import java.util.ArrayList;
import java.util.List;

public class Member_OptionData {
    public static List<String> member_option = new ArrayList<>();

    private static boolean isFirstTime = true;

    public static void init(){

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = TestApplication.getContext().getSharedPreferences(
                "Member_OptionData", Context.MODE_PRIVATE).edit();
        SharedPreferences pref = TestApplication.getContext().getSharedPreferences(
                "Member_OptionData", Context.MODE_PRIVATE);
        isFirstTime = pref.getBoolean("isFirstTime", true);
        //第一次打开程序
        if(isFirstTime) {
            editor.clear();
            editor.putString("0","默认");
            editor.putString("1","成员1");
            editor.putString("2","成员2");
            editor.putString("3","成员3");
            editor.putInt("size", 4);
            editor.putBoolean("isFirstTime", false);
            editor.apply();
            isFirstTime = false;
        }

        //从本地读取member数据
        int size = pref.getInt("size", 0);
        member_option.clear();
        for(int i = 0; i < size; i++){
            member_option.add(pref.getString(String.valueOf(i), ""));
        }

    }

    public static void add(String member){
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = TestApplication.getContext().getSharedPreferences(
                "Member_OptionData", Context.MODE_PRIVATE).edit();
        SharedPreferences pref = TestApplication.getContext().getSharedPreferences(
                "Member_OptionData", Context.MODE_PRIVATE);

        for(String string : member_option){
            if(string.equals(member)){
                Toast.makeText(TestApplication.getContext(), "成员已存在", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //存入本地
        int size = pref.getInt("size", 0);
        editor.putInt("size", size+1);
        editor.putString(String.valueOf(size), member);
        editor.apply();
        //存入list
        member_option.add(member);
        Toast.makeText(TestApplication.getContext(), "成员添加成功", Toast.LENGTH_SHORT).show();
    }

    public static List<String> getList(){
        return member_option;
    }

    public static int getSize(){
        return member_option.size();
    }
}
