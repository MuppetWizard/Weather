package com.muppet.weather.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.muppet.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ModifyMyInfoActivity extends AppCompatActivity {

    @BindView(R.id.go_back)
    ImageView goBack;
    @BindView(R.id.show_faces)
    CircleImageView showFaces;
    @BindView(R.id.rl_modify_icon)
    RelativeLayout rlModifyIcon;
    @BindView(R.id.show_nickname)
    TextView showNickname;
    @BindView(R.id.rl_modify_nickname)
    RelativeLayout rlModifyNickname;
    @BindView(R.id.show_sex)
    TextView showSex;
    @BindView(R.id.rl_modify_sex)
    RelativeLayout rlModifySex;
    @BindView(R.id.show_birthday)
    TextView showBirthday;
    @BindView(R.id.rl_modify_birthday)
    RelativeLayout rlModifyBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_my_info);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.go_back, R.id.rl_modify_icon, R.id.rl_modify_nickname, R.id.rl_modify_sex, R.id.rl_modify_birthday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go_back:
                Intent intent = new Intent();
                intent.putExtra("goback", "返回");
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.rl_modify_icon:

                break;
            case R.id.rl_modify_nickname:
                break;
            case R.id.rl_modify_sex:
                break;
            case R.id.rl_modify_birthday:
                break;
        }
    }
}
