package com.muppet.weather.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.muppet.weather.IpAddress;
import com.muppet.weather.Model.BusCityWrap;
import com.muppet.weather.Model.CityEntity;
import com.muppet.weather.R;
import com.muppet.weather.Utils.JsonReadUtil;
import com.muppet.weather.Utils.LocationUtils;
import com.muppet.weather.Utils.PermissionUtils;
import com.muppet.weather.Utils.ToastUtil;
import com.muppet.weather.View.LetterListView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActCitySelection extends AppCompatActivity implements AbsListView.OnScrollListener,PermissionUtils.PermissionCallbacks{

    @BindView(R.id.search_locate_content_et)
    EditText searchLocateContentEt;
    @BindView(R.id.tool_bar_fl)
    FrameLayout toolBarFl;
    @BindView(R.id.total_city_lv)
    ListView totalCityLv;
    @BindView(R.id.total_city_letter)
    LetterListView totalCityLetter;
    @BindView(R.id.search_city_lv)
    ListView searchCityLv;
    @BindView(R.id.no_search_result_tv)
    TextView noSearchResultTv;

    //文件名称
    private final static String CityFileName = "allcity.json";

    private Handler handler;
    private TextView overlay; // 对话框首字母TextView
    private OverlayThread overlayThread; // 显示首字母对话框
    private boolean mReady = false;
    private boolean isScroll = false;

    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置

    protected List<CityEntity> hotCityList = new ArrayList<>();
    protected List<CityEntity> totalCityList = new ArrayList<>();
    protected List<CityEntity> curCityList = new ArrayList<>();
    protected List<CityEntity> searchCityList = new ArrayList<>();
    protected CityListAdapter cityListAdapter;
    protected SearchCityListAdapter searchCityListAdapter;

    private LocationManager mLocationManager;

    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private static final int REQUEST_PERMISSION_CODE = 12;

    private String locationCity, curSelCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_city_selection);
        ButterKnife.bind(this);
        // 默认软键盘不弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initView();
        initData();
        initListener();
        /////////
    }

    private void initView() {

        handler = new Handler();
        overlayThread = new OverlayThread();
        searchCityListAdapter = new SearchCityListAdapter(this, searchCityList);
        searchCityLv.setAdapter(searchCityListAdapter);
     //   locationCity = "成都";
       // curSelCity = locationCity;

    }

    private void initData() {
        initTotalCityList();
        cityListAdapter = new CityListAdapter(this, totalCityList, hotCityList);
        totalCityLv.setAdapter(cityListAdapter);
        totalCityLv.setOnScrollListener(this);
        totalCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 1) {
                    CityEntity cityEntity = totalCityList.get(position);
                    showSetCityDialog(cityEntity.getName(), cityEntity.getCityCode());
                }

            }
        });
        totalCityLetter.setOnTouchingLetterChangedListener(new LetterListViewListener());
        initOverlay();
    }

    private void initListener() {

        searchCityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityEntity cityEntity = searchCityList.get(position);
                showSetCityDialog(cityEntity.getName(),cityEntity.getCityCode());
            }
        });
        searchLocateContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = searchLocateContentEt.getText().toString().trim().toLowerCase();
                setSearchCityList(content);
            }
        });

        searchLocateContentEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftInput(searchLocateContentEt.getWindowToken());
                    String content = searchLocateContentEt.getText().toString().trim().toLowerCase();
                    setSearchCityList(content);
                    return true;
                }
                return false;
            }
        });
    }

    //定位
    private void getLocation() {
        LocationUtils.register(this, 0, 0, new LocationUtils.OnLocationChangeListener() {
            @Override
            public void getLastKnownLocation(Location location) {
                Log.e("123", "onLocationChanged: " + location.getLatitude());
            }

            @Override
            public void onLocationChanged(Location location) {
                //位置信息变化时触发
                Log.e("123", "定位方式：" + location.getProvider());
                Log.e("123", "纬度：" + location.getLatitude());
                Log.e("123", "经度：" + location.getLongitude());
                Log.e("123", "海拔：" + location.getAltitude());
                Log.e("123", "时间：" + location.getTime());
                Log.e("123", "国家：" + LocationUtils.getCountryName(ActCitySelection.this, location.getLatitude(), location.getLongitude()));
                Log.e("123", "获取地理位置：" + LocationUtils.getAddress(ActCitySelection.this, location.getLatitude(), location.getLongitude()));
                Log.e("123", "所在地：" + LocationUtils.getLocality(ActCitySelection.this, location.getLatitude(), location.getLongitude()));
                Log.e("123", "所在街道：" + LocationUtils.getStreet(ActCitySelection.this, location.getLatitude(), location.getLongitude()));


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationUtils.unregister();
    }

    /**
     * 设置搜索数据展示
     */
    private void setSearchCityList(String content) {
        searchCityList.clear();
        if (TextUtils.isEmpty(content)) {
            totalCityLv.setVisibility(View.VISIBLE);
            totalCityLetter.setVisibility(View.VISIBLE);
            searchCityLv.setVisibility(View.GONE);
            noSearchResultTv.setVisibility(View.GONE);
        } else {
            totalCityLv.setVisibility(View.GONE);
            totalCityLetter.setVisibility(View.GONE);
            for (int i = 0; i < curCityList.size(); i++) {
                CityEntity cityEntity = curCityList.get(i);
                if (cityEntity.getName().contains(content) || cityEntity.getPinyin().contains(content)
                        || cityEntity.getFirst().contains(content)) {
                    searchCityList.add(cityEntity);
                }
            }

            if (searchCityList.size() != 0) {
                noSearchResultTv.setVisibility(View.GONE);
                searchCityLv.setVisibility(View.VISIBLE);
            } else {
                noSearchResultTv.setVisibility(View.VISIBLE);
                searchCityLv.setVisibility(View.GONE);
            }

            searchCityListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 初始化全部城市列表
     */
    public void initTotalCityList() {
        hotCityList.clear();
        totalCityList.clear();
        curCityList.clear();

        String cityListJson = JsonReadUtil.getJsonStr(this, CityFileName);
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(cityListJson);
            JSONArray array = jsonObject.getJSONArray("City");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                String key = object.getString("key");
                String pinyin = object.getString("full");
                String first = object.getString("first");
                String cityCode = object.getString("code");

                CityEntity cityEntity = new CityEntity();
                cityEntity.setName(name);
                cityEntity.setKey(key);
                cityEntity.setPinyin(pinyin);
                cityEntity.setFirst(first);
                cityEntity.setCityCode(cityCode);

                if (key.equals("热门")) {
                    hotCityList.add(cityEntity);
                } else {
                    if (!cityEntity.getKey().equals("0") && !cityEntity.getKey().equals("1")) {
                        curCityList.add(cityEntity);
                    }
                    totalCityList.add(cityEntity);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL
                || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        } else {
            isScroll = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }

        if (mReady) {
            String key = getAlpha(totalCityList.get(firstVisibleItem).getKey());
            overlay.setText(key);
            overlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟让overlay为不可见
            handler.postDelayed(overlayThread, 700);
        }
    }

    @Override
    public void onPermissionsAllGranted(int requestCode, List<String> perms, boolean isAllGranted) {
        if (isAllGranted) {
            getLocation();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (PermissionUtils.somePermissionPermanentlyDenied(this, perms)) {
            PermissionUtils.showDialogGoToAppSettting(this);
        } else {
            PermissionUtils.showPermissionReason(requestCode, this, permissions, "需要定位权限");
        }

    }

    private class LetterListViewListener implements
            LetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            isScroll = false;
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                totalCityLv.setSelection(position);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟让overlay为不可见
                handler.postDelayed(overlayThread, 700);
            }
        }
    }


    /**
     * 设置overlay不可见
     */
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    /**
     * 总城市适配器
     */
    class CityListAdapter extends BaseAdapter {
        private Context context;

        private List<CityEntity> totalCityList;
        private List<CityEntity> hotCityList;
        private LayoutInflater inflater;
        final int VIEW_TYPE = 3;

        private CityListAdapter(Context context,
                        List<CityEntity> totalCityList,
                        List<CityEntity> hotCityList) {
            this.context = context;
            this.totalCityList = totalCityList;
            this.hotCityList = hotCityList;
            inflater = LayoutInflater.from(context);

            alphaIndexer = new HashMap<>();

            for (int i = 0; i < totalCityList.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = totalCityList.get(i).getKey();

                String previewStr = (i - 1) >= 0 ? totalCityList.get(i - 1).getKey() : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(currentStr);
                    alphaIndexer.put(name, i);
                }
            }
        }

        @Override
        public int getViewTypeCount() {
            return VIEW_TYPE;
        }

        @Override
        public int getItemViewType(int position) {
            return position < 2 ? position : 2;
        }

        @Override
        public int getCount() {
            return totalCityList == null ? 0 : totalCityList.size();
        }

        @Override
        public Object getItem(int position) {
            return totalCityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView curCityNameTv;
            ViewHolder holder;
            int viewType = getItemViewType(position);
            if (viewType == 0) { // 定位
                convertView = inflater.inflate(R.layout.select_city_location_item, null);

                LinearLayout noLocationLl = convertView.findViewById(R.id.cur_city_no_data_ll);
                TextView getLocationTv = convertView.findViewById(R.id.cur_city_re_get_location_tv);
                curCityNameTv = convertView.findViewById(R.id.cur_city_name_tv);

                if (TextUtils.isEmpty(locationCity)) {
                    noLocationLl.setVisibility(View.VISIBLE);
                    curCityNameTv.setVisibility(View.GONE);
                    getLocationTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //获取定位
                            if (!PermissionUtils.hasPermissions(ActCitySelection.this, permissions)) {
                                PermissionUtils.requestPermissions(ActCitySelection.this, REQUEST_PERMISSION_CODE, permissions);
                            } else {
                                getLocation();
                            }
                        }
                    });
                } else {
                    noLocationLl.setVisibility(View.GONE);
                    curCityNameTv.setVisibility(View.VISIBLE);

                    curCityNameTv.setText(locationCity);
                    curCityNameTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!locationCity.equals(curSelCity)) {
                                //设置城市代码
                                String cityCode = "";
                                for (CityEntity cityEntity : curCityList) {
                                    if (cityEntity.getName().equals(locationCity)) {
                                        cityCode = cityEntity.getCityCode();
                                        break;
                                    }
                                }
                                showSetCityDialog(locationCity, cityCode);
                            } else {
                                ToastUtil.showMessage("当前定位城市" + curCityNameTv.getText().toString());
                            }
                        }
                    });
                }
            } else if (viewType == 1) { //热门城市
                convertView = inflater.inflate(R.layout.recent_city_item, null);
                GridView hotCityGv = convertView.findViewById(R.id.recent_city_gv);
                hotCityGv.setAdapter(new HotCityListAdapter(context, this.hotCityList));
                hotCityGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        CityEntity cityEntity = hotCityList.get(position);
                        showSetCityDialog(cityEntity.getName(), cityEntity.getCityCode());
                    }
                });
            } else {
                if (null == convertView) {
                    convertView = inflater.inflate(R.layout.city_list_item_layout, null);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                CityEntity cityEntity = totalCityList.get(position);
                holder.cityKeyTv.setVisibility(View.VISIBLE);
                holder.cityKeyTv.setText(getAlpha(cityEntity.getKey()));
                holder.cityNameTv.setText(cityEntity.getName());

                if (position >= 1) {
                    CityEntity preCity = totalCityList.get(position - 1);
                    if (preCity.getKey().equals(cityEntity.getKey())) {
                        holder.cityKeyTv.setVisibility(View.GONE);
                    } else {
                        holder.cityKeyTv.setVisibility(View.VISIBLE);
                    }
                }
            }

            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.city_key_tv)
            TextView cityKeyTv;
            @BindView(R.id.city_name_tv)
            TextView cityNameTv;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * 热门城市适配器
     */
    class HotCityListAdapter extends BaseAdapter {

        private List<CityEntity> cityEntities;
        private LayoutInflater inflater;

        HotCityListAdapter(Context mContext, List<CityEntity> cityEntities) {
            this.cityEntities = cityEntities;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return cityEntities == null ? 0 : cityEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return cityEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.city_list_grid_item_layout, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            CityEntity cityEntity = cityEntities.get(position);
            holder.cityListGridItemNameTv.setText(cityEntity.getName());

            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.city_list_grid_item_name_tv)
            TextView cityListGridItemNameTv;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    /**
     * 搜索城市列表适配器
     */
    class SearchCityListAdapter extends BaseAdapter {

        private List<CityEntity> cityEntities;
        private LayoutInflater inflater;

        SearchCityListAdapter(Context mContext, List<CityEntity> cityEntities) {
            this.cityEntities = cityEntities;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return cityEntities == null ? 0 : cityEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return cityEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.city_list_item_layout, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CityEntity cityEntity = cityEntities.get(position);
            holder.cityKeyTv.setVisibility(View.GONE);
            holder.cityNameTv.setText(cityEntity.getName());

            return convertView;
        }



     class ViewHolder {
            @BindView(R.id.city_key_tv)
            TextView cityKeyTv;
            @BindView(R.id.city_name_tv)
            TextView cityNameTv;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * 展示设置城市对话框
     */
    private void showSetCityDialog(final String curCity, final String cityCode) {
        if (curCity.equals(curSelCity)) {
            ToastUtil.showMessage("当前定位城市" + curCity);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否设置 " + curCity + " 为您的当前城市？"); //设置内容

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.ADDCITY));
                SharedPreferences sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);
                String phone = sharedPreferences.getString("phone", null);
                params.addParameter("user_name", phone);
                params.addBodyParameter("city", curCity);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

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
                BusCityWrap cityWrap = new BusCityWrap(curCity);
                EventBus.getDefault().post(cityWrap);
                startActivity(new Intent(ActCitySelection.this,MainActivity.class));
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 初始化汉语拼音首字母弹出提示框
     */
    private void initOverlay() {
        mReady = true;
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    /**
     * 获得首字母
     */
    private String getAlpha(String key) {
        if (key.equals("0")) {
            return "定位";
        } else if (key.equals("1")) {
            return "热门";
        } else {
            return key;
        }
    }

}
