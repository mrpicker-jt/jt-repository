package com.gproject.android.fragment;


import android.support.v4.app.Fragment;

/**
 * Created by 姜腾 on 2017/4/15.
 */

public class BaseFragment extends Fragment {
    private boolean isFirstShow = true;

    public BaseFragment() {
        super();
    }


    public void onFirstShow() {
        isFirstShow = false;
    }

    public boolean isFirstShow() {
        return isFirstShow;
    }
}
