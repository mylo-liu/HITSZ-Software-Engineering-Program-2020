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
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.bookkeepingapp.Database.Account_OptionData;
import com.example.bookkeepingapp.Database.DataIn;
import com.example.bookkeepingapp.MainFunction.MainFunction_1.Bean;
import com.example.bookkeepingapp.R;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MF_2_SubFunction_3 extends Fragment {

    private List<DataAccount> DataList = new ArrayList<>();
    private List<Integer> MonthList = new ArrayList<>();
    private String account;
    private int year;

    private RecyclerView recyclerView;
    private DataAdapter_by_month adapter;

    ////////////////////////////////////////////////////
    private Button btn_account;
    private Button btn_year;
    private Button btn_reflash;
    private ArrayList<Bean> account_options1Items = new ArrayList<>();
    ////////////////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mf_2_subfunction_3, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_month);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        adapter = new DataAdapter_by_month(DataList);
        recyclerView.setAdapter(adapter);

        ////////////////////////////////////////////////////

        //每次加载OptionData前清空存储器，避免新添加的类别数据无法存入
        account_options1Items.clear();

        getOptionData();
        btn_account = (Button) view.findViewById(R.id.btn_account_mf2_sf3);
        btn_year = (Button) view.findViewById(R.id.btn_year_mf2_sf3);
        btn_reflash = (Button) view.findViewById(R.id.btn_reflash_mf2_sf3);

        btn_reflash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData(account,year);
                recyclerView.setAdapter(adapter);
            }
        });

        btn_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        btn_year.setText(getTime(date));
                        year = date.getYear()+1900;
                    }
                })
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .setType(new boolean[]{true, false, false, false, false, false})//分别对应年月日时分秒，默认全部显示
                        .setTitleText("选择时间")//标题
                        .build();
                // pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
            }
        });

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
                        account = tx;
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
    private void initData(String account_input, int year_input) {
        String account;
        account = account_input;
        int year;
        year = year_input;
        DataList.clear();
        MonthList.clear();

        List<DataIn> dataIns = LitePal.findAll(DataIn.class);

        //创建各月时间
        for (DataIn dataIn : dataIns) {
            if (dataIn.getYear() == year) {
                if (dataIn.getBill().equals("转账")) {
                    if ((dataIn.getAccount_in().equals(account) || dataIn.getAccount_out().equals(account))
                            && !MonthList.contains(dataIn.getMonth())) {
                        MonthList.add(dataIn.getMonth());
                    }
                } else {
                    if (dataIn.getAccount().equals(account) && !MonthList.contains(dataIn.getMonth())) {
                        MonthList.add(dataIn.getMonth());
                    }
                }
            }
        }

        MonthList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        //计算各月收支
        for(int month : MonthList){
            DataAccount dataAccount = new DataAccount();
            dataAccount.setMonth(month);
            float money_in = 0;
            float money_out = 0;
            float money_left = 0;

            for(DataIn dataIn : dataIns){
                if (dataIn.getYear() == year) {
                    if (dataIn.getBill().equals("转账")) {
                        if (dataIn.getAccount_in().equals(account) && dataIn.getMonth() == month) {
                            money_in += Float.parseFloat(dataIn.getMoney());
                            money_left += Float.parseFloat(dataIn.getMoney());
                        } else if (dataIn.getAccount_out().equals(account) && dataIn.getMonth() == month) {
                            money_out += Float.parseFloat(dataIn.getMoney());
                            money_left -= Float.parseFloat(dataIn.getMoney());
                        }
                    } else if (dataIn.getBill().equals("支出")) {
                        if (dataIn.getAccount().equals(account) && dataIn.getMonth() == month) {
                            money_out += Float.parseFloat(dataIn.getMoney());
                            money_left -= Float.parseFloat(dataIn.getMoney());
                        }
                    } else {
                        if (dataIn.getAccount().equals(account) && dataIn.getMonth() == month) {
                            money_in += Float.parseFloat(dataIn.getMoney());
                            money_left += Float.parseFloat(dataIn.getMoney());
                        }
                    }
                }
            }

            dataAccount.setMoney_in(String.valueOf(money_in));
            dataAccount.setMoney_out(String.valueOf(money_out));
            dataAccount.setMoney_left(String.valueOf(money_left));
            DataList.add(dataAccount);

            Log.d("Database",  month + "  " + String.valueOf(money_in) +  "  " + String.valueOf(money_out));

        }

    }
    ///////////////////////////////////////////////////////
    private void getOptionData() {
        if (account_options1Items.isEmpty()){
            int i = 0;
            for(String account : Account_OptionData.getList()){
                account_options1Items.add(new Bean(i, account));
                i++;
            }
        }
        /*--------数据源添加完毕---------*/
    }
    //-----时间选择-----
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年");
        return format.format(date);

    }
    //-----时间选择-----
}
