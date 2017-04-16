package com.gproject.android.network;


public interface RequestListener<JsonClass extends BaseResponse> {
    public void onSuccess(JsonClass response);

    public void onFailure(BaseException exception, Throwable throwable);
}
