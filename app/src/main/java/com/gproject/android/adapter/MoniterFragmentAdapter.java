package com.gproject.android.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gproject.android.fragment.MoniterItemFragment;

/**
 * Created by 姜腾 on 2017/4/15.
 */

public class MoniterFragmentAdapter extends FragmentStatePagerAdapter {
    private static String[] tags = {"宿舍", "灯光", "温度", "湿度", "其他"};
    private Context mContext;


    public MoniterFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return new MoniterItemFragment();
    }

    @Override
    public int getCount() {
        return tags.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tags[position];
    }
}
