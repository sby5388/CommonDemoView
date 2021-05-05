package com.by5388.demo.sample;

import com.by5388.demo.common.CommonMainActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CommonMainActivity {

    @Override
    public List<String> getCategories() {
        final ArrayList<String> strings = new ArrayList<>();
        strings.add(BuildConfig.APPLICATION_ID);
        return strings;
    }
}
