package com.gproject.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gproject.R;

/**
 * Created by 姜腾 on 2017/4/15.
 */

public class MoniterItemFragment extends BaseFragment {
    public MoniterItemFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_moniter_item, container, false);
        return v;
    }
}
