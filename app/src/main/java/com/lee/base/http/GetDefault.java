package com.lee.base.http;

import android.content.Context;

/**
 * Created by liqg
 * 2016/7/18 16:32
 * Note :
 */
public class GetDefault extends GetJson {

    public GetDefault(Context context, RequestType requestType, String url) {
        super(context, requestType, url);
    }

    @Override
    protected boolean analysis(String result) {
        if (result==null){
         setAnalysisResult("");
        }else {
            setAnalysisResult(result);
        }return true;
    }
}
