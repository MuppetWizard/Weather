package com.muppet.weather.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muppet.weather.Adapter.CommonAdapter;
import com.muppet.weather.R;
import com.muppet.weather.View.ItemSwipeCallback;
import com.muppet.weather.View.SwipeToDismissWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonCityActivity extends AppCompatActivity {

    @BindView(R.id.recycler_common_city)
    RecyclerView recyclerCommonCity;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.lv_back)
    ImageView lvBack;
    private SwipeToDismissWrapper mSwipeToDismissWrapper;
    private List<String> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_city);
        ButterKnife.bind(this);
        recyclerCommonCity = findViewById(R.id.recycler_common_city);
        recyclerCommonCity.setLayoutManager(new LinearLayoutManager(this));
        final List<String> datas = new ArrayList<>();
        getData();
        CommonAdapter commonAdapter = new CommonAdapter(mDataList);
        mSwipeToDismissWrapper = new SwipeToDismissWrapper(commonAdapter, mDataList);
        mSwipeToDismissWrapper.attachToRecyclerView(recyclerCommonCity);
        recyclerCommonCity.setLayoutManager(new LinearLayoutManager(this));
        recyclerCommonCity.setAdapter(mSwipeToDismissWrapper);
        mSwipeToDismissWrapper.setItemDismissListener(new ItemSwipeCallback.ItemDismissListener() {
            @Override
            public void onItemDismiss(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CommonCityActivity.this);
                builder.setMessage("确定删除该城市吗 ?")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDataList.remove(position);
                                mSwipeToDismissWrapper.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mSwipeToDismissWrapper.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void getData() {
        char letter = 'A';
        for (int i = 0; i < 26; i++) {
            mDataList.add(String.valueOf(letter));
            letter++;
        }
    }

    @OnClick(R.id.lv_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lv_back:
                Intent intent = new Intent();
                intent.putExtra("goback", "返回");
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
