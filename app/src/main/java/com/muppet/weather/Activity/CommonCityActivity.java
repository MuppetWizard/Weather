package com.muppet.weather.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muppet.weather.Adapter.CommonAdapter;
import com.muppet.weather.IpAddress;
import com.muppet.weather.R;
import com.muppet.weather.View.ItemSwipeCallback;
import com.muppet.weather.View.SwipeToDismissWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
    @BindView(R.id.tv_addCity)
    TextView tvAddCity;
    private SwipeToDismissWrapper mSwipeToDismissWrapper;
    private SharedPreferences sharedPreferences;
    private List<String> mDataList;
    private CommonAdapter commonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_city);
        ButterKnife.bind(this);
        recyclerCommonCity = findViewById(R.id.recycler_common_city);
        recyclerCommonCity.setLayoutManager(new LinearLayoutManager(this));
        getData();
        commonAdapter = new CommonAdapter(mDataList);
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
        mDataList = new ArrayList<>();
        sharedPreferences = getSharedPreferences("user_login",MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone", null);
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.GETALLCITY));
        params.addParameter("user_name", phone);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String city = jsonObject.getString("city");
                        Log.e("sss0",city);
                        mDataList.add(city);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(CommonCityActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @OnClick({R.id.lv_back, R.id.tv_addCity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lv_back:
                finish();
                break;
            case R.id.tv_addCity:
                Intent intent = new Intent(this, ActCitySelection.class);
                startActivity(intent);
                break;
        }
    }
}
