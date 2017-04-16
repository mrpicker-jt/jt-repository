package com.gproject.android.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gproject.R;
import com.gproject.android.adapter.MoniterFragmentAdapter;


public class MoniterFragment extends BaseFragment {

    public TabLayout tabLayout;

    public ViewPager viewPager;

    protected Activity mActivity;


    public MoniterFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_moniter, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        tabLayout = (TabLayout) v.findViewById(R.id.frag_moniter_tab_layout);
        viewPager = (ViewPager) v.findViewById(R.id.frag_moniter_viewpager);
        viewPager.setAdapter(new MoniterFragmentAdapter(getChildFragmentManager(), mActivity));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black_d), getResources().getColor(R.color.yellow_d));
    }


}
