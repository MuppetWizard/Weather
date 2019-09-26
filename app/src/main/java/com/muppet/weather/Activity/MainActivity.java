package com.muppet.weather.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.muppet.weather.IpAddress;
import com.muppet.weather.Model.NormalImg;
import com.muppet.weather.R;
import com.muppet.weather.Utils.Constant;
import com.muppet.weather.Utils.ToastUtil;
import com.muppet.weather.View.CategoryDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.btn_changeImg)
    Button btnChangeImg;
    @BindView(R.id.btn_category)
    Button btnCategory;

    private String TAG = "123";
    //第几张图片
    private int changeImg = Constant.NORMAL_ZERO;
    private CategoryDialog categoryDialog;

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
