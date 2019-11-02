package com.muppet.weather.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.muppet.weather.IpAddress;
import com.muppet.weather.R;
import com.muppet.weather.Utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Forgetpswd extends AppCompatActivity {
    @BindView(R.id.userid)
    EditText userid;
    @BindView(R.id.pswd_new)
    EditText pswdNew;
    @BindView(R.id.resetpswd)
    Button resetpswd;
    @BindView(R.id.lv_back)
    ImageView lvBack;
    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_forgetpswd);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("phone")) {
            phone = intent.getStringExtra("phone");
            userid.setText(phone);
        }
    }

    @OnClick({R.id.lv_back, R.id.resetpswd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lv_back:
                finish();
                break;
            case R.id.resetpswd:
                String newpswd = pswdNew.getText().toString();
                RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.FORGETPSWD));
                params.addParameter("password", newpswd);
                params.addParameter("user_name", phone);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (result.toString().equals("更改成功")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Forgetpswd.this);
                            builder.setTitle("成功");
                            //内容居中
                            TextView myMessage = new TextView(Forgetpswd.this);
                            myMessage.setText("更改成功");
                            myMessage.setTextSize(20);
                            myMessage.setGravity(Gravity.CENTER);
                            builder.setView(myMessage);

                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Forgetpswd.this, ActLogin.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        } else {
                            ToastUtil.showMessage("更改失败");
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
                break;
        }
    }
}
