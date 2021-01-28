package com.example.bookkeepingapp.MainFunction.MainFunction_3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.bookkeepingapp.Database.Account_OptionData;
import com.example.bookkeepingapp.Database.Category_In_OptionData;
import com.example.bookkeepingapp.Database.Category_Out_OptionData;
import com.example.bookkeepingapp.Database.Member_OptionData;
import com.example.bookkeepingapp.MainFunction.MainFunction_1.Bean;
import com.example.bookkeepingapp.MainFunction.TabFragmentAdapter;
import com.example.bookkeepingapp.R;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainFunction_3 extends Fragment {
    private View viewContent;
    private TabLayout mf3_tablayout;
    private ViewPager mf3_viewpager;
    public static MainFunction_3 mainFunction3_fragment;
    private int mode = TabLayout.MODE_FIXED;
    public MainFunction_3() {
        // Required empty public constructor
    }

    //////////////////////////////////////////////
    private ArrayList<Bean> in_category_options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> in_category_options2Items = new ArrayList<>();
    private ArrayList<Bean> out_category_options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> out_category_options2Items = new ArrayList<>();

    private ArrayList<Bean> account_options1Items = new ArrayList<>();
    private ArrayList<Bean> member_options1Items = new ArrayList<>();
    private ArrayList<Bean> bill_options1Items = new ArrayList<>();

    private Button btn_time_begin;
    private Button btn_time_end;
    private Button btn_category;
    private Button btn_bill;
    private Button btn_account;
    private Button btn_member;

    private String bill = "支出";

    static public String account_mf3;
    static public String member_mf3;
    static public String bill_mf3;
    static public String category1_mf3;
    static public String category2_mf3;
    static public int year_begin = 0;
    static public int year_end = 9000;
    static public int month_begin = 1;
    static public int month_end = 1;
    static public int day_begin = 1;
    static public int day_end = 1;
    static public int hour_begin = 0;
    static public int hour_end = 0;
    static public int minute_begin = 0;
    static public int minute_end = 0;
    static public int second_begin = 0;
    static public int second_end = 0;

    //////////////////////////////////////////////


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContent = inflater.inflate(R.layout.mainfunction_3_fragment,container,false);
        initConentView(viewContent);
        initData();

        //////////////////////////////////////////////

        //每次加载OptionData前清空存储器，避免新添加的类别数据无法存入
        out_category_options1Items.clear();
        out_category_options2Items.clear();
        in_category_options1Items.clear();
        in_category_options2Items.clear();
        account_options1Items.clear();
        member_options1Items.clear();

        getOptionData();
        btn_time_begin = (Button) viewContent.findViewById(R.id.btn_time_mf3_begin);
        btn_time_end = (Button) viewContent.findViewById(R.id.btn_time_mf3_end);
        btn_category = (Button) viewContent.findViewById(R.id.btn_category_mf3_1);
        btn_bill = (Button) viewContent.findViewById(R.id.btn_bill);
        btn_account = (Button) viewContent.findViewById(R.id.btn_account_mf3);
        btn_member = (Button) viewContent.findViewById(R.id.btn_member_mf3);

        //-----开始时间选择-----
        btn_time_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        btn_time_begin.setText(getTime(date));
                        year_begin = date.getYear()+1900;
                        month_begin = date.getMonth()+1;
                        day_begin = date.getDate();
                        hour_begin = date.getHours();
                        minute_begin = date.getMinutes();
                        second_begin = date.getSeconds();
                    }
                })
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .setType(new boolean[]{true, true, true, true, true, true})//分别对应年月日时分秒，默认全部显示
                        .setTitleText("选择开始时间")//标题
                        .build();
                // pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();
            }
        });

        //-----结束时间选择-----
        btn_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        btn_time_end.setText(getTime(date));
                        year_end = date.getYear()+1900;
                        month_end = date.getMonth()+1;
                        day_end = date.getDate();
                        hour_end = date.getHours();
                        minute_end = date.getMinutes();
                        second_end = date.getSeconds();

                    }
                })
                        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                        .setType(new boolean[]{true, true, true, true, true, true})//分别对应年月日时分秒，默认全部显示
                        .setTitleText("选择结束时间")//标题
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
                        String tx;
                        if(bill.equals("收入")){
                            String a = in_category_options1Items.get(options1).getPickerViewText();
                            String b = in_category_options2Items.get(options1).get(option2);
                            tx = a + "-" + b;
//                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                            if (a.equals("全部"))
                            {
                                category1_mf3 = null;
                                category2_mf3 = null;
                            }
                            else {
                                category1_mf3 = in_category_options1Items.get(options1).getPickerViewText();
                                category2_mf3 = in_category_options2Items.get(options1).get(option2);
                            }

                        }
                        else {
                            String a = out_category_options1Items.get(options1).getPickerViewText();
                            String b = out_category_options2Items.get(options1).get(option2);
                            tx = a + "-" + b;
//                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                            if (a.equals("全部")){
                                category1_mf3 = null;
                                category2_mf3 = null;
                            }
                            else {
                                category1_mf3 = out_category_options1Items.get(options1).getPickerViewText();
                                category2_mf3 = out_category_options2Items.get(options1).get(option2);
                            }
                        }
                        btn_category.setText(tx);
                    }
                })
                        .setTitleText("选择类别")//标题
