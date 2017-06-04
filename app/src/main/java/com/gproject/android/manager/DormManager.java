package com.gproject.android.manager;

import android.content.Context;

import com.gproject.android.response.vo.Dorm;

/**
 * Created by 姜腾 on 2017/6/5.
 */

public class DormManager {
    private static DormManager instance;
    private Context context;
    private int modelId;
    private Dorm dorm = new Dorm();

    private DormManager(Context context) {
        this.context = context;
    }

    public static DormManager GetInstance(Context context) {
        if (instance == null) {
            synchronized (DormManager.class) {
                if (instance == null) {
                    instance = new DormManager(context);
                }
            }
        }
        return instance;
    }

    public static DormManager GetInstance() {
        return instance;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public Dorm getDorm() {
        return dorm;
    }

    public void setDorm(Dorm dorm) {
        this.dorm = dorm;
    }

}
