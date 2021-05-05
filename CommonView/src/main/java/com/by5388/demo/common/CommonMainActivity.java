package com.by5388.demo.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class CommonMainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {
    private ItemAdapter mItemAdapter;

    private static void sortData(List<ResolveInfo> resolveInfos) {
        boolean skipSort = true;
        if (skipSort) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resolveInfos.sort(new Comparator<ResolveInfo>() {
                @Override
                public int compare(ResolveInfo o1, ResolveInfo o2) {
                    return 0;
                }
            });
        } else {
            Collections.sort(resolveInfos, new Comparator<ResolveInfo>() {
                @Override
                public int compare(ResolveInfo o1, ResolveInfo o2) {
                    return 0;
                }
            });
        }
    }

    public abstract List<String> getCategories();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_main);
        mItemAdapter = new ItemAdapter(this);
        final ListView listView = findViewById(android.R.id.list);
        final View emptyView = findViewById(android.R.id.empty);
        listView.setEmptyView(emptyView);
        listView.setAdapter(mItemAdapter);
        listView.setOnItemClickListener(this);
        loadData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final ResolveInfo info = mItemAdapter.getItem(position);
        final ComponentName componentName = new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);
        final Intent intent = new Intent();
        intent.setComponent(componentName);
        final List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
        if (resolveInfos.isEmpty()) {
            // TODO: 2020/12/23 这里需要显示错误提示
            final DialogFragment dialogFragment = ErrorDialog.newInstance("错误", "未找到匹配的活动", false);
            dialogFragment.show(getSupportFragmentManager(), "ErrorDialog");
        } else {
            startActivity(intent);
        }
    }

    private void loadData() {
        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        // TODO: 2020/8/12 某个包名下的所有activity
        final List<String> categories = getCategories();
        for (String category : categories) {
            intent.addCategory(category);
        }
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        mItemAdapter.setResolveInfos(resolveInfos);
    }

    private static class ItemAdapter extends BaseAdapter {
        private PackageManager mPackageManager;
        private List<ResolveInfo> mResolveInfos;

        public ItemAdapter(Context context) {
            mPackageManager = context.getApplicationContext().getPackageManager();
        }

        public void setResolveInfos(List<ResolveInfo> resolveInfos) {
            sortData(resolveInfos);
            mResolveInfos = resolveInfos;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (mResolveInfos == null) {
                return 0;
            }
            return mResolveInfos.size();
        }

        @Override
        public ResolveInfo getItem(int position) {
            if (mResolveInfos == null || mResolveInfos.isEmpty()) {
                return null;
            }
            return mResolveInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            if (mResolveInfos == null) {
                return 0;
            }
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.bind(getItem(position), mPackageManager);

            return convertView;
        }
    }

    private static class ViewHolder {
        private final TextView mTextView;

        ViewHolder(View view) {
            mTextView = view.findViewById(android.R.id.text1);
        }

        private void bind(ResolveInfo info, PackageManager pm) {
            if (info == null) {
                return;
            }
            final CharSequence labelSeq = info.loadLabel(pm);
            mTextView.setText(labelSeq.toString());
        }
    }
}