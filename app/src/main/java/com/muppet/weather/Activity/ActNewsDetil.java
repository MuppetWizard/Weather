package com.muppet.weather.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.muppet.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActNewsDetil extends AppCompatActivity {

    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.lv_back)
    ImageView lvBack;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_news_detil);
        ButterKnife.bind(this);
        initWebView();
    }

    private void initWebView() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("URL")) {
            String url = intent.getStringExtra("URL");
            webview.loadUrl(url);
        }
    }
    @OnClick(R.id.lv_back)
    public void onViewClicked() {
        finish();
    }
}
