package com.muppet.weather.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.muppet.weather.R;


public class CategoryDialog extends Dialog {

    public CategoryDialog(@NonNull Context context) {
        super(context, R.style.CategoryDialog);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_category);
        setCanceledOnTouchOutside(false);//点击窗口外是否关闭dialog
//9.25了，写dialog布局
    }
}
