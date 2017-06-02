package com.lee.base.http;

import android.content.Context;

import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadRequest;

import java.util.Map;

/**
 * Created by liqg
 * 2016/7/18 14:50
 * Note :
 */
public abstract class Download extends BaseHttpDownload {


    public Download(Context context, RequestType requestType, String url, String path, String fileName) {
        super(context, requestType, url, path, fileName);
    }
    @Override
    public void submit(  final ResponseCallbackProgress requestCallback) {
        super.responseCallbackProgress = requestCallback;


        DownloadRequest request = NoHttp.createDownloadRequest(url, requestMethod, path, fileName, false, true);
        for (Map.Entry<String, String> e : requestParams.getUrlParams().entrySet()) {
            request.add(e.getKey(), e.getValue());
        }
        CallServer.getDownloadInstance().add(0, request, new DownloadListener() {
            @Override
            public void onDownloadError(int what, Exception exception) {
                onSubmitFailure(0, exception.getMessage(), null);
            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

            }

            @Override
            public void onProgress(int what, int progress, long fileCount) {
                onDownloadProgress(progress, fileCount);

            }

            @Override
            public void onFinish(int what, String filePath) {
                onSubmitSuccess(filePath);
            }

            @Override
            public void onCancel(int what) {

            }
        });
    }
}
