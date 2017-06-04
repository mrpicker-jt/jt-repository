package com.gproject.android.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gproject.R;
import com.gproject.android.manager.DormManager;

/**
 * Created by 姜腾 on 2017/4/15.
 */

public class MoniterItemFragment extends BaseFragment {
    private TextView powerCount;
    private TextView personStatus;

    public MoniterItemFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_moniter_item, container, false);
        powerCount = (TextView) v.findViewById(R.id.dorm_power_count);
        personStatus = (TextView) v.findViewById(R.id.dorm_person_status);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (DormManager.GetInstance().getDorm().personStatus) {
                    personStatus.setText("宿舍室内人员状态：有人");
                } else {
                    personStatus.setText("宿舍室内人员状态：无人");
                }
                powerCount.setText("宿舍本月用电量" + new java.text.DecimalFormat("#.00").
                        format(DormManager.GetInstance().getDorm().powerCount) + "度");
            }
        }, 400);
        return v;
    }
}
