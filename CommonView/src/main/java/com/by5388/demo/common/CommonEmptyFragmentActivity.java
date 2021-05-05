package com.by5388.demo.common;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;


/**
 * @author Administrator  on 2020/9/12.
 */
public abstract class CommonEmptyFragmentActivity extends CommonSingleFragmentActivity {
    private String mMetaDataFragmentName;

    public abstract String getAppId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mMetaDataFragmentName = getAppId() + ".fragment";
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        try {
            return getFragmentByMetaData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorFragment.newInstance();
    }

    private Fragment getFragmentByMetaData() throws Exception {
        final ActivityInfo activityInfo = getPackageManager()
                .getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
        if (activityInfo.metaData == null) {
            return null;
        }
        final String fragmentName = activityInfo.metaData.getString(mMetaDataFragmentName);
        final Object object = getClassLoader().loadClass(fragmentName).newInstance();
        if (object instanceof Fragment) {
            return (Fragment) object;
        }
        return null;
    }
}
