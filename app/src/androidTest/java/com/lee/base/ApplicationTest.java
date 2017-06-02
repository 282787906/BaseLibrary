package com.lee.base;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.lee.base.http.GetDefault;
import com.lee.base.http.GetJson;
import com.lee.base.http.RequestType;
import com.lee.base.http.ResponseCallback;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    String news = "http://apis.baidu.com/txapi/keji/keji";
    String apikey="8becc540b3aee80561d92c44ec14a722";
    String page1,page2;
    @org.junit.Test
    public void submit() throws Exception {
     final GetJson getJson = new GetDefault(mContext, RequestType.GET, news);
        getJson.getRequest().addHeader("apikey", apikey);
        getJson.requestParams.put("num", 2);
        getJson.requestParams.put("page", 1);
        getJson.requestParams.put("word", "android");
        getJson.submit(new ResponseCallback() {
            @Override
            public void onSuccess() {
                page1= (String) getJson.getAnalysisResult();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {

            }
        });

        final GetJson getJson1 = new GetDefault(mContext, RequestType.GET, news);
        getJson1.getRequest().addHeader("apikey", apikey);
        getJson1.requestParams.put("num", 2);
        getJson1.requestParams.put("page", 2);
        getJson1.requestParams.put("word", "ios");
        getJson1.submit(new ResponseCallback() {
            @Override
            public void onSuccess() {
                page2= (String) getJson1.getAnalysisResult();
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {

            }
        });
        assertEquals(page1,page2);
    }

}