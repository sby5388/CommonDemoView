package com.by5388.demo.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.widget.TextView;

/**
 * @author admin  on 2021/5/10.
 */
public class MainItemAdapter extends CommonAdapter<ResolveInfo, MainItemAdapter.MainHolder> {
    private final Context mContext;

    public MainItemAdapter(Context context) {
        super(android.R.layout.simple_list_item_1);
        this.mContext = context;
    }

    @Override
    public MainHolder createHolder(View view) {
        return new MainHolder(mContext, view);
    }

    public static class MainHolder extends CommonAdapter.CommonHolder<ResolveInfo> {
        private final PackageManager pm;
        private final TextView mTextView;

        public MainHolder(Context context, View view) {
            pm = context.getPackageManager();
            mTextView = view.findViewById(android.R.id.text1);
        }

        @Override
        void bind(ResolveInfo resolveInfo) {
            if (resolveInfo == null) {
                return;
            }
            mTextView.setText(resolveInfo.loadLabel(pm).toString());
        }
    }
}
