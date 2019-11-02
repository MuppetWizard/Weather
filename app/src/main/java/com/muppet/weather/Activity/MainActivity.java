package com.muppet.weather.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.navigation.NavigationView;
import com.muppet.weather.Adapter.NewsLIstAdapter;
import com.muppet.weather.IpAddress;
import com.muppet.weather.Model.BusCityWrap;
import com.muppet.weather.Model.NewsList;
import com.muppet.weather.Model.NormalImg;
import com.muppet.weather.Model.UserInfo;
import com.muppet.weather.Model.Weather;
import com.muppet.weather.R;
import com.muppet.weather.Utils.Constant;
import com.muppet.weather.Utils.DownLoadImageService;
import com.muppet.weather.Utils.ImageDownLoadCallBack;
import com.muppet.weather.Utils.MessageEvent;
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

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
    private String CategoryId = "4e4d610cdf714d2966000002";//壁纸类别id

    private List<Integer> FutureTemp;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<String> futureC;

    //我的页面
    private SharedPreferences sharedPreferences;
    private MenuItem menuItem;
    private String file = null;
    private String nickname = null;
    private String addr = null;
    private Integer age;

    private String ImgUrl;

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
        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone", null);
        NavigationView navigationView = findViewById(R.id.navigation);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        View headerLayout = navigationView.inflateHeaderView(R.layout.drawer_layout_left_head);
        TextView my_nickname = headerLayout.findViewById(R.id.my_nickname);
        ImageView my_icon = headerLayout.findViewById(R.id.my_icon);
        if (phone != null){
            setUserInfo(phone,my_nickname,my_icon,navigationView,drawerLayout,headerLayout);
        }else {
            gotoLogin(navigationView,my_icon,my_nickname,drawerLayout,headerLayout);
        }
        String goback = getIntent().getStringExtra("goback");
        if (goback != null) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
        ivPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        navigationViewSelecr(my_icon,my_nickname,headerLayout,navigationView,drawerLayout);
    }

    //点击事件
    private void navigationViewSelecr(ImageView my_icon, TextView my_nickname, View headerLayout, NavigationView navigationView, DrawerLayout drawerLayout) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (item.getItemId()) {
                    case R.id.nav_commoncity:
                        Intent intent = new Intent(MainActivity.this, CommonCityActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_outlogin:
                        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);
                        sharedPreferences.edit().clear().apply();
                        gotoLogin(navigationView,my_icon,my_nickname,drawerLayout,headerLayout);
                        break;
                    case R.id.nav_othercity:
                        startActivity(new Intent(MainActivity.this,ActCitySelection.class));
                        break;
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }

    //去登录
    private void gotoLogin(NavigationView navigationView, ImageView my_icon, TextView my_nickname, DrawerLayout drawerLayout, View headerLayout) {
        menuItem = navigationView.getMenu().findItem(R.id.nav_outlogin);
        menuItem.setVisible(false);
        menuItem = navigationView.getMenu().findItem(R.id.nav_commoncity);
        menuItem.setVisible(false);
        menuItem = navigationView.getMenu().findItem(R.id.nav_othercity);
        menuItem.setVisible(true);
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
    }

    //登录设置值
    private void setUserInfo(String phone, TextView my_nickname, ImageView my_icon, NavigationView navigationView, DrawerLayout drawerLayout, View headerLayout) {
        menuItem = navigationView.getMenu().findItem(R.id.nav_othercity);
        menuItem.setVisible(false);
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.GETUSERINFO));
        params.addParameter("user_name", phone);
        x.http().post(params, new Callback.CommonCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo result) {
                file = result.getFile();
                nickname = result.getName();
                age = result.getAge();
                addr = result.getAddr();
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
                Intent intent = new Intent(MainActivity.this, ModifyMyInfoActivity.class);
                intent.putExtra("age", age);
                intent.putExtra("addr", addr);
                intent.putExtra("file", file);
                intent.putExtra("nickname", nickname);
                startActivity(intent);
                drawerLayout.closeDrawer(Gravity.LEFT);
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Envet(MessageEvent messageEvent) {
        String message = messageEvent.getMessage();
        if (message.equals("update")) {
            initMyActvity();
        }
    }

    private void initView() {
        newsLIstAdapter = new NewsLIstAdapter(this);
        lvNews.setAdapter(newsLIstAdapter);
        lvNews.setOnItemClickListener(this);
        sv.smoothScrollTo(0, 0);//调整scrollview位置
        getBgImg(CategoryId);
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
    public void getBgImg(String categoryId) {
        RequestParams params = new RequestParams(IpAddress.getImgUrl(IpAddress.IMG_CATEGORY) + "/" + categoryId + "/" + "vertical");
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
                    ImgUrl = list.get(0).getImg();
                    Glide.with(MainActivity.this)
                            .load(list.get(0).getImg())
                            .error(getResources().getDrawable(R.mipmap.bgimg))
                            .into(ivBg);
                    //获取当前图片bitmap
                   /* try {
                        bitmap = Glide.with(MainActivity.this)
                                .load(list.get(0).getImg())
                                .asBitmap()
                                .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                                .get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
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
    private void saveImg()  {
        Log.e(TAG, "saveImg: "+ImgUrl );
        DownLoadImageService service = new DownLoadImageService(this, ImgUrl, new ImageDownLoadCallBack() {
            @Override
            public void onDownLoadSuccess(File file) {

            }

            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {

            }

            @Override
            public void onDownLoadFailed() {

            }
        });
        //启动图片下载线程
        new Thread(service).start();
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
                final PopupWindow window = new PopupWindow(popupView, 330, 370);
                TextView tvSaveImg = popupView.findViewById(R.id.tv_saveImg);
                TextView tvChangeImg = popupView.findViewById(R.id.tv_changeImg);
                TextView tvCategory = popupView.findViewById(R.id.tv_category);
                //点击保存图片
                tvSaveImg.setOnClickListener(v -> {
                    saveImg();
                    window.dismiss();
                });
                //点击更换图片
                tvChangeImg.setOnClickListener(v -> {
                        changeImg += 1;
                        getBgImg(CategoryId);
                        window.dismiss();

                });
                //点击选择壁纸类别
                tvCategory.setOnClickListener(v -> {
                        //回调接口
                        CategoryDialog.OnDialogItemClickListener itemClickListener = categoryId -> {
                            CategoryId = categoryId;
                            getBgImg(CategoryId);
                        };
                        if (categoryDialog == null) {
                            categoryDialog = new CategoryDialog(MainActivity.this, itemClickListener);
                        }
                        categoryDialog.show();
                        window.dismiss();


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

    //新闻
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String NewsUrl = mNewsData.get(position).getUrl();
        Intent intent = new Intent(this, ActNewsDetil.class);
        intent.putExtra("URL", NewsUrl);
        startActivity(intent);
    }
}
