package com.lee.base.http;


import android.content.Context;

import com.google.gson.JsonSyntaxException;
import com.lee.base.module.UpdateModule;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/23.
 */
public class UpdateTask extends GetJson {
    String tag = this.getClass().getName();

    public UpdateTask(Context context, RequestType requestType, String url) {
        super(context, requestType, url);
    }


    @Override
    protected boolean analysis(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            UpdateModule updateModule = gson.fromJson(jsonObject.getString("data"), UpdateModule.class);
            setAnalysisResult(updateModule);

            return true;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            setErrorMsg("analysis JsonSyntaxException");
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            setErrorMsg("analysis JSONException");
            return false;
        }
    }
}
