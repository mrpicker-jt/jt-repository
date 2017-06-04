package com.gproject.android.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gproject.R;
import com.gproject.android.activity.BaseActivity;
import com.gproject.android.manager.DormManager;
import com.gproject.android.network.BaseException;
import com.gproject.android.network.BaseResponse;
import com.gproject.android.network.RequestListener;
import com.gproject.android.response.GetModeResponse;


/**
 * Created by 姜腾 on 2017/4/15.
 */

public class ModelFragment extends BaseFragment {
    private TextView modelName;
    private TextView modelRefresh;
    private TextView manualModelBtn;
    private TextView outModelBtn;
    private TextView studyModelBtn;
    private TextView sleepModelBtn;

    private BaseActivity activity;

    public ModelFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_model, container, false);
        activity = (BaseActivity) getActivity();
        initView(v);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateView();
            }
        }, 500);
        return v;
    }

    private void initView(View v) {
        modelName = (TextView) v.findViewById(R.id.model_name);
        modelRefresh = (TextView) v.findViewById(R.id.model_refresh);
        manualModelBtn = (TextView) v.findViewById(R.id.model_manual_btn);
        outModelBtn = (TextView) v.findViewById(R.id.model_out_btn);
        studyModelBtn = (TextView) v.findViewById(R.id.model_study_btn);
        sleepModelBtn = (TextView) v.findViewById(R.id.model_sleep_btn);

        modelRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateView();
            }
        });

        manualModelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showLoading(null);
                activity.getNetworkManager().changeMode(1, new RequestListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        activity.hideLoading();
                        updateView();
                    }

                    @Override
                    public void onFailure(BaseException exception, Throwable throwable) {
                        activity.hideLoading();
                        activity.getNetworkManager().handleNetworkFailure(exception, throwable);
                    }
                });
            }
        });
        studyModelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showLoading(null);
                activity.getNetworkManager().changeMode(2, new RequestListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        activity.hideLoading();
                        updateView();
                    }

                    @Override
                    public void onFailure(BaseException exception, Throwable throwable) {
                        activity.hideLoading();
                        activity.getNetworkManager().handleNetworkFailure(exception, throwable);
                    }
                });
            }
        });
        outModelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showLoading(null);
                activity.getNetworkManager().changeMode(3, new RequestListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        activity.hideLoading();
                        updateView();
                    }

                    @Override
                    public void onFailure(BaseException exception, Throwable throwable) {
                        activity.hideLoading();
                        activity.getNetworkManager().handleNetworkFailure(exception, throwable);
                    }
                });
            }
        });
        sleepModelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showLoading(null);
                activity.getNetworkManager().changeMode(4, new RequestListener<BaseResponse>() {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        activity.hideLoading();
                        updateView();
                    }

                    @Override
                    public void onFailure(BaseException exception, Throwable throwable) {
                        activity.hideLoading();
                        activity.getNetworkManager().handleNetworkFailure(exception, throwable);
                    }
                });
            }
        });
    }

    private void updateView() {
        activity.showLoading(null);
        activity.getNetworkManager().getMode(new RequestListener<GetModeResponse>() {
            @Override
            public void onSuccess(GetModeResponse response) {
                activity.hideLoading();
                DormManager.GetInstance(activity).setModelId(response.currentModeId);
                switch (response.currentModeId) {
                    case 1:
                        modelName.setText("手动模式");
                        break;
                    case 2:
                        modelName.setText("学习模式");
                        break;
                    case 3:
                        modelName.setText("外出模式");
                        break;
                    case 4:
                        modelName.setText("睡眠模式");
                        break;
                }
            }

            @Override
            public void onFailure(BaseException exception, Throwable throwable) {
                activity.hideLoading();
                activity.getNetworkManager().handleNetworkFailure(exception, throwable);
            }
        });
    }
}
