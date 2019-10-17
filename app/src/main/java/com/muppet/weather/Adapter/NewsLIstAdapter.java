package com.muppet.weather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.muppet.weather.Model.NewsList;
import com.muppet.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsLIstAdapter extends BaseAdapter {

    private Context mContext;
    private List<NewsList.ResultBean.DataBean> mData = new ArrayList<>();
    private int itemCount = 3;

    public NewsLIstAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setDate(List<NewsList.ResultBean.DataBean> list) {
        if (list != null) {
            this.mData = list;
            notifyDataSetChanged();
        }
    }

    public void addItemNum(int number) {
        itemCount = number;
    }
    @Override
    public int getCount() {
        if (mData.size() > 3) {
            return itemCount;
        } else {
            return mData.size();
        }

    }

    @Override
    public NewsList.ResultBean.DataBean getItem(int position) {
        if (position > -1 && position < mData.size()) {
            return mData.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final NewsList.ResultBean.DataBean item = getItem(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvAuthor.setText(item.getAuthor_name());
        Glide.with(mContext).load(item.getThumbnail_pic_s())
                .error(R.mipmap.img_normal)
                .into(holder.ivNew);
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.iv_new)
        ImageView ivNew;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
