package com.gproject.android.manager;

import android.content.Context;
import android.widget.Toast;

import com.gproject.android.consts.NetworkConsts;
import com.gproject.android.network.AsyncHttpClientWrapper;
import com.gproject.android.network.BaseException;
import com.gproject.android.network.BaseResponse;
import com.gproject.android.network.NetworkError;
import com.gproject.android.network.RequestListener;
import com.gproject.android.response.LoginResponse;
import com.gproject.android.response.RegisterResponse;
import com.gproject.android.response.TestResponse;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;


public class NetworkManager {

    private static final String NET_ERROR = "当前网络不可用，请检测网络设置";
    private static final String SERVER_ERROR = "服务器出了点问题，请稍后再试";

    private AsyncHttpClient asyncHttpClient;
    private AsyncHttpClient downloadHttpClient;
    private SyncHttpClient syncHttpClient;

    private Context context;


    //    private
    public NetworkManager(Context context) {
        this.context = context;
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(30 * 1000);
        asyncHttpClient.setResponseTimeout(30 * 1000);
        asyncHttpClient.addHeader("device", "ANDROID");

        syncHttpClient = new SyncHttpClient();
        syncHttpClient.setConnectTimeout(30 * 1000);
        syncHttpClient.setResponseTimeout(30 * 1000);
        syncHttpClient.addHeader("device", "ANDROID");

        downloadHttpClient = new AsyncHttpClient();
        downloadHttpClient.setConnectTimeout(300 * 1000);
        downloadHttpClient.setResponseTimeout(300 * 1000);
        downloadHttpClient.addHeader("device", "ANDROID");

    }

    public void testConnection(RequestListener<TestResponse> listener) {
        RequestParams params = getDefaultParams();
        new AsyncHttpClientWrapper<TestResponse>(asyncHttpClient, context).startGetRequest(fullUrl(NetworkConsts.TEST_CONNECTION), params, TestResponse.class, listener);
    }


    public void cancelAllRequest() {
        asyncHttpClient.cancelAllRequests(true);
        syncHttpClient.cancelAllRequests(true);
        downloadHttpClient.cancelAllRequests(true);
    }

    public void login(String id, String password, RequestListener<LoginResponse> listener){
        RequestParams params=getDefaultParams();
        params.put("id",id);
        params.put("password",password);
        new AsyncHttpClientWrapper<LoginResponse>(asyncHttpClient,context).startPostRequest(fullUrl(NetworkConsts.USER_LOGIN),params,LoginResponse.class,listener);
    }

    public void register(String username,String password,int authority,RequestListener<RegisterResponse> listener){
        RequestParams params=getDefaultParams();
        params.put("username",username);
        params.put("password",password);
        params.put("authority",authority);
        new AsyncHttpClientWrapper<RegisterResponse>(asyncHttpClient,context).startPostRequest(fullUrl(NetworkConsts.USER_REGISTER),params,RegisterResponse.class,listener);
    }





    public void handleNetworkFailure(BaseException exception, Throwable throwable) {
        if (exception.code < NetworkError.NETWORK_STATUS_EEROR.code) {
            if (exception.code == NetworkError.NETWORK_NO_CONNECTION.code) {
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, NET_ERROR, Toast.LENGTH_SHORT).show();
            }
        } else if (exception.code < NetworkError.NETWORK_STATUS_EEROR.code + 1000) {
            Toast.makeText(context, SERVER_ERROR, Toast.LENGTH_SHORT).show();
        } else if (exception != null && exception.message != null) {
            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, NET_ERROR, Toast.LENGTH_SHORT).show();
        }
    }


    public void handleLoginNetworkFailure(BaseException exception, Throwable throwable) {
        if (exception.code < NetworkError.NETWORK_STATUS_EEROR.code) {
            if (exception.code == NetworkError.NETWORK_NO_CONNECTION.code) {
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, NET_ERROR, Toast.LENGTH_SHORT).show();
            }
        } else if (exception.code < NetworkError.NETWORK_STATUS_EEROR.code + 1000) {
            Toast.makeText(context, SERVER_ERROR, Toast.LENGTH_SHORT).show();

        } else if (exception != null && exception.message != null) {
            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, NET_ERROR, Toast.LENGTH_SHORT).show();
        }
    }

    private RequestParams getDefaultParams() {
        RequestParams params = new RequestParams();
        return params;
    }

    private String fullUrl(String prefix) {
        String utl = GlobalSettingManager.GetBaseUrl(context) + prefix;
        return utl;
    }

}