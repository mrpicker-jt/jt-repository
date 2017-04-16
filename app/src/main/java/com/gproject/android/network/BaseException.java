package com.gproject.android.network;

import java.util.List;

public class BaseException extends BaseResponse {
    public int code;
    public String message;
    public List<Integer> invalidIds;

    public BaseException() {

    }

    public BaseException(int code, String message, List<Integer> invalidIds) {
        this.code = code;
        this.message = message;
        this.invalidIds = invalidIds;
    }

    public BaseException(NetworkError error) {
        this.code = error.code;
        this.message = error.message;
    }

    public BaseException(int statusCode, String rawData) {
        this.code = NetworkError.NETWORK_STATUS_EEROR.code + statusCode;
        this.message = rawData;
    }
}
