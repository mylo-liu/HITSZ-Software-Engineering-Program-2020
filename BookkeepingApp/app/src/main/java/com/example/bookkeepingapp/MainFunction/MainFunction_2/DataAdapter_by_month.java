package com.example.bookkeepingapp.MainFunction.MainFunction_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkeepingapp.R;

import java.util.List;

public class DataAdapter_by_month extends RecyclerView.Adapter<DataAdapter_by_month.ViewHolder>{

    private List<DataAccount> mDataList;

    public DataAdapter_by_month(List<DataAccount> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.month_item, parent, false);

        final DataAdapter_by_month.ViewHolder holder = new DataAdapter_by_month.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataAccount dataAccount = mDataList.get(position);

        holder.text_month.setText(String.valueOf(dataAccount.getMonth())+"月");
        holder.text_money_in.setText(String.valueOf(dataAccount.getMoney_in())+"元");
        holder.text_money_out.setText(String.valueOf(dataAccount.getMoney_out())+"元");
        holder.text_money_left.setText(String.valueOf(dataAccount.getMoney_left())+"元");
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View dataView;
        TextView text_month;
        TextView text_money_in;
        TextView text_money_out;
        TextView text_money_left;

        public ViewHolder(View view) {
            super(view);
            dataView = view;
            text_month = view.findViewById(R.id.text_month);
            text_money_in = view.findViewById(R.id.text_money_in);
            text_money_out = view.findViewById(R.id.text_money_out);
            text_money_left = view.findViewById(R.id.text_money_left);
        }
    }
}
