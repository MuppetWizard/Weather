package com.muppet.weather.Utils;

import android.widget.Toast;

public class Utils {

    /**
     * 截取字符串
     * @param str
     * @param strStart
     * @param strEnd
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {
        //找出指定的2个字符在 该字符串里面的 位置
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);
        //index 为负数 即表示该字符串中 没有该字符
        if (strStartIndex < 0) {
            ToastUtil.showMessage( "字符串-->" + str + "<--中不存在" + strStartIndex + "无法截取");
            return null;
        }
        if (strEndIndex < 0) {
            ToastUtil.showMessage( "字符串-->" + str + "<--中不存在" + strEndIndex + "无法截取");
            return null;
        }
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }
}
