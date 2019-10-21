package com.muppet.weather.View;

import android.view.ViewGroup;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SwipeToDismissWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemSwipeCallback.ItemDismissListener {

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;
    // Data list
    private List<?> mDataList;
    // A listener for a dismissal event.
    private ItemSwipeCallback.ItemDismissListener mItemDismissListener;

    public SwipeToDismissWrapper(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, List<?> dataList) {
        this.mAdapter = adapter;
        this.mDataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        mAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount();
    }

    @Override
    public void onItemDismiss(int position) {
        if (mItemDismissListener != null) {
            mItemDismissListener.onItemDismiss(position);
        } else {
            mDataList.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * Attach to RecyclerView for swipe-to-dismiss.
     *
     * @param recyclerView RecyclerView
     */
    public void attachToRecyclerView(RecyclerView recyclerView) {
        ItemTouchHelper.Callback callback = new ItemSwipeCallback(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * Set a listener for a dismissal event.
     *
     * @param itemDismissListener {@link ItemSwipeCallback.ItemDismissListener}
     */
    public void setItemDismissListener(ItemSwipeCallback.ItemDismissListener itemDismissListener) {
        this.mItemDismissListener = itemDismissListener;
    }
}
