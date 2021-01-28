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
import com.example.bookkeepingapp.Database.Category_Out_OptionData;
import com.example.bookkeepingapp.Database.DataIn;
import com.example.bookkeepingapp.Database.Member_OptionData;
import com.example.bookkeepingapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MF_1_SubFunction_2 extends Fragment {

    private ArrayList<Bean> category_options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> category_options2Items = new ArrayList<>();

    private ArrayList<Bean> account_options1Items = new ArrayList<>();
    private ArrayList<Bean> member_options1Items = new ArrayList<>();

    //临时存储变量
    private String account;
    private String bill = "支出";
    private Date time;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    private String category_1;
    private String category_2;
    private String member;
    private String remark;


    //-----输入金额相关变量-----
    private EditText edt_money;
    private TextView tv_pay;
    private String balance;
    //金额正则   0.00~9999999999.99
    String Money="(^[1-9](\\d+)?(\\.\\d{1,2})?$)|(^0$)|(^\\d\\.\\d{1,2}$)";

    //-----按钮选择相关变量
    private Button btn_time;
    private Button btn_category;
    private Button btn_account;
    private Button btn_member;

    private EditText edit_remark;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mf_1_subfunction_2, container, false);

        //每次加载OptionData前清空存储器，避免新添加的类别数据无法存入
        category_options1Items.clear();
        category_options2Items.clear();
        account_options1Items.clear();
        member_options1Items.clear();

        getOptionData();

        btn_time = (Button) view.findViewById(R.id.btn_time_2);
        btn_category = (Button) view.findViewById(R.id.btn_category_2);
        btn_account = (Button) view.findViewById(R.id.btn_account_2);
        btn_member = (Button) view.findViewById(R.id.btn_member_2);
        edit_remark =  view.findViewById(R.id.edit_remark_2);

        //-----输入金额-----
        edt_money=view.findViewById(R.id.edt_money_2);
        tv_pay=view.findViewById(R.id.tv_pay_2);
        setEditChangedListener();
        onClickEvent();
        //-----输入金额-----

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

        //-----类别选择-----
        btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = category_options1Items.get(options1).getPickerViewText()
                                + " — " +category_options2Items.get(options1).get(option2);
//                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        btn_category.setText(tx);
                        saveCategory(category_options1Items.get(options1).getPickerViewText(),
                                category_options2Items.get(options1).get(option2));
                    }
                })
                        .setTitleText("选择类别")//标题
//                        .setCyclic(true, true, true)//循环与否
                        .build();
                pvOptions.setPicker(category_options1Items, category_options2Items);
                pvOptions.show();
            }
        });

        //帐户选择
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView pvOptions = new  OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = account_options1Items.get(options1).getPickerViewText();
//                                + " — " +options2Items.get(options1).get(option2);
//                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        account = tx;//存储账户变量
                        btn_account.setText(tx);
                    }
                })
                        .setTitleText("选择帐户")//标题
//                        .setCyclic(true, true, true)//循环与否
                        .build();
                pvOptions.setPicker(account_options1Items);
                pvOptions.show();
            }
        });

        //成员选择
        btn_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView pvOptions = new  OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = member_options1Items.get(options1).getPickerViewText();
//                                + " — " +options2Items.get(options1).get(option2);
//                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        btn_member.setText(tx);
                        member = tx;
                    }
                })
                        .setTitleText("选择成员")//标题
