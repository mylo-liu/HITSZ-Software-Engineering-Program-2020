package com.example.bookkeepingapp.Setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bookkeepingapp.R;

public class NumLockSettingFrament extends Fragment {

    private NumLockPanel mNumLockPanel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.numlocksetting_fragment, container, false);

        mNumLockPanel = (NumLockPanel) view.findViewById(R.id.num_lock_setting);
        mNumLockPanel.setInputListener(new NumLockPanel.InputListener() {
            @Override
            public void inputFinish(String result) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("NumPassword", getActivity().MODE_PRIVATE).edit();
                editor.putString("password", result);
                editor.putBoolean("passwordExist", true);
                editor.apply();

                replace(new PatternLockSettingFragment());

            }
        });
        return view;
    }

    private void replace(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.SetLock, fragment);
        transaction.commit();
    }

}
