package com.muppet.weather.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.muppet.weather.IpAddress;
import com.muppet.weather.Model.NormalImg;
import com.muppet.weather.Model.Weather;
import com.muppet.weather.R;
import com.muppet.weather.Utils.Constant;
import com.muppet.weather.Utils.ToastUtil;
import com.muppet.weather.View.CategoryDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.btn_changeImg)
    Button btnChangeImg;
    @BindView(R.id.btn_category)
    Button btnCategory;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.manage)
    ImageView manage;
    @BindView(R.id.temperature)
    TextView temperature;
    @BindView(R.id.date_info)
    TextView dateInfo;
    @BindView(R.id.weather_status)
    ImageView weatherStatus;
    @BindView(R.id.wind_power)
    TextView windPower;
    @BindView(R.id.wind_direct)
    TextView windDirect;
    @BindView(R.id.wind_aqi)
    TextView windAqi;
    @BindView(R.id.wind_humidity)
    TextView windHumidity;
    @BindView(R.id.ziwaixian)
    TextView ziwaixian;
    @BindView(R.id.future_img01)
    ImageView futureImg01;
    @BindView(R.id.future_data)
    TextView futureData;
    @BindView(R.id.future_img02)
    ImageView futureImg02;
    @BindView(R.id.future_date01)
    TextView futureDate01;
    @BindView(R.id.future_img03)
    ImageView futureImg03;
    @BindView(R.id.future_date02)
    TextView futureDate02;
    @BindView(R.id.future_img04)
    ImageView futureImg04;
    @BindView(R.id.future_date03)
    TextView futureDate03;
    @BindView(R.id.future_img05)
    ImageView futureImg05;
    @BindView(R.id.future_date04)
    TextView futureDate04;
    @BindView(R.id.future_chart)
    LineChartView futureChart;

    private String TAG = "123";
    //第几张图片
    private int changeImg = Constant.NORMAL_ZERO;
    private CategoryDialog categoryDialog;
    private Date date;
    private String mCity = "成都";
    private List<Weather.ResultBean.FutureBean> mFutureData;
    private Weather.ResultBean.RealtimeBean mWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //1234
        initView();
    }

    private void initView() {
        getBgImg();
        getWeather();
    }

    /**
     * 获取天气信息
     */
    private void getWeather() {

        RequestParams params = new RequestParams(IpAddress.getWeatherUrl(IpAddress.WEATHER));
        params.addParameter("key", Constant.WEATHER_KEY);
        params.addParameter("city", mCity);
        x.http().post(params, new Callback.CommonCallback<Weather>() {
            @Override
            public void onSuccess(Weather result) {
                if (result != null) {
                    Weather.ResultBean weatherData = result.getResult();
                    if (weatherData != null) {
                        mWeather = weatherData.getRealtime();
                        mFutureData = weatherData.getFuture();
                        //设置天气数据
                        setWeatherData(mWeather, weatherData, mFutureData);
                    }
                }
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

    /**
     * 设置天气数据
     *
     * @param mWeather
     * @param weatherData
     * @param mFutureData
     */
    private void setWeatherData(Weather.ResultBean.RealtimeBean mWeather, Weather.ResultBean weatherData, List<Weather.ResultBean.FutureBean> mFutureData) {
        String Temperature = mWeather.getTemperature();
        String Humidity = mWeather.getHumidity();
        String Info = mWeather.getInfo();
        String Wid = mWeather.getWid();
        String Direct = mWeather.getDirect();
        String Power = mWeather.getPower();
        String Aqi = mWeather.getAqi();
        String City = weatherData.getCity();
        //城市
        city.setText(City);
        //温度
        temperature.setText(Temperature);
        //湿度
        windHumidity.setText(Humidity + "%");
        //获取系统时间
        date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        String taday = sdf.format(date);
        //日期+天气情况
        dateInfo.setText(taday + "/" + Info);
        //风力
        windPower.setText(Power);
        //风向
        windDirect.setText(Direct);
        //空气质量
        windAqi.setText(Aqi);
    }

    /**
     * 获取图片
     */
    private void getBgImg() {

        RequestParams params = new RequestParams(IpAddress.getImgUrl(IpAddress.IMG_NORMAL));
        //返回数量
        params.addParameter("limit", Constant.NORMAL_ONE);
        //
        params.addParameter("first", Constant.NORMAL_ONE);
        //略过数量
        params.addParameter("skip", changeImg);
        //值“hot”最热 或者“new”最新
        params.addParameter("order", "hot");
        x.http().post(params, new Callback.CommonCallback<NormalImg>() {
            @Override
            public void onSuccess(NormalImg result) {
                if (result != null) {
                    List<NormalImg.ResBean.VerticalBean> list = result.getRes().getVertical();

                    Log.d(TAG, "onSuccess: " + list.get(0).getImg());
                    Glide.with(MainActivity.this).load(list.get(0).getImg())
                            .error(getResources().getDrawable(R.mipmap.ic_launcher))
                            .into(ivBg);
                    ToastUtil.showMessage("成功");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.e(TAG, "onError: 失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 保存图片
     */
    private void saveImg() {


        //dhgdhdfh
        int a = 1;
        int b = 2;


    }

    @OnClick({R.id.btn_changeImg, R.id.btn_category})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_changeImg:
                changeImg += 1;
                getBgImg();
                break;
            case R.id.btn_category:
                if (categoryDialog == null) {
                    categoryDialog = new CategoryDialog(this);
                }
                categoryDialog.show();
                break;
        }
    }
}
