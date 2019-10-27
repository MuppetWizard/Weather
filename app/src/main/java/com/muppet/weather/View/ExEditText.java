package com.muppet.weather.View;

import android.content.Context;
import android.graphics.Rect;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

import com.muppet.weather.R;
import com.muppet.weather.Utils.EmojiFilter;

public class ExEditText extends AppCompatEditText {

    Rect clearRect = new Rect();

    public ExEditText(Context context) {
        super(context);
    }

    public ExEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //在不影响默认过滤规则的情况下,添加Emoji表情过滤
        if ("emoji".equalsIgnoreCase(String.valueOf(getTag()))) {
            final InputFilter[] filters = getFilters();
            final InputFilter[] newFilters = new InputFilter[filters.length + 1];
            System.arraycopy(filters, 0, newFilters, 0, filters.length);
            newFilters[filters.length] = new EmojiFilter();
            setFilters(newFilters);
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        //焦点改变,检查是否需要显示删除按钮
        checkEdit(focused);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //用来计算删除按钮所在的矩形区域
        clearRect.set(w - getPaddingRight() - Math.min(w, h), getPaddingTop(), w - getPaddingRight(), Math.min(w, h) - getPaddingBottom());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //检查点击删除按钮区域,并且当前具有焦点
        if (event.getAction() == MotionEvent.ACTION_DOWN && isFocused()) {
            //检查是否在删除按钮矩形区域,按下
            if (checkClear(event.getX(), event.getY())) {
                //如果当前已经是空的, 不处理Touch事件,否则清空文本,达到删除的效果
                if (!TextUtils.isEmpty(getText())) {
                    setText("");
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void checkEdit(boolean focused) {
        if (TextUtils.isEmpty(getText()) || !focused) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            //这里才是关键, 用的是原生的方法显示删除图标.
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.base_edit_delete, 0);
        }
    }

    //坐标是否在按钮区
    private boolean checkClear(float x, float y) {
        return clearRect.contains(((int) x), (int) y);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        //时刻检查一下
        checkEdit(true);
    }
}
