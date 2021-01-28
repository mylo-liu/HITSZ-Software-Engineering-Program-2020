package com.example.bookkeepingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.bookkeepingapp.Checking.Activity_2;
import com.example.bookkeepingapp.Setting.Activity_1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        SharedPreferences pref = getSharedPreferences("NumPassword",MODE_PRIVATE);
        boolean passwordExist = pref.getBoolean("passwordExist", false);
        if (passwordExist){
            Activity_2.action(this);
        }
        else {
            Activity_1.action(this);
        }
        finish();
    }
}