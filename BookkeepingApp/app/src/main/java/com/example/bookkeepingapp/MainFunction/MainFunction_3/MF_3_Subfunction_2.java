package com.example.bookkeepingapp.MainFunction.MainFunction_3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.don.pieviewlibrary.PercentPieView;
import com.example.bookkeepingapp.Database.DataIn;
import com.example.bookkeepingapp.MainFunction.MainFunction_1.Bean;
import com.example.bookkeepingapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.bookkeepingapp.MainFunction.MainFunction_3.MF_3_Subfunction_1.DataList;

public class MF_3_Subfunction_2 extends Fragment {

    private Button btn_select;
    private ArrayList<Bean> options1Items = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();
    private List<Integer> dataList = new ArrayList<>();
    private int[] datatoshow = new int[]{};
    private String[] nametoshow = new String[]{};
    private PercentPieView pieView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mf_3_subfunction_2, container, false);

//        int[] color = new int[]{
//                getResources().getColor(R.color.blue),
//                getResources().getColor(R.color.red),
//                getResources().getColor(R.color.green),
//                getResources().getColor(R.color.purple)};

//        LinePieView pieView = (LinePieView) view.findViewById(R.id.pieView);
////设置指定颜色
//        pieView.setData(data, name, color);
        pieView = (PercentPieView) view.findViewById(R.id.pieView);
        pieView.setData(datatoshow, nametoshow);

        getOptionData();
        btn_select = (Button)view.findViewById(R.id.btn_select_mf3_sf2);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getPickerViewText();
//                                + " — " +category_options2Items.get(options1).get(option2);
//                                + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                        btn_select.setText(tx);
                        setDataToShow(tx);
                        pieView.setData(datatoshow, nametoshow);
                    }
                })
                        .setTitleText("选择展示分类")//标题
                        .build();
                pvOptions.setPicker(options1Items);
                pvOptions.show();
            }
        });
//使用随机颜色

        return view;
    }

    private void getOptionData() {

        if (options1Items.isEmpty()){
            options1Items.add(new Bean(0, "按一级分类展示"/*, "描述部分", "其他数据"*/));
            options1Items.add(new Bean(1, "按二级分类展示"/*, "描述部分", "其他数据"*/));
            options1Items.add(new Bean(2, "按成员展示支出"/*, "描述部分", "其他数据"*/));
            options1Items.add(new Bean(3, "按成员展示收入"/*, "描述部分", "其他数据"*/));
        }
    }

    private void setDataToShow(String selectStr){
        nameList.clear();
        dataList.clear();
        if (selectStr == "按一级分类展示"){
            int index;
            for(DataIn d : DataList){
                String name = d.getCategory_1();
                int data = (int)Float.parseFloat(d.getMoney());
                if (nameList.contains(name)){
                    index = nameList.indexOf(name);
                    dataList.set(index, dataList.get(index)+data);
                }
                else{
                    nameList.add(name);
                    dataList.add(0);
                    index = nameList.indexOf(name);
                    dataList.set(index, data);
                }
            }
            int n = dataList.size();
            if (n == 0){
                Toast.makeText(getActivity(), "数据不存在", Toast.LENGTH_SHORT).show();
            }
            datatoshow = new int[n];
            nametoshow = new String[n];
            for (int i=0; i<n; i++){
                datatoshow[i] = dataList.get(i);
                nametoshow[i] = nameList.get(i);
            }
        }
        else if (selectStr == "按二级分类展示"){
            int index;
            for(DataIn d : DataList){
                String name = d.getCategory_2();
                int data = (int)Float.parseFloat(d.getMoney());
                if (nameList.contains(name)){
                    index = nameList.indexOf(name);
                    dataList.set(index, dataList.get(index)+data);
                }
                else{
                    nameList.add(name);
                    dataList.add(0);
                    index = nameList.indexOf(name);
                    dataList.set(index, data);
                }
            }
            int n = dataList.size();
            if (n == 0){
                Toast.makeText(getActivity(), "数据不存在", Toast.LENGTH_SHORT).show();
            }
            datatoshow = new int[n];
            nametoshow = new String[n];
            for (int i=0; i<n; i++){
                datatoshow[i] = dataList.get(i);
                nametoshow[i] = nameList.get(i);
            }
        }
        else if (selectStr == "按成员展示支出"){
            int index;
            for(DataIn d : DataList){
                String name = d.getMember();
                int data = (int)Float.parseFloat(d.getMoney());
                if (d.getBill().equals("支出"))
                {
                    if (nameList.contains(name)){
                        index = nameList.indexOf(name);
                        dataList.set(index, dataList.get(index)+data);
                    }
                    else{
                        nameList.add(name);
                        dataList.add(0);
                        index = nameList.indexOf(name);
                        dataList.set(index, data);
                    }
                }
            }
            int n = dataList.size();
            if (n == 0){
                Toast.makeText(getActivity(), "数据不存在", Toast.LENGTH_SHORT).show();
            }
            datatoshow = new int[n];
            nametoshow = new String[n];
            for (int i=0; i<n; i++){
                datatoshow[i] = dataList.get(i);
                nametoshow[i] = nameList.get(i);
            }
        }
        else if (selectStr == "按成员展示收入"){
            int index;
            for(DataIn d : DataList){
                String name = d.getMember();
                int data = (int)Float.parseFloat(d.getMoney());
                if (d.getBill().equals("收入"))
                {
                    if (nameList.contains(name)){
                        index = nameList.indexOf(name);
                        dataList.set(index, dataList.get(index)+data);
                    }
                    else{
                        nameList.add(name);
                        dataList.add(0);
                        index = nameList.indexOf(name);
                        dataList.set(index, data);
                    }
                }
            }
            int n = dataList.size();
            if (n == 0){
                Toast.makeText(getActivity(), "数据不存在", Toast.LENGTH_SHORT).show();
            }
            datatoshow = new int[n];
            nametoshow = new String[n];
            for (int i=0; i<n; i++){
                datatoshow[i] = dataList.get(i);
                nametoshow[i] = nameList.get(i);
            }
        }
    }
    public static int get_int_index(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;//如果未找到返回-1
    }

    public static int get_String_index(String[] arr, String value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(value)) {
                return i;
            }
        }
        return -1;//如果未找到返回-1
    }


}
