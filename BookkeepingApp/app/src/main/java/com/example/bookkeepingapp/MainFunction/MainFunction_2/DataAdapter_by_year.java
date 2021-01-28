package com.example.bookkeepingapp.MainFunction.MainFunction_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkeepingapp.R;

import java.util.List;

public class DataAdapter_by_year extends RecyclerView.Adapter<DataAdapter_by_year.ViewHolder> {

    private List<DataAccount> mDataList;

    public DataAdapter_by_year(List<DataAccount> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.year_item, parent, false);

        final DataAdapter_by_year.ViewHolder holder = new DataAdapter_by_year.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataAccount dataAccount = mDataList.get(position);

        holder.text_year.setText(String.valueOf(dataAccount.getYear())+"年");
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
        TextView text_year;
        TextView text_money_in;
        TextView text_money_out;
        TextView text_money_left;

        public ViewHolder(View view) {
            super(view);
            dataView = view;
            text_year = view.findViewById(R.id.text_year);
            text_money_in = view.findViewById(R.id.text_money_in);
            text_money_out = view.findViewById(R.id.text_money_out);
            text_money_left = view.findViewById(R.id.text_money_left);
        }
    }
}
