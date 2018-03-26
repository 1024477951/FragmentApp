package com.fragmentapp.base;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by liuzhen on 2018/3/22.
 */

public abstract class ArrayRecyclerAdapter<T, V extends BaseViewHolder> extends BaseQuickAdapter<T,V> {

    public ArrayRecyclerAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void add(final T object) {
        mData.add(object);
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getItem(final int position) {
        return mData.get(position);
    }

    public long getItemId(final int position) {
        return position;
    }

    public void addAll(List<T> list) {
        final int size = getItemCount();
        setNewData(list);
        notifyItemRangeChanged(0, size);
    }

}
