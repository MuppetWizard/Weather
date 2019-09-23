package com.muppet.weather.Utils;


import android.text.TextUtils;
import android.widget.Toast;

import com.muppet.weather.App;



/**
 * Created by carson2440 on 2016/5/17.
 */
public class ToastUtil {
    public static void showMessage(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            Toast toast = Toast.makeText(App.getAppContext(), text,
                    Toast.LENGTH_SHORT);
            // toast.getView().setBackgroundResource(R.drawable.base_tip_bg);
//            toast.getView().setPadding(45, 45, 45, 45);
            toast.show();
        }

    }

}
