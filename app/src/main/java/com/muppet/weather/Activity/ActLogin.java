package com.muppet.weather.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class ActLogin extends AppCompatActivity implements PlatformActionListener {

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
    //    @BindView(R.id.wechat_login)
//    ImageView wechatLogin;
    @BindView(R.id.qq_login)
    ImageView qqLogin;
    @BindView(R.id.rememberPswd)
    CheckBox rememberPswd;
    private EventHandler handler;
    private PlatformDb platDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
        SpannableString spannableString = new SpannableString("登录即同意《用户协议》和《隐私政策》");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.moji.com/about/agreement/");
                intent.setData(content_url);
                startActivity(intent);
            }

            public void updateDrawState(TextPaint ds) {
                ds.setColor(ContextCompat.getColor(ActLogin.this, R.color.colorPrimary)); //设置颜色
                ds.setUnderlineText(false);//去下划线
            }
        };
        spannableString.setSpan(clickableSpan, 5, 11, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.moji.com/about/agreement/");
                intent.setData(content_url);
                startActivity(intent);
            }

            public void updateDrawState(TextPaint ds) {
                ds.setColor(ContextCompat.getColor(ActLogin.this, R.color.colorPrimary)); //设置颜色
                ds.setUnderlineText(false);//去下划线
            }
        };
        spannableString.setSpan(clickableSpan2, 12, 18, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        spannableString.setSpan(colorSpan, 5, 11, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        spannableString.setSpan(colorSpan2, 12, 18, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        userreadLogin.setText(spannableString);
        userreadLogin.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick({R.id.msg_button_login, R.id.forget_button_login, R.id.register_button_login, R.id.login, R.id.qq_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.msg_button_login:
                msgLogin(this);
                break;
            case R.id.forget_button_login:
                forgetLogin(this);
                break;
            case R.id.register_button_login:
                Intent intent = new Intent(ActLogin.this, ActRegister.class);
                startActivity(intent);
                break;
            case R.id.login:
                Login();
                break;
//            case R.id.wechat_login:
//                weChatLogin();
//                break;
            case R.id.qq_login:
                QQLogin();
                break;
        }
    }

    private void QQLogin() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        authorize(qq);
    }

//    private void weChatLogin() {
//        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
//        authorize(wechat);
//    }

    private void Login() {
        String username = userLogin.getText().toString();
        String password = pswdLogin.getText().toString();
        RequestParams params = new RequestParams(IpAddress.getUrl(IpAddress.LOGIN));
        params.addParameter("user_name", username);
        params.addParameter("password", password);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("登录成功")) {
                    SharedPreferences.Editor editor = getSharedPreferences("user_login",MODE_PRIVATE).edit();
                    editor.putString("phone",username);
                    editor.commit();
                    ToastUtil.showMessage("登录成功");
                } else {
                    ToastUtil.showMessage("登录失败");
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

    private void forgetLogin(Context context) {
        RegisterPage page = new RegisterPage();
        page.setTempCode(null); //没有申请短信模板编号
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //处理成功结果
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String phone = (String) phoneMap.get("phone");
                    Intent intent = new Intent(ActLogin.this, Forgetpswd.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
                } else {
                    ToastUtil.showMessage("请使用正确手机号");
                }
            }
        });
        page.show(context);
    }


    private void msgLogin(Context context) {
        RegisterPage page = new RegisterPage();
        page.setTempCode(null); //没有申请短信模板编号
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //处理成功结果
//                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    Intent intent = new Intent(ActLogin.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.showMessage("请使用正确手机号");
                }
            }
        });
        page.show(context);
    }

    private void authorize(Platform plat) {
        if (plat == null) {
            ToastUtil.showMessage("空");
        }
        plat.removeAccount(true);//移除授权状态和本地缓存，下次授权会重新授权
        plat.SSOSetting(false);//SSO授权，传false默认是客户端授权
        plat.setPlatformActionListener(this);//授权回调监听，监听oncomplete，onerror，oncancel三种状态
        //plat.authorize();
        plat.showUser(null);//// 参数null表示获取当前授权用户资料
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        String headImageUrl = null; //头像
        String token;
        String gender;//性别
        String userId;
        String name = null;//用户名
        // 微信返回数据是打包的
        if (i == Platform.ACTION_USER_INFOR) {
            platDB = platform.getDb();//获取平台数据DB
//            if (platform.getName().equals(Wechat.NAME)){
//                //通过DB获取数据
//                token = platDB.getToken();
//                userId = platDB.getUserId();
//                name = platDB.getUserName();
//                gender = platDB.getUserGender();
//                headImageUrl = platDB.getUserIcon();
//            }
            if (platform.getName().equals(QQ.NAME)) {
                token = platDB.getToken();
                userId = platDB.getUserId();
                name = hashMap.get("nickname").toString();//用户名
                gender = hashMap.get("gender").toString();
                headImageUrl = hashMap.get("figureurl_qq_2").toString();
                String city = hashMap.get("city").toString();
                String province = hashMap.get("province").toString();
//              Intent intent = new Intent(this,MainActivity.class);
//              startActivity(intent);
            }
        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        ToastUtil.showMessage("错误");
    }

    @Override
    public void onCancel(Platform platform, int i) {

    }

    @OnClick(R.id.rememberPswd)
    public void onViewClicked() {
      //1  ToastUtil.showMessage("sssssss");
    }
}
