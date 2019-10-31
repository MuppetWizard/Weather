package com.muppet.weather.View;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.muppet.weather.Activity.MainActivity;
import com.muppet.weather.Adapter.CategoryListAdapter;
import com.muppet.weather.IpAddress;
import com.muppet.weather.Model.CategoryList;
import com.muppet.weather.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryDialog extends Dialog {

    @BindView(R.id.gv_category)
    GridView gvCategory;
    @BindView(R.id.pg_progress)
    ProgressBar pgProgress;

    private CategoryListAdapter categoryListAdapter;
    private Context mContext;
    private List<CategoryList.ResBean.CategoryBean> list;
    private OnDialogItemClickListener mlistener;

    public CategoryDialog( Context context) {
        super(context, R.style.CategoryDialog);
    }

    public CategoryDialog( Context context, OnDialogItemClickListener clickListener) {
        super(context,R.style.CategoryDialog);
        this.mContext = context;
        this.mlistener = clickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_category);
        setCanceledOnTouchOutside(true);//点击窗口外是否关闭dialog
        ButterKnife.bind(this);

        initData();
        gvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Category = list.get(position).getId();
                mlistener.OnItemClicked(Category);
                dismiss();
            }
        });
    }

    private void initData() {

        categoryListAdapter = new CategoryListAdapter(mContext);
        gvCategory.setAdapter(categoryListAdapter);

        getCategory();

    }

    /**
     * 获取类别列表
     */
    private void getCategory() {

        list = new ArrayList<>();
        RequestParams params = new RequestParams(IpAddress.getImgUrl(IpAddress.IMG_CATEGORY));

        x.http().post(params, new Callback.CommonCallback<CategoryList>() {
            @Override
            public void onSuccess(CategoryList result) {
                if (result != null) {
                    pgProgress.setVisibility(View.GONE);//隐藏精度条
                    list = result.getRes().getCategory();//获取数据
                    categoryListAdapter.setCategoryData(list);//设置数据
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

    public interface OnDialogItemClickListener {
        void OnItemClicked(String categoryId);//获取点击item中的类别id
    }
}
