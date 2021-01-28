package com.example.bookkeepingapp.MainFunction.MainFunction_4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookkeepingapp.Database.Account_OptionData;
import com.example.bookkeepingapp.Database.Category_In_OptionData;
import com.example.bookkeepingapp.Database.Category_Out_OptionData;
import com.example.bookkeepingapp.Database.DataIn;
import com.example.bookkeepingapp.Database.Member_OptionData;
import com.example.bookkeepingapp.R;
import com.example.bookkeepingapp.TestApplication;

import org.litepal.LitePal;

public class MainFunction_4 extends Fragment {

    private Button button_delete;
    private Button button_delete_database;

    private EditText edit_account;
    private Button btn_accountAdd;

    private EditText edit_category_1;
    private EditText edit_category_2;
    private Button btn_category_1_Add;
    private Button btn_category_2_Add;
    private RadioGroup radioGroup_1;

    private EditText edit_member;
    private Button btn_memberAdd;

    private String select = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.mainfunction_4_fragment, container, false);

        button_delete = view.findViewById(R.id.delete_allPassword);
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("NumPassword", getActivity().MODE_PRIVATE).edit();
                editor.putBoolean("passwordExist", false);
                editor.apply();
            }
        });

        button_delete_database = view.findViewById(R.id.delete_Database);
        button_delete_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(DataIn.class);
            }
        });

        edit_account = view.findViewById(R.id.edit_account);
        btn_accountAdd = view.findViewById(R.id.btn_accountAdd);
        btn_accountAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edit_account.getText().toString().equals("")){
                    Account_OptionData.add(edit_account.getText().toString());
                }
            }
        });

        radioGroup_1 = view.findViewById(R.id.radiogroup_1);
        radioGroup_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                select = radioButton.getText().toString();
//                Toast.makeText(TestApplication.getContext(), "选中"+select, Toast.LENGTH_SHORT).show();
            }
        });

        edit_category_1 = view.findViewById(R.id.edit_category_1);
//        btn_category_1_Add = view.findViewById(R.id.btn_category_1_Add);
//        btn_category_1_Add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!edit_category_1.getText().toString().equals("")){
//                    if(select.equals("收入")) {
//                        Category_In_OptionData.category_1_add(edit_category_1.getText().toString());
//                        Toast.makeText(TestApplication.getContext(), "分类添加", Toast.LENGTH_SHORT).show();
//                    }else if(select.equals("支出")){
//                        Category_Out_OptionData.category_1_add(edit_category_1.getText().toString());
//                        Toast.makeText(TestApplication.getContext(), "分类添加", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(TestApplication.getContext(), "请先选择收入或支出", Toast.LENGTH_SHORT).show();
//                    }
////                }
//            }
//        });

        edit_category_2 = view.findViewById(R.id.edit_category_2);
        btn_category_2_Add = view.findViewById(R.id.btn_category_2_Add);
        btn_category_2_Add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!edit_category_1.getText().toString().equals("") && !edit_category_2.getText().toString().equals("")){
                    if(select.equals("收入")) {
                        Category_In_OptionData.category_2_add(edit_category_1.getText().toString(),
                                edit_category_2.getText().toString());
                        Toast.makeText(TestApplication.getContext(), "类别添加成功", Toast.LENGTH_SHORT).show();
                    }else if(select.equals("支出")){
                        Category_Out_OptionData.category_2_add(edit_category_1.getText().toString(),
                                edit_category_2.getText().toString());
                        Toast.makeText(TestApplication.getContext(), "类别添加成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(TestApplication.getContext(), "请先选择收入或支出", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        edit_member = view.findViewById(R.id.edit_member);
        btn_memberAdd = view.findViewById(R.id.btn_memberAdd);
        btn_memberAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!edit_member.getText().toString().equals("")) {
                    Member_OptionData.add(edit_member.getText().toString());
                }
            }
        });


        return view;
    }
}
