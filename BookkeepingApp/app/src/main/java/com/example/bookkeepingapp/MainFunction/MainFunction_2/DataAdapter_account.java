package com.example.bookkeepingapp.MainFunction.MainFunction_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkeepingapp.R;

import java.util.List;

public class DataAdapter_account extends RecyclerView.Adapter<DataAdapter_account.ViewHolder> {
    private List<DataAccount> mDataList;

    public DataAdapter_account(List<DataAccount> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public DataAdapter_account.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_item, parent, false);

        final DataAdapter_account.ViewHolder holder = new DataAdapter_account.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter_account.ViewHolder holder, int position) {
        DataAccount dataAccount = mDataList.get(position);

        holder.text_account.setText(dataAccount.getAccount());
        holder.text_money_in.setText(dataAccount.getMoney_in()+"元");
        holder.text_money_out.setText(dataAccount.getMoney_out()+"元");
        holder.text_money_left.setText(dataAccount.getMoney_left()+"元");
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View dataView;
        TextView text_account;
        TextView text_money_in;
        TextView text_money_out;
        TextView text_money_left;

        public ViewHolder(View view) {
            super(view);
            dataView = view;
            text_account = view.findViewById(R.id.text_account);
            text_money_in = view.findViewById(R.id.text_money_in);
            text_money_out = view.findViewById(R.id.text_money_out);
            text_money_left = view.findViewById(R.id.text_money_left);
        }
    }
}
