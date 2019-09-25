package com.muppet.weather.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.muppet.weather.R;
import com.muppet.weather.Utils.ToastUtil;

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

public class ActLogin extends AppCompatActivity {

    @BindView(R.id.user_login)
    EditText userLogin;
    @BindView(R.id.pswd_login)
    EditText pswdLogin;
    @BindView(R.id.msg_button_login)
    Button msgButtonLogin;
    @BindView(R.id.forget_button_login)
    Button forgetButtonLogin;
    @BindView(R.id.register_button_login)
    Button registerButtonLogin;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.userread_login)
    TextView userreadLogin;
    @BindView(R.id.wechat_login)
    ImageView wechatLogin;
    @BindView(R.id.qq_login)
    ImageView qqLogin;
    private EventHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
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
                ds.setColor(ContextCompat.getColor(ActLogin.this,R.color.colorPrimary)); //设置颜色
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
                ds.setColor(ContextCompat.getColor(ActLogin.this,R.color.colorPrimary)); //设置颜色
                ds.setUnderlineText(false);//去下划线
            }
        };
        spannableString.setSpan(clickableSpan2,12,18,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this,R.color.colorPrimary));
        spannableString.setSpan(colorSpan,5,11,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(ContextCompat.getColor(this,R.color.colorPrimary));
        spannableString.setSpan(colorSpan2,12,18,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        userreadLogin.setText(spannableString);
        userreadLogin.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick({R.id.msg_button_login, R.id.forget_button_login, R.id.register_button_login, R.id.login, R.id.wechat_login, R.id.qq_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.msg_button_login:
                msgLogin(this);
                break;
            case R.id.forget_button_login:
                forgetLogin();
                break;
            case R.id.register_button_login:
                break;
            case R.id.login:
                break;
            case R.id.wechat_login:
                break;
            case R.id.qq_login:
                break;
        }
    }

    private void forgetLogin() {

    }

    private void msgLogin(Context context) {
        RegisterPage page = new RegisterPage();
        page.setTempCode(null); //没有申请短信模板编号
        page.setRegisterCallback(new EventHandler(){
            public void afterEvent(int event, int result, Object data){
                if (result == SMSSDK.RESULT_COMPLETE){
                    //处理成功结果
//                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    Intent intent = new Intent(ActLogin.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    ToastUtil.showMessage("请使用正确手机号");
                }
            }
        });
        page.show(context);
    }
}
