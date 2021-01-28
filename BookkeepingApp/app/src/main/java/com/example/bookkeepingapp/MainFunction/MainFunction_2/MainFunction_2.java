package com.example.bookkeepingapp.MainFunction.MainFunction_2;

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

public class MainFunction_2 extends Fragment {
    private View viewContent;
    private TabLayout mf2_tablayout;
    private ViewPager mf2_viewpager;
    public static MainFunction_2 mainFunction2_fragment;

    private int mode = TabLayout.MODE_FIXED;

    public MainFunction_2() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContent = inflater.inflate(R.layout.mainfunction_2_fragment,container,false);
        initConentView(viewContent);
        initData();
        return viewContent;
    }

    public void initConentView(View viewContent) {
        this.mf2_tablayout = (TabLayout) viewContent.findViewById(R.id.mf2_tablayout);
        this.mf2_viewpager = (ViewPager) viewContent.findViewById(R.id.mf2_viewpager);
    }

    public void initData() {
        //创建一个viewpager的adapter
        TabFragmentAdapter adapter = new TabFragmentAdapter(getChildFragmentManager());
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new MF_2_SubFunction_1());
        fragments.add(new MF_2_SubFunction_2());
        fragments.add(new MF_2_SubFunction_3());
        fragments.add(new MF_2_SubFunction_4());

        String[] titlesArr = {"帐户", "年度", "月度", "流水"};
        adapter.setTitlesArr(titlesArr);
        adapter.setFragments(fragments);

        this.mf2_viewpager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来
        this.mf2_tablayout.setupWithViewPager(this.mf2_viewpager);
        mf2_tablayout.setTabMode(mode);
    }
}
