package com.gproject.android.network;


public enum NetworkError {
    NETWORK_EEROR(1000, "服务器出了点问题，请稍后再试"),
    NETWORK_NO_CONNECTION(1001, "当前网络不给力，请稍后再试"),
    NETWORK_DATA_EEROR(1002, "网络数据解析失败"),
    NETWORK_DOWNLOAD_ERROR(1003, "下载文件失败"),
    NETWORK_DOWNLOAD_404(1004, "文件不存在"),
    NETWORK_STATUS_EEROR(2000, "服务器出了点问题，请稍后再试"), ;
    public int code;
    public String message;

    NetworkError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
