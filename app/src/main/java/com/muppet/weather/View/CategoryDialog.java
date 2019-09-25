package com.muppet.weather.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;

import com.muppet.weather.Adapter.CategoryListAdapter;
import com.muppet.weather.IpAddress;
import com.muppet.weather.Model.CategoryList;
import com.muppet.weather.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryDialog extends Dialog {

    @BindView(R.id.gv_category)
    GridView gvCategory;

    private CategoryListAdapter categoryListAdapter;
    private Context mContext;

    public CategoryDialog(@NonNull Context context) {
        super(context, R.style.CategoryDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_category);
        setCanceledOnTouchOutside(true);//点击窗口外是否关闭dialog
        ButterKnife.bind(this);

        /*gvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/
        initData();
    }

    private void initData() {

        categoryListAdapter = new CategoryListAdapter(mContext);
        gvCategory.setAdapter(categoryListAdapter);

        RequestParams params = new RequestParams(IpAddress.getImgUrl(IpAddress.IMG_CATEGORY));

        x.http().post(params, new Callback.CommonCallback<CategoryList>() {
            @Override
            public void onSuccess(CategoryList result) {
                if (result != null) {
                    List<CategoryList.ResBean.CategoryBean> list = result.getRes().getCategory();
                    categoryListAdapter.setCategoryData(list);
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
