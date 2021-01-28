package com.example.bookkeepingapp.MainFunction.MainFunction_3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookkeepingapp.Database.DataIn;
import com.example.bookkeepingapp.R;

import java.util.List;

public class DataAdapter_MF_3 extends RecyclerView.Adapter<DataAdapter_MF_3.ViewHolder> {

    private List<DataIn> mDataList;

    public DataAdapter_MF_3(List<DataIn> mDataList) {
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public DataAdapter_MF_3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mf_3_item, parent, false);

        final ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter_MF_3.ViewHolder holder, int position) {
        DataIn dataIn = mDataList.get(position);

        String time = dataIn.getYear() + "年" + dataIn.getMonth() + "月" + dataIn.getDay() + "日  "
                + dataIn.getHour() + ":" + dataIn.getMinute() + ":" + dataIn.getSecond();
        String category = dataIn.getCategory_1() + " — " + dataIn.getCategory_2();

        String remark;
        if (dataIn.getRemark() == null) {
            remark = "无";
        } else {
            remark = dataIn.getRemark();
        }

        if (dataIn.getBill().equals("转账")) {
            holder.text_bill.setText(dataIn.getBill());
            holder.text_money.setText(dataIn.getMoney()+" 元");
            holder.text_time.setText("时间：" + time);
            holder.text_account_in.setText("转入账户：" + dataIn.getAccount_in());
            holder.text_account_out.setText("转出账户：" + dataIn.getAccount_out());
            holder.text_remark.setText("备注：" + remark);

            holder.message_layout.setVisibility(View.GONE);
            holder.account_layout.setVisibility(View.VISIBLE);

        } else {
            holder.text_bill.setText(dataIn.getBill());
            holder.text_account.setText("帐户："+dataIn.getAccount());
            holder.text_time.setText("时间：" + time);
            holder.text_money.setText(dataIn.getMoney()+" 元");
            holder.text_category.setText(category);
            holder.text_member.setText("成员：" + dataIn.getMember());
            holder.text_remark.setText("备注：" + remark);

            holder.message_layout.setVisibility(View.VISIBLE);
            holder.account_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View dataView;
        TextView text_bill;
        TextView text_money;
        TextView text_account_in;
        TextView text_account_out;
        TextView text_account;
        TextView text_time;
        TextView text_category;
        TextView text_member;
        TextView text_remark;
        LinearLayout account_layout;
        LinearLayout message_layout;

        public ViewHolder(View view) {
            super(view);
            dataView = view;
            text_bill = view.findViewById(R.id.text_bill);
            text_money = view.findViewById(R.id.text_money);
            text_account = view.findViewById(R.id.text_account);
            text_account_in = view.findViewById(R.id.text_account_in);
            text_account_out = view.findViewById(R.id.text_account_out);
            text_time = view.findViewById(R.id.text_time);
            text_category = view.findViewById(R.id.text_category);
            text_member = view.findViewById(R.id.text_member);
            text_remark = view.findViewById(R.id.text_remark);
            account_layout = view.findViewById(R.id.account_layout);
            message_layout = view.findViewById(R.id.message_layout);
        }
    }


}
