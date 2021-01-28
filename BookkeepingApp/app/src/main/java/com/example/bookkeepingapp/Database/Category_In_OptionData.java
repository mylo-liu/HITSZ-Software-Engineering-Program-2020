package com.example.bookkeepingapp.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.bookkeepingapp.TestApplication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Category_In_OptionData {

    public static HashMap<String, HashSet> category_option = new HashMap<>();

    private static boolean isFirstTime = true;

    public static void init() {

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = TestApplication.getContext().getSharedPreferences(
                "Category_In_OptionData", Context.MODE_PRIVATE).edit();
        SharedPreferences pref = TestApplication.getContext().getSharedPreferences(
                "Category_In_OptionData", Context.MODE_PRIVATE);

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editorList = TestApplication.getContext().getSharedPreferences(
                "CategoryInList", Context.MODE_PRIVATE).edit();
        SharedPreferences prefList = TestApplication.getContext().getSharedPreferences(
                "CategoryInList", Context.MODE_PRIVATE);

        isFirstTime = pref.getBoolean("isFirstTime", true);

        if(isFirstTime) {
            editor.clear();
            editorList.clear();
            HashSet<String> hashSet_0 = new HashSet<>();
            hashSet_0.add("日结");
            hashSet_0.add("月结");
            hashSet_0.add("兼职");
            hashSet_0.add("年终奖");
            editor.putStringSet("0", hashSet_0);
            editorList.putString("0", "工资");
            category_option.put("工资", hashSet_0);

            HashSet<String> hashSet_1 = new HashSet<>();
            hashSet_1.add("股票");
            hashSet_1.add("基金");
            hashSet_1.add("期货");
            hashSet_1.add("其他");
            editor.putStringSet("1", hashSet_1);
            editorList.putString("1", "理财");
            category_option.put("理财", hashSet_1);

            HashSet<String> hashSet_2 = new HashSet<>();
            hashSet_2.add("人情");
            hashSet_2.add("礼金");
            hashSet_2.add("其他");
            editor.putStringSet("2", hashSet_2);
            editorList.putString("2", "其他");
            category_option.put("其他", hashSet_2);

            editor.putInt("size", 3);
            editorList.putInt("size", 3);

            editor.putBoolean("isFirstTime", false);
            editor.apply();
            editorList.apply();
            isFirstTime = false;
        }

        int size = pref.getInt("size", 0);
        HashSet hashSet = new HashSet();
        category_option.clear();
        for(int i = 0; i < size; i++){
            category_option.put(prefList.getString( String.valueOf(i), ""),
                    (HashSet) pref.getStringSet(String.valueOf(i), hashSet) );
//            Toast.makeText(TestApplication.getContext(), prefList.getString( String.valueOf(i),"none"), Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean category_1_add(String category){
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = TestApplication.getContext().getSharedPreferences(
                "Category_In_OptionData", Context.MODE_PRIVATE).edit();
        SharedPreferences pref = TestApplication.getContext().getSharedPreferences(
                "Category_In_OptionData", Context.MODE_PRIVATE);

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editorList = TestApplication.getContext().getSharedPreferences(
                "CategoryInList", Context.MODE_PRIVATE).edit();
        SharedPreferences prefList = TestApplication.getContext().getSharedPreferences(
                "CategoryInList", Context.MODE_PRIVATE);


        //判断是否新类别
        Iterator inter = category_option.entrySet().iterator();
        while (inter.hasNext()) {
            Map.Entry<String, HashSet> entry = (Map.Entry<String, HashSet>) inter.next();
            if (entry.getKey().equals(category)) {
//                Toast.makeText(TestApplication.getContext(), "分类1添加失败", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        //本地存储

        HashSet hashSet = new HashSet<String>();
        hashSet.add("请添加二级类别");

        int size = pref.getInt("size", 0);
        editor.putStringSet(String.valueOf(size), hashSet);
        editor.putInt("size", size+1);
        editorList.putString(String.valueOf(size), category);
        editorList.putInt("size", size+1);
        editor.apply();
        editorList.apply();

        //临时存储
        category_option.put(category, hashSet);
//        Toast.makeText(TestApplication.getContext(), "分类1添加成功", Toast.LENGTH_SHORT).show();
        return true;

    }

    public static boolean category_2_add(String category_1, String category_2){

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = TestApplication.getContext().getSharedPreferences(
                "Category_In_OptionData", Context.MODE_PRIVATE).edit();
        SharedPreferences pref = TestApplication.getContext().getSharedPreferences(
                "Category_In_OptionData", Context.MODE_PRIVATE);

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editorList = TestApplication.getContext().getSharedPreferences(
                "CategoryInList", Context.MODE_PRIVATE).edit();
        SharedPreferences prefList = TestApplication.getContext().getSharedPreferences(
                "CategoryInList", Context.MODE_PRIVATE);

        //创建一级类别
        category_1_add(category_1);

        Log.d("add", "pass1");

        //临时存储新添加二级类别
        HashSet hashSet = new HashSet();
        Iterator inter = category_option.entrySet().iterator();
        while (inter.hasNext()) {
            Map.Entry<String, HashSet> entry = (Map.Entry<String, HashSet>) inter.next();
            //匹配一级类别
            if(entry.getKey().equals(category_1)){
                hashSet = entry.getValue();
                Iterator inter1 = hashSet.iterator();
                while (inter1.hasNext()){ //二级类别重复判断
                    if (inter1.next().equals(category_2)){
                        return false;
                    }
                }
                hashSet.remove("请添加二级类别");
                hashSet.add(category_2);
                break;
            }
        }
        Log.d("add", "pass2");

        //本地存储
        int size = pref.getInt("size", 0);
        for(int i = 0; i < size; i++){
            if(prefList.getString(String.valueOf(i), "").equals(category_1)){
                Log.d("add", "pass3");
                editor.putStringSet(String.valueOf(i), hashSet);
                editor.apply();
                break;
            }
        }

        return true;
    }

}
