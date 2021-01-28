package com.example.bookkeepingapp.Checking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bookkeepingapp.R;

public class Activity_2 extends AppCompatActivity {

    private Button button_Pattern;
    private Button button_Num;

    public static void action(Context context){
        Intent intent = new Intent(context, Activity_2.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        getSupportActionBar().hide();

        replace(new NumLockCheckingFragment());

        button_Pattern = findViewById(R.id.button_pattern);
        button_Num = findViewById(R.id.button_Num);

        button_Pattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new PatternLockCheckingFragment());
            }
        });

        button_Num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new NumLockCheckingFragment());
            }
        });

    }
    private void replace(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_2_fragment, fragment);
        transaction.commit();
    }
}