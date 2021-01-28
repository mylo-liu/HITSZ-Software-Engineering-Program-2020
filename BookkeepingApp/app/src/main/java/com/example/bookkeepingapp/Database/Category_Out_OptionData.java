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

public class Category_Out_OptionData {

    public static HashMap<String, HashSet> category_option = new HashMap<>();

    private static boolean isFirstTime = true;

    public static void init() {

        SharedPreferences.Editor editor = TestApplication.getContext().getSharedPreferences(
                "Category_Out_OptionData", Context.MODE_PRIVATE).edit();
        SharedPreferences pref = TestApplication.getContext().getSharedPreferences(
                "Category_Out_OptionData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editorList = TestApplication.getContext().getSharedPreferences(
                "CategoryOutList", Context.MODE_PRIVATE).edit();
        SharedPreferences prefList = TestApplication.getContext().getSharedPreferences(
                "CategoryOutList", Context.MODE_PRIVATE);

        isFirstTime = pref.getBoolean("isFirstTime", true);

        if(isFirstTime) {
            editor.clear();
            editorList.clear();
            HashSet<String> hashSet_0 = new HashSet<>();
            hashSet_0.add("地铁");
            hashSet_0.add("公交");
            hashSet_0.add("出租车");
            hashSet_0.add("共享单车");
            editor.putStringSet("0", hashSet_0);
            editorList.putString("0", "交通");
            category_option.put("交通", hashSet_0);

            HashSet<String> hashSet_1 = new HashSet<>();
            hashSet_1.add("早餐");
            hashSet_1.add("午餐");
            hashSet_1.add("晚餐");
            hashSet_1.add("加餐");
            editor.putStringSet("1", hashSet_1);
            editorList.putString("1", "餐饮");
            category_option.put("餐饮", hashSet_1);

            HashSet<String> hashSet_2 = new HashSet<>();
            hashSet_2.add("日用");
            hashSet_2.add("蔬菜");
            hashSet_2.add("水果");
            hashSet_2.add("书籍");
            hashSet_2.add("服饰");
            editor.putStringSet("2", hashSet_2);
            editorList.putString("2", "购物");
            category_option.put("购物", hashSet_2);

            HashSet<String> hashSet_3 = new HashSet<>();
            hashSet_3.add("娱乐");
            hashSet_3.add("美容");
            hashSet_3.add("社交");
            hashSet_3.add("旅行");
            hashSet_3.add("医疗");
            editor.putStringSet("3", hashSet_3);
            editorList.putString("3", "其他");
            category_option.put("其他", hashSet_3);

            editor.putInt("size", 4);
            editorList.putInt("size", 4);

            editor.putBoolean("isFirstTime", false);
            editor.apply();
            editorList.apply();
            isFirstTime = false;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            return;
        }

        int size = pref.getInt("size", 0);
        HashSet hashSet = new HashSet();
        category_option.clear();
        for(int i = 0; i < size; i++){
            String s = prefList.getString( String.valueOf(i), "none");
            category_option.put(s, (HashSet) pref.getStringSet(String.valueOf(i), hashSet) );
//            Toast.makeText(TestApplication.getContext(), s, Toast.LENGTH_SHORT).show();
//            Toast.makeText(TestApplication.getContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
        }


    }

    public static boolean category_1_add(String category){
        SharedPreferences.Editor editor = TestApplication.getContext().getSharedPreferences(
                "Category_Out_OptionData", Context.MODE_PRIVATE).edit();
        SharedPreferences pref = TestApplication.getContext().getSharedPreferences(
                "Category_Out_OptionData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editorList = TestApplication.getContext().getSharedPreferences(
                "CategoryOutList", Context.MODE_PRIVATE).edit();
        SharedPreferences prefList = TestApplication.getContext().getSharedPreferences(
                "CategoryOutList", Context.MODE_PRIVATE);


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

        SharedPreferences.Editor editor = TestApplication.getContext().getSharedPreferences(
                "Category_Out_OptionData", Context.MODE_PRIVATE).edit();
        SharedPreferences pref = TestApplication.getContext().getSharedPreferences(
                "Category_Out_OptionData", Context.MODE_PRIVATE);

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editorList = TestApplication.getContext().getSharedPreferences(
                "CategoryOutList", Context.MODE_PRIVATE).edit();
        SharedPreferences prefList = TestApplication.getContext().getSharedPreferences(
                "CategoryOutList", Context.MODE_PRIVATE);

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
