package com.gproject.android.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gproject.R;
import com.gproject.android.activity.BaseActivity;
import com.gproject.android.adapter.MoniterFragmentAdapter;
import com.gproject.android.manager.DormManager;
import com.gproject.android.network.BaseException;
import com.gproject.android.network.RequestListener;
import com.gproject.android.response.GetDormInfoResponse;


public class MoniterFragment extends BaseFragment {

    public TabLayout tabLayout;

    public ViewPager viewPager;

    protected BaseActivity mActivity;
    private DormManager dormManager;


    public MoniterFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_moniter, container, false);
        dormManager = DormManager.GetInstance(mActivity);
        initView(v);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 100);
        return v;
    }

    private void getData() {
        mActivity.showLoading(null);
        mActivity.getNetworkManager().getDormInfo(new RequestListener<GetDormInfoResponse>() {
            @Override
            public void onSuccess(GetDormInfoResponse response) {
                mActivity.hideLoading();
                dormManager.setDorm(response.doc);
            }

            @Override
            public void onFailure(BaseException exception, Throwable throwable) {
                mActivity.hideLoading();
                mActivity.getNetworkManager().handleNetworkFailure(exception, throwable);
            }
        });
    }


    private void initView(View v) {
        tabLayout = (TabLayout) v.findViewById(R.id.frag_moniter_tab_layout);
        viewPager = (ViewPager) v.findViewById(R.id.frag_moniter_viewpager);
        viewPager.setAdapter(new MoniterFragmentAdapter(getChildFragmentManager(), mActivity));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black_d), getResources().getColor(R.color.yellow_d));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                getData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


}
