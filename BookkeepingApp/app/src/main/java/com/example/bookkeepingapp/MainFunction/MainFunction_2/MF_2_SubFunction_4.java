package com.example.bookkeepingapp.MainFunction.MainFunction_2;

import android.os.Bundle;
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

public class MF_2_SubFunction_4 extends Fragment {

    private List<DataIn> DataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mf_2_subfunction_4, container, false);

        initData();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_journal);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        DataAdapter_journal adapter = new DataAdapter_journal(DataList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initData(){
        DataList.clear();
        List<DataIn> dataIns = LitePal.findAll(DataIn.class);
        for(DataIn dataIn : dataIns){
            if (dataIn.getRemark() == ""){
                dataIn.setRemark("none");
            }
            DataList.add(dataIn);
        }

    }
}
