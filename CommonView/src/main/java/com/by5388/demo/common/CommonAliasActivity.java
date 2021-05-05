package com.by5388.demo.common;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

/**
 * @author Administrator  on 2021/3/22.
 */
public class CommonAliasActivity extends CommonSingleFragmentActivity {
    public static final String TAG = "CommonAliasActivity";
    private String mMetaDataFragmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mMetaDataFragmentName = getPackageName() + ".fragment";
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected Fragment createFragment() {
        final PackageManager packageManager = getPackageManager();
        try {
            final ActivityInfo activityInfo = packageManager.getActivityInfo(
                    getComponentName(), PackageManager.GET_META_DATA);
            final Bundle metaData = activityInfo.metaData;
            if (metaData == null) {
                return null;
            }
            Log.d(TAG, "createFragment: " + metaData);
            final String fragmentClassName = metaData.getString(mMetaDataFragmentName, ErrorFragment.class.getName());
            final Object object = getClassLoader().loadClass(fragmentClassName).newInstance();
            if (object instanceof Fragment) {
                return (Fragment) object;
            }
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
