package com.example.bookkeepingapp.Checking;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookkeepingapp.MainFunction.Activity_3;
import com.example.bookkeepingapp.R;
import com.example.bookkeepingapp.Setting.NumLockPanel;

public class NumLockCheckingFragment extends Fragment {

    private NumLockPanel mNumLockPanel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.numlockchecking_fragment, container, false);

        mNumLockPanel = (NumLockPanel) view.findViewById(R.id.num_lock_checking);
        mNumLockPanel.setInputListener(new NumLockPanel.InputListener() {
            @Override
            public void inputFinish(String result) {

                SharedPreferences pref = getActivity().getSharedPreferences("NumPassword", Context.MODE_PRIVATE);
                String password = pref.getString("password", "");
                if (result.equals(password)){
                    Activity_3.action(getContext());
                    getActivity().finish();
                }
                else{
                    Toast.makeText(getActivity(), "密码错误", Toast.LENGTH_SHORT).show();
                    mNumLockPanel.showErrorStatus();
                }
            }
        });
        return view;

    }
}
