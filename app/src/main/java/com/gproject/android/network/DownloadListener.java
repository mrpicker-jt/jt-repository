package com.gproject.android.network;

import java.io.File;


public interface DownloadListener {

    public void onSuccess(File file);

    public void onFailure(BaseException exception, Throwable throwable);

    public void onProgress(float progress);
}
