package com.example.bookkeepingapp.MainFunction.MainFunction_1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.bookkeepingapp.Database.Account_OptionData;
import com.example.bookkeepingapp.Database.DataIn;
import com.example.bookkeepingapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MF_1_SubFunction_3 extends Fragment {

    //-----输入金额相关变量-----
    private EditText edt_money;
    private TextView tv_pay;
    private String balance;
    //金额正则   0.00~9999999999.99
    String Money="(^[1-9](\\d+)?(\\.\\d{1,2})?$)|(^0$)|(^\\d\\.\\d{1,2}$)";

    //临时存储变量
    private String account_in;
    private String account_out;
    private String bill = "转账";
    private Date time;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private String remark;

    private EditText edit_remark;
    private Button btn_account_in;
    private Button btn_account_out;
    private Button btn_time;

    private ArrayList<Bean> account_options1Items = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mf_1_subfunction_3, container, false);

        //每次加载OptionData前清空存储器，避免新添加的类别数据无法存入
        account_options1Items.clear();

        getOptionData();


        //-----输入金额-----
        edt_money=view.findViewById(R.id.edt_money_3);
        tv_pay=view.findViewById(R.id.tv_pay_3);
        setEditChangedListener();
        onClickEvent();
        //-----输入金额-----

        btn_account_in = (Button) view.findViewById(R.id.btn_account_in_3);
        btn_account_out = (Button) view.findViewById(R.id.btn_account_out_3);
        edit_remark = view.findViewById(R.id.edit_remark_3);
        btn_time = (Button) view.findViewById(R.id.btn_time_3);

        tv_pay.setEnabled(ifCanChargeMoney());
        time = new Date();
        year = time.getYear() + 1900;
        month = time.getMonth() + 1;
        day = time.getDate();
        hour = time.getHours();
        minute = time.getMinutes();
        second = time.getSeconds();

        //-----时间选择-----
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        saveTime(date);//存储时间
                        btn_time.setText(getTime(date));
                    }
                })
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .setType(new boolean[]{true, true, true, true, true, true})//分别对应年月日时分秒，默认全部显示
                        .setTitleText("选择时间")//标题
                        .build();
                // pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
            }
        });

        //转入帐户选择
        btn_account_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = account_options1Items.get(options1).getPickerViewText();
//                                + " — " +options2Items.get(options1).get(option2);
//                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        btn_account_in.setText(tx);
                        account_in = tx;
                    }
                })
                        .setTitleText("选择帐户")//标题
//                        .setCyclic(true, true, true)//循环与否
                        .build();
                pvOptions.setPicker(account_options1Items);
                pvOptions.show();
            }
        });

        //转出帐户选择
        btn_account_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = account_options1Items.get(options1).getPickerViewText();
//                                + " — " +options2Items.get(options1).get(option2);
//                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        btn_account_out.setText(tx);
                        account_out = tx;
                    }
                })
                        .setTitleText("选择帐户")//标题
//                        .setCyclic(true, true, true)//循环与否
                        .build();
                pvOptions.setPicker(account_options1Items);
                pvOptions.show();
            }
        });

        return view;
    }

    //-----输入金额-----
    private void onClickEvent() {
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (account_in==null || account_out==null || balance==null){
                    Toast.makeText(getActivity(), "记账失败" ,Toast.LENGTH_SHORT).show();
                }
                else {
                    String str = "时间: " + getTime(time) + "\n转入帐户: " + account_in + "\n转出帐户: " + account_out +
                             "\n金额: " + balance;
                    Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();

                    remark = edit_remark.getText().toString();
                    DataIn dataIn = new DataIn();
                    dataIn.setMoney(balance);
                    dataIn.setBill(bill);
                    dataIn.setAccount_in(account_in);
                    dataIn.setAccount_out(account_out);
                    dataIn.setYear(year);
                    dataIn.setMonth(month);
                    dataIn.setDay(day);
                    dataIn.setHour(hour);
                    dataIn.setMinute(minute);
                    dataIn.setSecond(second);
                    dataIn.setRemark(remark);

                    dataIn.save();
                }
            }
        });
    }
    private boolean ifCanChargeMoney() {
        //输入为空
        if (TextUtils.isEmpty(edt_money.getText().toString())) return false;

        //获取输入余额
        balance=edt_money.getText().toString();

        //判断格式是否符合金额
        if (!balance.matches(Money)) return false;

        //判断金额不能小于等于 0
        if(Double.parseDouble(balance)<=0) return false;


        return true;
    }
    private void setEditChangedListener() {
        edt_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot > 0) if (temp.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }
                tv_pay.setEnabled(ifCanChargeMoney());
            }
        });
    }
    //-----输入金额-----

    //-----时间选择-----
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        return format.format(date);
    }
    //-----时间选择-----

    //存储时间
    private void saveTime(Date date){
        year = date.getYear() + 1900;
        month = date.getMonth() + 1;
        day = date.getDate();
        hour = date.getHours();
        minute = date.getMinutes();
        second = date.getSeconds();
    }

    private void getOptionData() {
        /*
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        //category_选项1
//        account_options1Items.add(new Bean(0, "支付宝"));
//        account_options1Items.add(new Bean(1, "微信"));
//        account_options1Items.add(new Bean(2, "储蓄卡"));
//        account_options1Items.add(new Bean(3, "信用卡"));
//        account_options1Items.add(new Bean(4, "余额宝"));
        /**
         * 账户数据加载
         */
        ////////////////////////////////////////////////
        int i = 0;
        account_options1Items.clear();
        for (String account : Account_OptionData.getList()) {
            account_options1Items.add(new Bean(i++, account));
        }

        /*--------数据源添加完毕---------*/
    }

}
