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
import com.muppet.weather.Utils.MessageEvent;
import com.muppet.weather.View.ItemSwipeCallback;
import com.muppet.weather.View.SwipeToDismissWrapper;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_city);
        ButterKnife.bind(this);
        recyclerCommonCity = findViewById(R.id.recycler_common_city);
        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);
        phone = sharedPreferences.getString("phone", null);
        mDataList = new ArrayList<>();
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.GETALLCITY));
        params.addBodyParameter("user_name", phone);
        params.setMultipart(true);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = jsonArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String city = null;
                    try {
                        city = jsonObject.getString("city");
                        Log.e("s11ss", String.valueOf(mDataList.size()));
                        Log.e("sss",city);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mDataList.add(city);
                }
                    CommonAdapter commonAdapter = new CommonAdapter(mDataList);
                    mSwipeToDismissWrapper = new SwipeToDismissWrapper(commonAdapter, mDataList);
                    mSwipeToDismissWrapper.attachToRecyclerView(recyclerCommonCity);
                    recyclerCommonCity.setLayoutManager(new LinearLayoutManager(CommonCityActivity.this));
                    recyclerCommonCity.setAdapter(mSwipeToDismissWrapper);
                    mSwipeToDismissWrapper.setItemDismissListener(new ItemSwipeCallback.ItemDismissListener() {
                        @Override
                        public void onItemDismiss(int position) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CommonCityActivity.this);
                            builder.setMessage("确定删除该城市吗 ?")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mDataList.remove(position);

                                            RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.DELETECITY));
                                            params.addParameter("user_name", phone);
                                            params.addBodyParameter("city", mDataList.get(position));
                                            x.http().post(params, new Callback.CommonCallback<String>() {
                                                @Override
                                                public void onSuccess(String result) {

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
                                            mSwipeToDismissWrapper.notifyDataSetChanged();
                                            dialog.dismiss();
                                            EventBus.getDefault().post(new MessageEvent("update"));
                                        }
                                    })
                                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            mSwipeToDismissWrapper.notifyDataSetChanged();
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }
                    });
                }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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
