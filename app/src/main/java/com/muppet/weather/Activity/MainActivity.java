package com.muppet.weather.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.mob.wrappers.UMSSDKWrapper;
import com.muppet.weather.Adapter.NewsLIstAdapter;
import com.muppet.weather.IpAddress;
import com.muppet.weather.Model.BusCityWrap;
import com.muppet.weather.Model.NewsList;
import com.muppet.weather.Model.NormalImg;
import com.muppet.weather.Model.UserInfo;
import com.muppet.weather.Model.Weather;
import com.muppet.weather.R;
import com.muppet.weather.Utils.Constant;
import com.muppet.weather.Utils.ToastUtil;
import com.muppet.weather.Utils.Utils;
import com.muppet.weather.View.CategoryDialog;
import com.muppet.weather.View.ListViewForScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
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
    @BindView(R.id.tv_footView)
    TextView tvFootView;
    @BindView(R.id.lv_news)
    ListViewForScrollView lvNews;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.iv_personal)
    ImageView ivPersonal;

    private String TAG = "123";
    //第几张图片
    private int changeImg = Constant.NORMAL_ZERO;
    private CategoryDialog categoryDialog;
    private Date date;
    private String mCity = "成都";//默认
    private List<Weather.ResultBean.FutureBean> mFutureData;
    private List<NewsList.ResultBean.DataBean> mNewsData;
    private Weather.ResultBean.RealtimeBean mWeather;
    private NewsLIstAdapter newsLIstAdapter;

    private List<Integer> FutureTemp;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<String> futureC;

    //我的页面
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SharedPreferences sharedPreferences;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initMyActvity();
        //初始化
        initView();
    }

    private void initMyActvity() {
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawer_layout);
        String goback = getIntent().getStringExtra("goback");
        if (goback != null) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
        View headerLayout = navigationView.inflateHeaderView(R.layout.drawer_layout_left_head);
        TextView my_nickname = headerLayout.findViewById(R.id.my_nickname);
        ImageView my_icon = headerLayout.findViewById(R.id.my_icon);

        //判断登录没
        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone", null);
        if (phone == null) {
            //隐藏视图
            menuItem = navigationView.getMenu().findItem(R.id.nav_outlogin);
            menuItem.setVisible(false);
            menuItem = navigationView.getMenu().findItem(R.id.nav_commoncity);
            menuItem.setVisible(false);
            my_nickname.setText("未登录");
            my_icon.setImageResource(R.mipmap.not_logged);
            headerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, ActLogin.class));
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    finish();
                }
            });
        } else {
            //连接后台加载用户数据
            RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.GETUSERINFO));
            params.addParameter("user_name", phone);
            x.http().post(params, new Callback.CommonCallback<UserInfo>() {
                @Override
                public void onSuccess(UserInfo result) {
                    my_nickname.setText(result.getName());
                    Glide.with(MainActivity.this).load(result.getFile()).into(my_icon);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(MainActivity.this, "网络请求错误", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
            Toast.makeText(this, phone, Toast.LENGTH_SHORT).show();
            headerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, ModifyMyInfoActivity.class));
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    finish();
                }
            });
        }
        ivPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (item.getItemId()) {
                    case R.id.nav_commoncity:
                        Intent intent = new Intent(MainActivity.this, CommonCityActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_outlogin:
                        //删除数据
                        Toast.makeText(MainActivity.this, "删除SharedPreferences中的数据", Toast.LENGTH_SHORT).show();
                        break;
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }

    private void initView() {
        newsLIstAdapter = new NewsLIstAdapter(this);
        lvNews.setAdapter(newsLIstAdapter);
        sv.smoothScrollTo(0, 0);//调整scrollview位置
        getBgImg();
        getWeather();
        getNewsList();
    }

    private void getNewsList() {

        mNewsData = new ArrayList<>();
        RequestParams params = new RequestParams(IpAddress.getNewsUrl(IpAddress.NEWS));
        params.addParameter("key", Constant.NEWS_KEY);
        params.addParameter("type", "hot");
        x.http().post(params, new Callback.CommonCallback<NewsList>() {
            @Override
            public void onSuccess(NewsList result) {
                if (result != null) {
                    NewsList.ResultBean resultBean = result.getResult();
                    if (resultBean != null) {
                        mNewsData = resultBean.getData();
                        newsLIstAdapter.setDate(mNewsData);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.showMessage("新闻请求失败或网络出现问题");
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
     * 获取天气信息
     */
    private void getWeather() {
        mFutureData = new ArrayList<>();
        futureC = new ArrayList<>();
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
                        for (int i = 0; i < weatherData.getFuture().size(); i++) {
                            futureC.add(weatherData.getFuture().get(i).getTemperature());
                        }
                        setChart(futureC);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.showMessage("天气请求失败或网络出现问题");
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
        //天气状况图片
        switch (Wid) {
            case "00":
                weatherStatus.setImageResource(R.mipmap.sunshine);
                break;
            case "01":
                weatherStatus.setImageResource(R.mipmap.cloudy);
                break;
            case "02":
                weatherStatus.setImageResource(R.mipmap.cloudy02);
                break;
            case "03":
            case "04":
            case "05":
            case "06":
            case "07":
            case "08":
            case "09":
            case "10":
            case "11":
            case "12":
            case "13":
            case "14":
            case "15":
            case "16":
            case "17":
            case "18":
            case "19":
                weatherStatus.setImageResource(R.mipmap.rain);
                break;
        }
        for (int i = 0; i < mFutureData.size(); i++) {
            String day = mFutureData.get(i).getWid().getDay();
            //未来天气数据设置
            setFutureData(day, i);
        }
    }

    /**
     * 设置未来天气
     *
     * @param day
     * @param i
     */
    private void setFutureData(String day, int i) {
        ImageView imageView = null;
        switch (i) {
            case 0:
                imageView = futureImg01;
                break;
            case 1:
                imageView = futureImg02;
                break;
            case 2:
                imageView = futureImg03;
                break;
            case 3:
                imageView = futureImg04;
                break;
            case 4:
                imageView = futureImg05;
                break;
        }
        if (imageView != null) {
            setFutureImg(imageView, day);
        } else {
            ToastUtil.showMessage("出现错误");
        }
        //未来时间
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;//月
        int time = calendar.get(Calendar.DAY_OF_MONTH);//日
        futureDate02.setText(month + "/" + (time + 2));
        futureDate03.setText(month + "/" + (time + 3));
        futureDate04.setText(month + "/" + (time + 4));
    }

    /**
     * 设置未来天气图片
     *
     * @param imageView
     * @param day
     */
    private void setFutureImg(ImageView imageView, String day) {
        switch (day) {
            case "00":
                imageView.setImageResource(R.mipmap.sunshine);
                break;
            case "01":
                imageView.setImageResource(R.mipmap.cloudy);
                break;
            case "02":
                imageView.setImageResource(R.mipmap.cloudy02);
                break;
            case "03":
            case "04":
            case "05":
            case "06":
            case "07":
            case "08":
            case "09":
                imageView.setImageResource(R.mipmap.rain);
                break;
        }
    }

    /**
     * 设置图表数据
     *
     * @param futureC
     */
    public void setChart(List<String> futureC) {
        FutureTemp = new ArrayList<>();
        for (int i = 0; i < futureC.size(); i++) {
            String oC = futureC.get(i);
            //截取字符串
            String strStart = "/";
            String strEnd = "℃";
            FutureTemp.add(Integer.parseInt(Objects.requireNonNull(Utils.subString(oC, strStart, strEnd))));
        }
        getAxisPoints();//获取坐标点
        initLineChart();//初始化
    }

    /**
     * 图表点显示
     */
    private void getAxisPoints() {
        mPointValues = new ArrayList<PointValue>();
        for (int i = 0; i < FutureTemp.size(); i++) {
            mPointValues.add(new PointValue(i, FutureTemp.get(i)));
        }
    }

    /**
     * 初始化图表
     */
    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFFFFF"));//折线颜色
        List<Line> lines = new ArrayList<>();
        //折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setValueLabelsTextColor(Color.parseColor("#FFFFFF"));
        data.setValueLabelBackgroundEnabled(false);
        data.setLines(lines);
        futureChart.setInteractive(false);
        futureChart.setZoomType(ZoomType.HORIZONTAL);
        futureChart.setMaxZoom((float) 2);//最大方法比例
        futureChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        futureChart.setLineChartData(data);
        futureChart.setVisibility(View.VISIBLE);
        Viewport v = new Viewport(futureChart.getMaximumViewport());
        v.left = 0;
        v.right = 4;
        futureChart.setCurrentViewport(v);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnReceivMsg(BusCityWrap city) {
        this.mCity = city.getCity();
        getWeather();
    }
//^^^^^^^^^^^^^^^^^^^^^^^^^^^天气数据显示^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

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

                    Glide.with(MainActivity.this).load(list.get(0).getImg())
                            .error(getResources().getDrawable(R.mipmap.bgimg))
                            .into(ivBg);
                    Log.e(TAG, "onSuccess: " + list.get(0).getImg());
                    ToastUtil.showMessage("成功");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.showMessage("壁纸请求失败或网络出现问题");
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

    @OnClick({R.id.tv_footView, R.id.manage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_footView:
                if (newsLIstAdapter.getCount() == 3) {
                    newsLIstAdapter.addItemNum(mNewsData.size());
                    tvFootView.setText("收起");
                    newsLIstAdapter.notifyDataSetChanged();
                } else {
                    newsLIstAdapter.addItemNum(3);
                    tvFootView.setText("查看更多");
                    newsLIstAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.manage:
                View popupView = this.getLayoutInflater().inflate(R.layout.popupwindow_option, null);
                final PopupWindow window = new PopupWindow(popupView, 450, 400);
                TextView tvSaveImg = popupView.findViewById(R.id.tv_saveImg);
                TextView tvChangeImg = popupView.findViewById(R.id.tv_changeImg);
                TextView tvCategory = popupView.findViewById(R.id.tv_category);
                tvSaveImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                        window.dismiss();
                    }
                });
                tvChangeImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeImg += 1;
                        getBgImg();
                        window.dismiss();
                    }
                });
                tvCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (categoryDialog == null) {
                            categoryDialog = new CategoryDialog(MainActivity.this);
                        }
                        categoryDialog.show();
                        window.dismiss();
                    }
                });
                window.setFocusable(true);
                window.update();
                window.showAsDropDown(manage, 0, 0);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
