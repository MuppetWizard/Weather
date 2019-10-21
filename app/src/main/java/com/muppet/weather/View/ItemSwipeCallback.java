package com.muppet.weather.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemSwipeCallback extends ItemTouchHelper.Callback {

    private ItemDismissListener mItemDismissListener;

    public ItemSwipeCallback(ItemDismissListener itemDismissListener) {
        this.mItemDismissListener = itemDismissListener;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        // Set movement flags.
        int dragFlags = 0;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        // NO OP
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // Notify the adapter of the dismissal.
        mItemDismissListener.onItemDismiss(viewHolder.getAdapterPosition());
    }

    /**
     * Interface to listen for a dismissal event from a {@link ItemTouchHelper.Callback}.
     */
    public interface ItemDismissListener {

        /**
         * Called when an item has been dismissed by a swipe.
         *
         * @param position The position of the item dismissed.
         */
        void onItemDismiss(int position);
    }
}
