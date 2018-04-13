package com.example.idn.biodatasqlite;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener {

    private int position;
    private OnItemClickCallBack onItemClickCallBack;

    public CustomOnItemClickListener(int position, OnItemClickCallBack onItemClickCallBack) {
        this.position = position;
        this.onItemClickCallBack = onItemClickCallBack;
    }


    @Override
    public void onClick(View view) {
        onItemClickCallBack.OnItemClicked(view, position);
    }


    public interface OnItemClickCallBack {
        void OnItemClicked(View view, int position);
    }
}
