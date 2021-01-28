package com.example.bookkeepingapp.MainFunction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.bookkeepingapp.MainFunction.MainFunction_1.MainFunction_1;
import com.example.bookkeepingapp.MainFunction.MainFunction_2.MainFunction_2;
import com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3;
import com.example.bookkeepingapp.MainFunction.MainFunction_4.MainFunction_4;
import com.example.bookkeepingapp.R;

import java.util.ArrayList;
import java.util.List;

public class Activity_3 extends AppCompatActivity {

    private List<TabItem> mTableItemList = new ArrayList<>();

    public static void action(Context context){
        Intent intent = new Intent(context, Activity_3.class);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏掉整个ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_3);
        initTabData();
        initTabHost();
    }


    public void initTabData() {
        //添加tab
        mTableItemList.add(new TabItem(Activity_3.this, R.mipmap.addcircle, R.mipmap.addcircle_blue, "记账", MainFunction_1.class));
        mTableItemList.add(new TabItem(Activity_3.this, R.mipmap.browse, R.mipmap.browse_blue, "资产", MainFunction_2.class));
        mTableItemList.add(new TabItem(Activity_3.this, R.mipmap.chartpie, R.mipmap.chartpie_blue, "报表", MainFunction_3.class));
        mTableItemList.add(new TabItem(Activity_3.this, R.mipmap.setting, R.mipmap.setting_blue, "设置", MainFunction_4.class));
    }

    //初始化选项卡视图
    private void initTabHost() {
        //实例化FragmentTabHost对象
        FragmentTabHost fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);

        //去掉分割线
        fragmentTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i<mTableItemList.size(); i++) {
            TabItem tabItem = mTableItemList.get(i);
            //实例化一个TabSpec,设置tab的名称和视图
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(tabItem.getTitle()).setIndicator(tabItem.getView());
            fragmentTabHost.addTab(tabSpec,tabItem.getFragment(),null);

            //给Tab按钮设置背景
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.azure));

            //默认选中第一个tab
            if(i == 0) {
                tabItem.setChecked(true);
            }
        }

        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //重置Tab样式
                for (int i = 0; i< mTableItemList.size(); i++) {
                    TabItem tabitem = mTableItemList.get(i);
                    if (tabId.equals(tabitem.getTitle())) {
                        tabitem.setChecked(true);
                    }else {
                        tabitem.setChecked(false);
                    }
                }
            }
        });
    }


}