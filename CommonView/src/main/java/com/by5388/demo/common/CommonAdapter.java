package com.by5388.demo.common;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin  on 2021/5/10.
 */
public abstract class CommonAdapter<E, H extends CommonAdapter.CommonHolder<E>>
        extends BaseAdapter {

    private List<E> mList;
    private int mItemLayout;

    public abstract H createHolder(View view);

    public CommonAdapter(@LayoutRes int itemLayout) {
        mList = new ArrayList<>();
        this.mItemLayout = itemLayout;
    }


    public void setDataList(List<E> list) {
        mList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public E getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H holder;
        if (convertView == null) {
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(mItemLayout, parent, false);
            holder = createHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (H) convertView.getTag();
        }
        holder.bind(getItem(position));
        return convertView;
    }


    public abstract static class CommonHolder<E> {
        abstract void bind(E e);
    }

}
