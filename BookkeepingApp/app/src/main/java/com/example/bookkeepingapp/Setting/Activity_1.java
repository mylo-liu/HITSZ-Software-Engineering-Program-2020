package com.example.bookkeepingapp.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.bookkeepingapp.R;

public class Activity_1 extends AppCompatActivity {

    public static void action(Context context){
        Intent intent = new Intent(context, Activity_1.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        getSupportActionBar().hide();

        replace(new NumLockSettingFrament());

    }

    private void replace(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.SetLock, fragment);
        transaction.commit();
    }

}