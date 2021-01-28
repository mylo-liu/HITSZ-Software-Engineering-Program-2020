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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkeepingapp.Database.DataIn;
import com.example.bookkeepingapp.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.account_mf3;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.bill_mf3;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.category1_mf3;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.category2_mf3;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.member_mf3;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.year_begin;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.year_end;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.month_begin;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.month_end;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.day_begin;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.day_end;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.hour_begin;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.hour_end;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.minute_begin;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.minute_end;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.second_begin;
import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MainFunction_3.second_end;


public class MF_3_Subfunction_1 extends Fragment {

    static public List<DataIn> DataList = new ArrayList<>();
    private Button btn_select;
    private Button btn_show_all;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mf_3_subfunction_1, container, false);

        btn_select = (Button) view.findViewById(R.id.btn_select);
        btn_show_all = (Button) view.findViewById(R.id.btn_show_all);
        initData();

        recyclerView = view.findViewById(R.id.recycler_view_mf3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        DataAdapter_MF_3 adapter = new DataAdapter_MF_3(DataList);
        recyclerView.setAdapter(adapter);

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflashData();
                DataAdapter_MF_3 adapter = new DataAdapter_MF_3(DataList);
                recyclerView.setAdapter(adapter);
            }
        });
        btn_show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                //retable();
                DataAdapter_MF_3 adapter = new DataAdapter_MF_3(DataList);
                recyclerView.setAdapter(adapter);
            }
        });
        return view;
    }



    private void initData(){
        DataList.clear();
        List<DataIn> dataIns = LitePal.findAll(DataIn.class);
        for(DataIn dataIn : dataIns){
            DataList.add(dataIn);
        }
        Iterator<DataIn> iterator = DataList.iterator();
        while(iterator.hasNext()) {
            DataIn data = iterator.next();
            if (data.getBill().equals("转账")){
                iterator.remove();
            }
        }
    }

    private void retable(){
        account_mf3 = null;
        member_mf3 = null;
        bill_mf3 = null;
        category1_mf3 = null;
        category2_mf3 = null;
        year_begin = 0;
        year_end = 9000;
        month_begin = 1;
        month_end = 1;
        day_begin = 1;
        day_end = 1;
        hour_begin = 0;
        hour_end = 0;
        minute_begin = 0;
        minute_end = 0;
        second_begin = 0;
        second_end = 0;
    }

    private void reflashData(){

        Log.d("begin time"," "+year_begin+" "+month_begin+" "+day_begin+" "+hour_begin+" "+minute_begin+" "+second_begin);
        Log.d("end time"," "+year_end+" "+month_end+" "+day_end+" "+hour_end+" "+minute_end+" "+second_end);

        DataList.clear();
        List<DataIn> dataIns = LitePal.findAll(DataIn.class);
        for(DataIn dataIn : dataIns){
            DataList.add(dataIn);
        }
        Iterator<DataIn> iterator = DataList.iterator();
        while(iterator.hasNext()){
            DataIn data =iterator.next();
            Log.d("cur time"," "+data.getYear()+" "+data.getMonth()+" "+data.getDay()+" "+data.getHour()+" "+data.getMinute()+" "+data.getSecond());
            List<HashMap<String, Integer>> mapList = initList(data.getYear(), data.getMonth(), data.getDay(),
                    data.getHour(), data.getMinute(), data.getSecond());

            if (data.getBill().equals("转账")){
                iterator.remove();
            }
            else if(account_mf3 != null && !data.getAccount().equals(account_mf3)){
                iterator.remove();
            }

            else if(member_mf3 != null && !data.getMember().equals(member_mf3)){
                iterator.remove();
            }

            else if(bill_mf3 != null && !data.getBill().equals(bill_mf3)){
                iterator.remove();
            }

            else if(category1_mf3 != null && category2_mf3 != null ){
                if (!data.getCategory_1().equals(category1_mf3) || !data.getCategory_2().equals(category2_mf3)){
                    iterator.remove();
                }
            }
            else if (!compareTime(mapList, mapList.get(0).get("begin"), mapList.get(0).get("cur"),
                    mapList.get(0).get("end"), 0)){
                iterator.remove();

                Log.d("remove because time",data.getAccount());
            }

        }
    }


    private HashMap<String, Integer> initMap(int begin, int cur, int end){
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        hashMap.put("begin", begin);
        hashMap.put("cur", cur);
        hashMap.put("end", end);
        return hashMap;
    }

    private List<HashMap<String, Integer>> initList(int year_cur, int month_cur, int day_cur,
                                                    int hour_cur, int minute_cur, int second_cur){
        List<HashMap<String, Integer>> mapList = new ArrayList<>();
        mapList.add(initMap(year_begin, year_cur, year_end));
        mapList.add(initMap(month_begin, month_cur, month_end));
        mapList.add(initMap(day_begin, day_cur, day_end));
        mapList.add(initMap(hour_begin, hour_cur, hour_end));
        mapList.add(initMap(minute_begin, minute_cur, minute_end));
        mapList.add(initMap(second_begin, second_cur, second_end));
        return mapList;
    }

    private Boolean compareTime(List<HashMap<String, Integer>> list, int begin, int cur, int end, int t){
        if(cur > begin && cur < end){
            return true;
        }
        if(begin == end && end == cur){
            if (t == 5) {
                return true;
            } else {
                return compareTime(list, list.get(t + 1).get("begin"), list.get(t + 1).get("cur"), list.get(t + 1).get("end"), t + 1);
            }
        }
        if(cur == begin){
            if (t == 5) {
                return true;
            } else {
                return compareTime(list, list.get(t + 1).get("begin"), list.get(t + 1).get("cur"), 9999, t + 1);
            }
        }
        if(cur == end) {
            if (t == 5) {
                return true;
            } else {
                return compareTime(list, -1, list.get(t + 1).get("cur"), list.get(t + 1).get("end"), t + 1);
            }
        }
        return false;

    }
}