//                        .setCyclic(true, true, true)//循环与否
                        .build();
                if(bill.equals("收入")){
                    pvOptions.setPicker(in_category_options1Items, in_category_options2Items);
                }
                else {
                    pvOptions.setPicker(out_category_options1Items, out_category_options2Items);
                }
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
                        if (tx.equals("全部")){
                            account_mf3 = null;
                        }
                        else {
                            account_mf3 = tx;
                        }
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

        //收支选择
        btn_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView pvOptions = new  OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = bill_options1Items.get(options1).getPickerViewText();
//                                + " — " +options2Items.get(options1).get(option2);
//                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        btn_bill.setText(tx);
                        bill = tx;
                        bill_mf3 = tx;
                    }
                })
                        .setTitleText("选择收支")//标题
//                        .setCyclic(true, true, true)//循环与否
                        .build();
                pvOptions.setPicker(bill_options1Items);
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
                        if (tx.equals("全部")){
                            member_mf3 = null;
                        }
                        else {
                            member_mf3 = tx;
                        }

                    }
                })
                        .setTitleText("选择成员")//标题
//                        .setCyclic(true, true, true)//循环与否
                        .build();
                pvOptions.setPicker(member_options1Items);
                pvOptions.show();
            }
        });
        //////////////////////////////////////////////


        return viewContent;
    }

    public void initConentView(View viewContent) {
        this.mf3_tablayout = (TabLayout) viewContent.findViewById(R.id.mf3_tablayout);
        this.mf3_viewpager = (ViewPager) viewContent.findViewById(R.id.mf3_viewpager);
    }

    public void initData() {
        //创建一个viewpager的adapter
        TabFragmentAdapter adapter = new TabFragmentAdapter(getChildFragmentManager());
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new MF_3_Subfunction_1());
        fragments.add(new MF_3_Subfunction_2());
        String[] titlesArr = {"流水", "图表"};
        adapter.setTitlesArr(titlesArr);
        adapter.setFragments(fragments);
        this.mf3_viewpager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来
        this.mf3_tablayout.setupWithViewPager(this.mf3_viewpager);
        mf3_tablayout.setTabMode(mode);
    }


    //////////////////////////////////////////////

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        return format.format(date);

    }

    private void getOptionData() {
        /*
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        int i = 0;
        //category_选项1

        if (out_category_options1Items.isEmpty()){
//            out_category_options1Items.add(new Bean(0, "交通"/*, "描述部分", "其他数据"*/));
//            out_category_options1Items.add(new Bean(1, "餐饮"/*, "描述部分", "其他数据"*/));
//            out_category_options1Items.add(new Bean(2, "购物"/*, "描述部分", "其他数据"*/));
//            out_category_options1Items.add(new Bean(3, "其他"/*, "描述部分", "其他数据"*/));

            i = 0;
            Iterator inter = Category_Out_OptionData.category_option.entrySet().iterator();
            while (inter.hasNext()){
                Map.Entry<String, Set> entry = (Map.Entry<String, Set>)inter.next();
                out_category_options1Items.add(new Bean(i++, entry.getKey()/*, "描述部分", "其他数据"*/));

                //内层Set遍历
                ArrayList<String> out_category_options2Items_x = new ArrayList<>();
                Iterator inter1 = entry.getValue().iterator();
                while (inter1.hasNext()){
                    out_category_options2Items_x.add((String)inter1.next());
                }
                out_category_options2Items.add(out_category_options2Items_x);
            }

            out_category_options1Items.add(new Bean(i, "全部"/*, "描述部分", "其他数据"*/));
            ArrayList<String> out_category_options2Items_x = new ArrayList<>();
            out_category_options2Items_x.add("全部");
            out_category_options2Items.add(out_category_options2Items_x);
        }

        if (account_options1Items.isEmpty()){
            i = 0;
            for(String account : Account_OptionData.getList()){
                account_options1Items.add(new Bean(i, account));
                i++;
            }
            account_options1Items.add(new Bean(i, "全部"));
        }
        if (member_options1Items.isEmpty()){
            i = 0;
            for (String member : Member_OptionData.getList()) {
                member_options1Items.add(new Bean(i++, member));
            }
            member_options1Items.add(new Bean(i, "全部"));
        }
        if (bill_options1Items.isEmpty()){
            bill_options1Items.add(new Bean(0, "收入"));
            bill_options1Items.add(new Bean(1, "支出"));
        }


        //category_选项2
