package com.lee.base.http;

import android.content.Context;

/**
 * Created by liqg
 * 2016/7/18 16:32
 * Note :
 */
public class GetLon  extends GetJson {

    public GetLon(Context context, RequestType requestType, String url) {
        super(context, requestType, url);
    }

    @Override
    protected boolean analysis(String result) {
        setResult(result);
        return true;
    }
}
