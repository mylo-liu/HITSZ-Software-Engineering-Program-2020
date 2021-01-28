package com.example.bookkeepingapp.MainFunction.MainFunction_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.bookkeepingapp.MainFunction.TabFragmentAdapter;
import com.example.bookkeepingapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainFunction_1 extends Fragment {
    private View viewContent;
    private TabLayout mf1_tablayout;
    private ViewPager mf1_viewpager;
    public static MainFunction_1 mainFunction1_fragment;

    private int mode = TabLayout.MODE_FIXED;

    public MainFunction_1() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContent = inflater.inflate(R.layout.mainfunction_1_fragment,container,false);
        initConentView(viewContent);
        initData();
        return viewContent;
    }

    public void initConentView(View viewContent) {
        this.mf1_tablayout = (TabLayout) viewContent.findViewById(R.id.mf1_tablayout);
        this.mf1_viewpager = (ViewPager) viewContent.findViewById(R.id.mf1_viewpager);
    }

    public void initData() {
        //创建一个viewpager的adapter
        TabFragmentAdapter adapter = new TabFragmentAdapter(getChildFragmentManager());
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new MF_1_SubFunction_1());
        fragments.add(new MF_1_SubFunction_2());
        fragments.add(new MF_1_SubFunction_3());
        String[] titlesArr = {"收入", "支出", "转账"};
        adapter.setTitlesArr(titlesArr);
        adapter.setFragments(fragments);
        this.mf1_viewpager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来
        this.mf1_tablayout.setupWithViewPager(this.mf1_viewpager);
        mf1_tablayout.setTabMode(mode);
    }
}
