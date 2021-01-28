package com.example.bookkeepingapp.MainFunction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.bookkeepingapp.R;

public class TabItem {

    private Context context;
    //未选中时图片
    private int normalImage;
    //选中时图片
    private int selectedImage;
    //标题
    private String title;
    //对应的Fragment
    public Class<? extends Fragment> fragment;

    public View view;
    public ImageView imageView;
    public TextView textView;

    public TabItem(Context context, int normalImage, int selectedImage, String title, Class<? extends Fragment> fragment){
        this.context = context;
        this.normalImage = normalImage;
        this.selectedImage = selectedImage;
        this.title = title;
        this.fragment = fragment;
    }

    public View getView(){
        if(this.view == null){
            this.view = LayoutInflater.from(context).inflate(R.layout.view_tab_indicator, null);
            this.imageView = (ImageView) this.view.findViewById(R.id.tab_image_IV);
            this.textView = (TextView) this.view.findViewById(R.id.tab_text_TV);
            if(this.title.equals("")) {
                this.textView.setVisibility(View.GONE);
            }else {
                this.textView.setVisibility(View.VISIBLE);
                this.textView.setText(this.title);
            }
            this.imageView.setImageResource(this.normalImage);
        }
        return this.view;
    }

    //切换tab的方法
    public void setChecked(boolean isChecked) {
        if(imageView != null) {
            if(isChecked) {
                imageView.setImageResource(selectedImage);
            }else {
                imageView.setImageResource(normalImage);
            }
        }
        if(textView != null && !title.equals("")) {
            if(isChecked) {
                textView.setTextColor(context.getResources().getColor(R.color.dodgerblue));
            } else {
                textView.setTextColor(context.getResources().getColor(R.color.black));
            }
        }
    }

    public Class<? extends Fragment> getFragment() {
        return fragment;
    }

    public String getTitle() {
        return title;
    }
}