package com.muppet.weather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.muppet.weather.Model.CategoryList;
import com.muppet.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListAdapter extends BaseAdapter {

    private Context mContext;
    private List<CategoryList.ResBean.CategoryBean> mlist = new ArrayList<>();

    public CategoryListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setCategoryData(List<CategoryList.ResBean.CategoryBean> mdata) {

        if (mdata != null) {
            this.mlist = mdata;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public CategoryList.ResBean.CategoryBean getItem(int position) {
        if (position > -1 && position < mlist.size()) {
            return mlist.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CategoryList.ResBean.CategoryBean item = getItem(position);
        holder.tvCategory.setText(item.getName());
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.tv_category)
        TextView tvCategory;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