//        ArrayList<String> out_category_options2Items_01 = new ArrayList<>();
//        out_category_options2Items_01.add("地铁");
//        out_category_options2Items_01.add("公交");
//        out_category_options2Items_01.add("出租车");
//        out_category_options2Items_01.add("共享单车");
//        ArrayList<String> out_category_options2Items_02 = new ArrayList<>();
//        out_category_options2Items_02.add("早餐");
//        out_category_options2Items_02.add("午餐");
//        out_category_options2Items_02.add("晚餐");
//        out_category_options2Items_02.add("加餐");
//        ArrayList<String> out_category_options2Items_03 = new ArrayList<>();
//        out_category_options2Items_03.add("日用");
//        out_category_options2Items_03.add("蔬菜");
//        out_category_options2Items_03.add("水果");
//        out_category_options2Items_03.add("书籍");
//        out_category_options2Items_03.add("服饰");
//        ArrayList<String> out_category_options2Items_04 = new ArrayList<>();
//        out_category_options2Items_04.add("娱乐");
//        out_category_options2Items_04.add("美容");
//        out_category_options2Items_04.add("社交");
//        out_category_options2Items_04.add("旅行");
//        out_category_options2Items_04.add("医疗");
//        ArrayList<String> out_category_options2Items_05 = new ArrayList<>();
//        out_category_options2Items_05.add("全部");
//        out_category_options2Items.add(out_category_options2Items_01);
//        out_category_options2Items.add(out_category_options2Items_02);
//        out_category_options2Items.add(out_category_options2Items_03);
//        out_category_options2Items.add(out_category_options2Items_04);
//        out_category_options2Items.add(out_category_options2Items_05);


        if (in_category_options1Items.isEmpty()){
//            in_category_options1Items.add(new Bean(0, "工资"/*, "描述部分", "其他数据"*/));
//            in_category_options1Items.add(new Bean(1, "理财"/*, "描述部分", "其他数据"*/));
//            in_category_options1Items.add(new Bean(2, "其他"/*, "描述部分", "其他数据"*/));
//            in_category_options1Items.add(new Bean(3, "全部"/*, "描述部分", "其他数据"*/));

            i = 0;
            Iterator inter = Category_In_OptionData.category_option.entrySet().iterator();
            while (inter.hasNext()){
                Map.Entry<String, Set> entry = (Map.Entry<String, Set>)inter.next();
                in_category_options1Items.add(new Bean(i++, entry.getKey()/*, "描述部分", "其他数据"*/));

                //内层Set遍历
                ArrayList<String> in_category_options2Items_x = new ArrayList<>();
                Iterator inter1 = entry.getValue().iterator();
                while (inter1.hasNext()){
                    in_category_options2Items_x.add((String)inter1.next());
                }
                in_category_options2Items.add(in_category_options2Items_x);
            }

            in_category_options1Items.add(new Bean(i, "全部"/*, "描述部分", "其他数据"*/));
            ArrayList<String> in_category_options2Items_x = new ArrayList<>();
            in_category_options2Items_x.add("全部");
            in_category_options2Items.add(in_category_options2Items_x);
        }
        /*--------数据源添加完毕---------*/

        //category_选项2
//        ArrayList<String> in_category_options2Items_01 = new ArrayList<>();
//        in_category_options2Items_01.add("日结");
//        in_category_options2Items_01.add("月结");
//        in_category_options2Items_01.add("兼职");
//        in_category_options2Items_01.add("年终奖");
//        ArrayList<String> in_category_options2Items_02 = new ArrayList<>();
//        in_category_options2Items_02.add("股票");
//        in_category_options2Items_02.add("基金");
//        in_category_options2Items_02.add("期货");
//        in_category_options2Items_02.add("其他");
//        ArrayList<String> in_category_options2Items_03 = new ArrayList<>();
//        in_category_options2Items_03.add("人情");
//        in_category_options2Items_03.add("礼金");
//        in_category_options2Items_03.add("其他");
//        ArrayList<String> in_category_options2Items_04 = new ArrayList<>();
//        in_category_options2Items_04.add("全部");
//        in_category_options2Items.add(in_category_options2Items_01);
//        in_category_options2Items.add(in_category_options2Items_02);
//        in_category_options2Items.add(in_category_options2Items_03);
//        in_category_options2Items.add(in_category_options2Items_04);

    }
    //////////////////////////////////////////////
}