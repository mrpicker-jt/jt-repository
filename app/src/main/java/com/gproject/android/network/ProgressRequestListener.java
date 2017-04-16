package com.gproject.android.network;


public interface ProgressRequestListener<JsonClass extends BaseResponse> extends RequestListener<JsonClass> {
    public void onProgress(float progress);
}
