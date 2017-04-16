package com.gproject.android.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.gproject.utils.DataUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;


public class AsyncHttpClientWrapper<JsonClass extends BaseResponse> {

    private AsyncHttpClient asyncHttpClient;
    private Context context;
    private Gson gson;

    public AsyncHttpClientWrapper(AsyncHttpClient asyncHttpClient, Context context) {
        this.asyncHttpClient = asyncHttpClient;
        this.context = context;
        gson = DataUtils.GetGson();
    }

    public RequestHandle startGetRequest(String url, RequestParams params, final Class clazz, final RequestListener<JsonClass> listener) {
        if (checkConnection() == false) {
            if (listener != null) {
                listener.onFailure(new BaseException(NetworkError.NETWORK_NO_CONNECTION), null);
            }
            return null;
        }
        RequestHandle requestHandle = asyncHttpClient.get(context, url, params, getJsonHandler(clazz, listener));
        return requestHandle;
    }

    public RequestHandle startPostRequest(String url, RequestParams params, final Class clazz, final RequestListener<JsonClass> listener) {
        if (checkConnection() == false) {
            if (listener != null) {
                listener.onFailure(new BaseException(NetworkError.NETWORK_NO_CONNECTION), null);
            }
            return null;
        }
        params.setUseJsonStreamer(true);
        RequestHandle requestHandle = asyncHttpClient.post(context, url, params, getJsonHandler(clazz, listener));
        return requestHandle;
    }

    public RequestHandle startMultipartPostRequest(String url, RequestParams params, final Class clazz, final RequestListener<JsonClass> listener) {
        if (checkConnection() == false) {
            if (listener != null) {
                listener.onFailure(new BaseException(NetworkError.NETWORK_NO_CONNECTION), null);
            }
            return null;
        }

        RequestHandle requestHandle = asyncHttpClient.post(context, url, params, getJsonHandler(clazz, listener));
        return requestHandle;
    }

    public RequestHandle startDownloadRequest(String url, final String path, boolean forceReload, final DownloadListener listener) {
        final String cachePath = path + ".tmp";
        File cacheFile = new File(cachePath);
        if (cacheFile.exists()) {
            cacheFile.delete();
        }
        File saveFile = new File(path);
        if (forceReload == false && saveFile.exists()) {
            listener.onProgress(1);
            listener.onSuccess(saveFile);
            return null;
        }

        if (checkConnection() == false) {
            if (listener != null) {
                listener.onFailure(new BaseException(NetworkError.NETWORK_NO_CONNECTION), null);
            }
            return null;
        }

        final RequestHandle requestHandle = asyncHttpClient.get(context, url, new FileAsyncHttpResponseHandler(cacheFile) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                if (statusCode == 404) {
                    if (listener != null) {
                        listener.onFailure(new BaseException(NetworkError.NETWORK_DOWNLOAD_404), throwable);
                    }
                }
                if (listener != null) {
                    listener.onFailure(new BaseException(NetworkError.NETWORK_DOWNLOAD_ERROR), throwable);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                File destFile = new File(path);
                if (destFile.exists()) {
                    destFile.delete();
                }
                destFile = new File(path);
                if (file.renameTo(destFile)) {
                    if (listener != null) {
                        listener.onSuccess(destFile);
                    }
                } else {
                    if (listener != null) {
                        listener.onFailure(new BaseException(NetworkError.NETWORK_DOWNLOAD_ERROR), null);
                    }
                }
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);

                if (listener != null) {
                    listener.onProgress(Math.max(0, Math.min(1, bytesWritten / (float) totalSize)));
                }
            }
        });

        return requestHandle;
    }

    private BaseJsonHttpResponseHandler<BaseResponse> getJsonHandler(final Class clazz, final RequestListener<JsonClass> listener) {
        BaseJsonHttpResponseHandler<BaseResponse> handler = new BaseJsonHttpResponseHandler<BaseResponse>() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, BaseResponse response) {
                if (listener != null) {
//                    if (response instanceof BaseException) {
//                        listener.onFailure((BaseException) response, null);
//                    } else {
//                        Log.d(getClass().getName(), rawJsonResponse);
//                        listener.onSuccess((JsonClass) response);
//                    }
                    BaseResponse baseResponse = DataUtils.JsonStr2Obj(rawJsonResponse, BaseResponse.class);
                    if (baseResponse.err != null) {
                        listener.onFailure(new BaseException(baseResponse.err.code, baseResponse.err.message), null);
                    } else {
                        listener.onSuccess((JsonClass) response);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, BaseResponse errorResponse) {
                if (rawJsonData != null) {
                    Log.d(AsyncHttpClientWrapper.class.getName(), rawJsonData);
                }
                if (throwable != null) {
                    throwable.printStackTrace();
                }

                BaseException exception = null;
                if (rawJsonData != null && statusCode == 400) {
                    try {
                        exception = gson.fromJson(rawJsonData, BaseException.class);
                    } catch (Throwable t) {
                        exception = new BaseException(NetworkError.NETWORK_DATA_EEROR);
                    }
                    if (listener != null) {
                        listener.onFailure(exception, throwable);
                    }
                    return;
                }

                if (statusCode > 0 && statusCode < 1000) {
                    if (listener != null) {
                        listener.onFailure(new BaseException(statusCode, rawJsonData), throwable);
                    }

                    return;
                }

                if (listener != null) {
                    throwable.printStackTrace();
                    listener.onFailure(new BaseException(NetworkError.NETWORK_EEROR), throwable);
                }
            }

            @Override
            protected BaseResponse parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                if (isFailure) {
                    return null;
                }

                try {
                    return (BaseResponse) gson.fromJson(rawJsonData, clazz);
                } catch (Throwable t) {
                    t.printStackTrace();
                    return (BaseResponse) new BaseException(NetworkError.NETWORK_DATA_EEROR);
                }
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                if (listener != null && listener instanceof ProgressRequestListener) {
                    Log.d("ahcw progress", bytesWritten + "," + totalSize + "," + (bytesWritten / (float) totalSize));
                    ((ProgressRequestListener) listener).onProgress(Math.max(0, Math.min(1, bytesWritten / (float) totalSize)));
                }
            }
        };
        handler.setUseSynchronousMode(false);
        handler.setUsePoolThread(false);
        return handler;
    }

    private boolean checkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || networkInfo.isConnected() == false) {
            return false;
        }

        return true;
    }
}
