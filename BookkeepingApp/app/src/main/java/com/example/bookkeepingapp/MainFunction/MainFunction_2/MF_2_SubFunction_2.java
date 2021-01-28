package com.example.bookkeepingapp.MainFunction.MainFunction_2;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.bookkeepingapp.Database.Account_OptionData;
import com.example.bookkeepingapp.Database.DataIn;
import com.example.bookkeepingapp.MainFunction.MainFunction_1.Bean;
import com.example.bookkeepingapp.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MF_2_SubFunction_2 extends Fragment {
    private List<DataAccount> DataList = new ArrayList<>();
    private List<Integer> YearList = new ArrayList<>();


    private RecyclerView recyclerView;
    private DataAdapter_by_year adapter;

    ////////////////////////////////////////////////////
    private Button btn_account;
    private ArrayList<Bean> account_options1Items = new ArrayList<>();
    ////////////////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mf_2_subfunction_2, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_year);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        adapter = new DataAdapter_by_year(DataList);
        recyclerView.setAdapter(adapter);

        ////////////////////////////////////////////////////
        //每次加载OptionData前清空存储器，避免新添加的类别数据无法存入
        account_options1Items.clear();

        getOptionData();
        btn_account = (Button) view.findViewById(R.id.btn_account_mf2_sf2);
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = account_options1Items.get(options1).getPickerViewText();
//                                + " — " +options2Items.get(options1).get(option2);
//                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        btn_account.setText(tx);
                        initData(tx);
                        recyclerView.setAdapter(adapter);
                    }
                })
                        .setTitleText("选择帐户")//标题
//                        .setCyclic(true, true, true)//循环与否
                        .build();
                pvOptions.setPicker(account_options1Items);
                pvOptions.show();
            }
        });
        ////////////////////////////////////////////////////

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)


    private void initData(String account_input){
        String account;
        account = account_input;
        DataList.clear();
        YearList.clear();

        List<DataIn> dataIns = LitePal.findAll(DataIn.class);

        //创建各年时间
        for(DataIn dataIn : dataIns){
            if(dataIn.getBill().equals("转账")){
                if((dataIn.getAccount_in().equals(account) || dataIn.getAccount_out().equals(account))
                        && !YearList.contains(dataIn.getYear())){
                    YearList.add(dataIn.getYear());
                }
            } else {
                if(dataIn.getAccount().equals(account) && !YearList.contains(dataIn.getYear())) {
                    YearList.add(dataIn.getYear());
                }
            }
        }

        YearList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        //计算各年收支
        for(int year : YearList){
            DataAccount dataAccount = new DataAccount();
            dataAccount.setYear(year);
            float money_in = 0;
            float money_out = 0;
            float money_left = 0;

            for(DataIn dataIn : dataIns){
                if(dataIn.getBill().equals("转账") ){
                    if(dataIn.getAccount_in().equals(account) && dataIn.getYear()==year){
                        money_in += Float.parseFloat(dataIn.getMoney());
                        money_left += Float.parseFloat(dataIn.getMoney());
                    }else if(dataIn.getAccount_out().equals(account) && dataIn.getYear()==year){
                        money_out += Float.parseFloat(dataIn.getMoney());
                        money_left -= Float.parseFloat(dataIn.getMoney());
                    }
                } else if(dataIn.getBill().equals("支出")) {
                    if(dataIn.getAccount().equals(account) && dataIn.getYear()==year){
                        money_out += Float.parseFloat(dataIn.getMoney());
                        money_left -= Float.parseFloat(dataIn.getMoney());
                    }
                } else {
                    if(dataIn.getAccount().equals(account) && dataIn.getYear()==year){
                        money_in += Float.parseFloat(dataIn.getMoney());
                        money_left += Float.parseFloat(dataIn.getMoney());
                    }
                }
            }

            dataAccount.setMoney_in(String.valueOf(money_in));
            dataAccount.setMoney_out(String.valueOf(money_out));
            dataAccount.setMoney_left(String.valueOf(money_left));
            DataList.add(dataAccount);

            Log.d("Database",  year + "  " + String.valueOf(money_in) +  "  " + String.valueOf(money_out));

        }
    }


///////////////////////////////////////////////////////
    private void getOptionData() {
//        account_options1Items.add(new Bean(0, "支付宝"));
//        account_options1Items.add(new Bean(1, "微信"));
//        account_options1Items.add(new Bean(2, "储蓄卡"));
//        account_options1Items.add(new Bean(3, "信用卡"));
//        account_options1Items.add(new Bean(4, "余额宝"));
        int i = 0;
        for(String account : Account_OptionData.getList()){
            account_options1Items.add(new Bean(i, account));
            i++;
        }
        /*--------数据源添加完毕---------*/
    }
}
