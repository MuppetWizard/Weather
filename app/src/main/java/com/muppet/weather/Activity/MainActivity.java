package com.muppet.weather.Activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.muppet.weather.IpAddress;
import com.muppet.weather.Model.NormalImg;
import com.muppet.weather.R;
import com.muppet.weather.Utils.Constant;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_bg)
    ImageView ivBg;

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

    private void getBgImg() {

        RequestParams params = new RequestParams(IpAddress.getImgUrl(IpAddress.IMG_NORMAL));
        //返回数量
        params.addBodyParameter("limit", Constant.NORMAL_ONE);
        //
        params.addBodyParameter("first", Constant.NORMAL_ONE);
        //略过数量
        params.addBodyParameter("skip",Constant.NORMAL_ZERO);
        //值“hot”最热 或者“new”最新
        params.addBodyParameter("order","hot");
        x.http().post(params, new Callback.CommonCallback<NormalImg>() {
            @Override
            public void onSuccess(NormalImg result) {
                if (result != null) {
                    List<NormalImg.ResBean.VerticalBean> list =  result.getRes().getVertical();

                    Glide.with(MainActivity.this).load(list.get(0).getImg())
                            .error(getResources().getDrawable(R.mipmap.ic_launcher))
                            .into(ivBg);
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
}