//                        .setCyclic(true, true, true)//循环与否
                        .build();
                pvOptions.setPicker(member_options1Items);
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
                if (account==null || category_1==null || category_2==null || member==null || balance==null){
                    Toast.makeText(getActivity(), "记账失败" ,Toast.LENGTH_SHORT).show();
                }
                else {
                    String str = "时间: " + getTime(time) + "\n帐户: " + account + "\n一级类别: " + category_1 +
                            "\n二级类别: " + category_2 + "\n成员: " + member + "\n金额: " + balance;
                    Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                    remark = edit_remark.getText().toString();//获取临时备注变量
                    //按下确认键临时变量存入数据库
                    DataIn dataIn = new DataIn();
                    dataIn.setMoney(balance);
                    dataIn.setAccount(account);
                    dataIn.setBill(bill);
                    dataIn.setYear(year);
                    dataIn.setMonth(month);
                    dataIn.setDay(day);
                    dataIn.setHour(hour);
                    dataIn.setMinute(minute);
                    dataIn.setSecond(second);
                    dataIn.setMember(member);
                    dataIn.setRemark(remark);
                    dataIn.setCategory_1(category_1);
                    dataIn.setCategory_2(category_2);
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

    //存储类别
    private void saveCategory(String category_1, String category_2){
        this.category_1 = category_1;
        this.category_2 = category_2;
    }



    private void getOptionData() {
        /*
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        //category_选项1
//        category_options1Items.add(new Bean(0, "交通"/*, "描述部分", "其他数据"*/));
//        category_options1Items.add(new Bean(1, "餐饮"/*, "描述部分", "其他数据"*/));
//        category_options1Items.add(new Bean(2, "购物"/*, "描述部分", "其他数据"*/));
//        category_options1Items.add(new Bean(2, "其他"/*, "描述部分", "其他数据"*/));
//        account_options1Items.add(new Bean(0, "支付宝"));
//        account_options1Items.add(new Bean(1, "微信"));
//        account_options1Items.add(new Bean(2, "储蓄卡"));
//        account_options1Items.add(new Bean(3, "信用卡"));
//        account_options1Items.add(new Bean(4, "余额宝"));
//        member_options1Items.add(new Bean(0, "默认"));
//        member_options1Items.add(new Bean(1, "成员1"));
//        member_options1Items.add(new Bean(2, "成员2"));
//        member_options1Items.add(new Bean(3, "成员3"));
//
//
        //category_选项2
//        ArrayList<String> category_options2Items_01 = new ArrayList<>();
//        category_options2Items_01.add("地铁");
//        category_options2Items_01.add("公交");
//        category_options2Items_01.add("出租车");
//        category_options2Items_01.add("共享单车");
//        ArrayList<String> category_options2Items_02 = new ArrayList<>();
//        category_options2Items_02.add("早餐");
//        category_options2Items_02.add("午餐");
//        category_options2Items_02.add("晚餐");
//        category_options2Items_02.add("加餐");
//        ArrayList<String> category_options2Items_03 = new ArrayList<>();
//        category_options2Items_03.add("日用");
//        category_options2Items_03.add("蔬菜");
//        category_options2Items_03.add("水果");
//        category_options2Items_03.add("书籍");
//        category_options2Items_03.add("服饰");
//        ArrayList<String> category_options2Items_04 = new ArrayList<>();
//        category_options2Items_04.add("娱乐");
//        category_options2Items_04.add("美容");
//        category_options2Items_04.add("社交");
//        category_options2Items_04.add("旅行");
//        category_options2Items_04.add("医疗");
//        category_options2Items.add(category_options2Items_01);
//        category_options2Items.add(category_options2Items_02);
//        category_options2Items.add(category_options2Items_03);
//        category_options2Items.add(category_options2Items_04);

        int i = 0;

        /**
         * 收入类别数据加载
         */
        /////////////////////////////////////////////////
        category_options1Items.clear();
        category_options2Items.clear();
        //外层Map遍历
        Iterator inter = Category_Out_OptionData.category_option.entrySet().iterator();
//        Toast.makeText(TestApplication.getContext(), Category_Out_OptionData.category_option.size()+"", Toast.LENGTH_SHORT).show();
        while (inter.hasNext()){
            Map.Entry<String, Set> entry = (Map.Entry<String, Set>)inter.next();
            category_options1Items.add(new Bean(i++, entry.getKey()/*, "描述部分", "其他数据"*/));
            Log.d("22222", entry.getKey());

            //内层Set遍历
            ArrayList<String> category_options2Items_x = new ArrayList<>();
            Iterator inter1 = entry.getValue().iterator();
            while (inter1.hasNext()){
                category_options2Items_x.add((String)inter1.next());
            }
            category_options2Items.add(category_options2Items_x);
        }
        /////////////////////////////////////////////////

        /**
         * 账户数据加载
         */
        ////////////////////////////////////////////////
        i = 0;
        account_options1Items.clear();
        for (String account : Account_OptionData.getList()) {
            account_options1Items.add(new Bean(i++, account));
        }

        /**
         * 成员数据加载
         */
        i = 0;
        member_options1Items.clear();
        for (String member : Member_OptionData.getList()) {
            member_options1Items.add(new Bean(i++, member));
        }


        /*--------数据源添加完毕---------*/
    }
}
