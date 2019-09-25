package com.muppet.weather.Activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.muppet.weather.IpAddress;
import com.muppet.weather.R;
import com.muppet.weather.Utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import static android.widget.Toast.LENGTH_SHORT;

public class ActRegister extends AppCompatActivity {
    @BindView(R.id.phone_register)
    EditText phoneRegister;
    @BindView(R.id.pswd_register)
    EditText pswdRegister;
    @BindView(R.id.showpswd_register)
    Button showpswdRegister;
//    @BindView(R.id.code_register)
//    EditText codeRegister;
    @BindView(R.id.codebt_register)
    Button codebtRegister;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.userread_register)
    TextView userreadRegister;
    private boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);
        ButterKnife.bind(this);
        SpannableString spannableString = new SpannableString("登录即同意《用户协议》和《隐私政策》");
        ClickableSpan clickableSpan  = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.moji.com/about/agreement/");
                intent.setData(content_url);
                startActivity(intent);
            }
            public void updateDrawState(TextPaint ds){
                ds.setColor(ContextCompat.getColor(ActRegister.this,R.color.colorPrimary)); //设置颜色
                ds.setUnderlineText(false);//去下划线
            }
        };
        spannableString.setSpan(clickableSpan,5,11, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent= new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.moji.com/about/agreement/");
                intent.setData(content_url);
                startActivity(intent);
            }
            public void  updateDrawState(TextPaint ds){
                ds.setColor(ContextCompat.getColor(ActRegister.this,R.color.colorPrimary)); //设置颜色
                ds.setUnderlineText(false);//去下划线
            }
        };
        spannableString.setSpan(clickableSpan2,12,18,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this,R.color.colorPrimary));
        spannableString.setSpan(colorSpan,5,11,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(ContextCompat.getColor(this,R.color.colorPrimary));
        spannableString.setSpan(colorSpan2,12,18,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        userreadRegister.setText(spannableString);
        userreadRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick({R.id.showpswd_register, R.id.codebt_register, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.showpswd_register:
                showPswd();
                break;
            case R.id.codebt_register:
                sendCode(this);
                break;
            case R.id.register:
                registerMe();
                break;
        }
    }
    private void registerMe() {

        if (flag){
            if (phoneRegister.getText().length()==11) {
                if (pswdRegister.getText().length() > 6) {
                    RequestParams requestParams = new RequestParams(IpAddress.getUrl(IpAddress.REGISTER));
                    requestParams.addParameter("user_name", phoneRegister.getText().toString());
                    requestParams.addParameter("password", pswdRegister.getText().toString());
                    requestParams.addParameter("password2", pswdRegister.getText().toString());
                    requestParams.addParameter("addr", "哈麻批");
                    requestParams.addParameter("name", "狗仗贱");
                    requestParams.addParameter("age", "11");
                    x.http().get(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            if (result.toString().equals("注册成功")){
                                ToastUtil.showMessage("注册成功");
                                Intent intent =  new Intent(ActRegister.this,ActLogin.class);
                                startActivity(intent);
                                finish();
                            }
                            else if (result.toString().equals("注册失败,账号存在")){
                                ToastUtil.showMessage("手机号已注册");
                            }
                            else {
                                ToastUtil.showMessage("注册失败");
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
                } else {
                    ToastUtil.showMessage("请输入6位以上的密码");
                }
            }
            else ToastUtil.showMessage("请使用正确的手机号");
        }
        else {
            Toast.makeText(this,"请先认证手机号",Toast.LENGTH_LONG).show();
        }
    }

    private void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        page.setTempCode(null); //没有申请短信模板编号
        page.setRegisterCallback(new EventHandler(){
            public void afterEvent(int event, int result, Object data){
                if (result == SMSSDK.RESULT_COMPLETE){
                    //处理成功结果
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    //国家代码
                    String country = (String) phoneMap.get("country");
                    //手机号码
                    String phone = (String) phoneMap.get("phone");
                   // Log.i("111111", phone +country);
                  //  SMSSDK.getVerificationCode(country,phone);
                    flag = true;
                    phoneRegister.setText(phone);
                }
                else {
                    ToastUtil.showMessage("请使用正确手机号");
                }
            }
        });
        page.show(context);
    }

    private void showPswd() {
              pswdRegister.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
              pswdRegister.setSelection(pswdRegister.getText().length());//后移焦点
//                pswdRegister.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
    }
}
