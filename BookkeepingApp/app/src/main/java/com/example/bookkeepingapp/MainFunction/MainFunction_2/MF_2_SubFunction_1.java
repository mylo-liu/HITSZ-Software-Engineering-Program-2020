package com.example.bookkeepingapp.MainFunction.MainFunction_2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkeepingapp.Database.DataIn;
import com.example.bookkeepingapp.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MF_2_SubFunction_1 extends Fragment {

    private List<DataAccount> DataList = new ArrayList<>();
    private List<String> AccountList = new ArrayList<>();

    private RecyclerView recyclerView;
    private DataAdapter_account adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mf_2_subfunction_1, container, false);

        initData();

        recyclerView = view.findViewById(R.id.recycler_view_account);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        adapter = new DataAdapter_account(DataList);
        recyclerView.setAdapter(adapter);


//        Button button = view.findViewById(R.id.btn_delete);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DataList.clear();
//                adapter.notifyDataSetChanged();
//            }
//        });

        return view;
    }

    private void initData(){

        DataList.clear();
        AccountList.clear();

        //创建账户
        List<DataIn> dataIns = LitePal.findAll(DataIn.class);
        for(DataIn dataIn : dataIns){
            if(dataIn.getBill().equals("转账")){
                if(!AccountList.contains(dataIn.getAccount_in())){
                    AccountList.add(dataIn.getAccount_in());
                }
                if(!AccountList.contains(dataIn.getAccount_out())){
                    AccountList.add(dataIn.getAccount_out());
                }
            } else {
                if( !AccountList.contains(dataIn.getAccount()) && !dataIn.getAccount().equals("")){
                    AccountList.add(dataIn.getAccount());
                }
            }
        }

        //计算各账户收支
        for(String s : AccountList){
            DataAccount dataAccount = new DataAccount();
            dataAccount.setAccount(s);
            float money_in = 0;
            float money_out = 0;
            float money_left = 0;

            for(DataIn dataIn : dataIns){
                if(dataIn.getBill().equals("转账")){
                    if(dataIn.getAccount_in().equals(s)){
                        money_in += Float.parseFloat(dataIn.getMoney());
                        money_left += Float.parseFloat(dataIn.getMoney());
                    }
                    if(dataIn.getAccount_out().equals(s)){
                        money_out += Float.parseFloat(dataIn.getMoney());
                        money_left -= Float.parseFloat(dataIn.getMoney());
                    }
                } else if(dataIn.getBill().equals("支出")) {
                    if(dataIn.getAccount().equals(s)){
                        money_out += Float.parseFloat(dataIn.getMoney());
                        money_left -= Float.parseFloat(dataIn.getMoney());
                    }
                } else {
                    if(dataIn.getAccount().equals(s)){
                        money_in += Float.parseFloat(dataIn.getMoney());
                        money_left += Float.parseFloat(dataIn.getMoney());
                    }
                }
            }

            dataAccount.setMoney_in(String.valueOf(money_in));
            dataAccount.setMoney_out(String.valueOf(money_out));
            dataAccount.setMoney_left(String.valueOf(money_left));
            DataList.add(dataAccount);

            Log.d("Database", s + "  " + String.valueOf(money_in) +  "  " + String.valueOf(money_out));

        }



    }


}
