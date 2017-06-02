package com.lee.base.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lee.base.R;
import com.lee.base.core.utils.ToastUtil;
import com.lee.base.http.GetDefault;
import com.lee.base.http.GetJson;
import com.lee.base.http.RequestType;
import com.lee.base.http.ResponseCallback;

public class ApiStoreActivity extends BaseActivity implements View.OnClickListener {

    private Button cityList;
    private EditText cityName;
    private Button imSend;
    private EditText inText;
    private TextView api_tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_store);
        initView();
    }

    private void initView() {
        cityList = (Button) findViewById(R.id.cityList);
        cityName = (EditText) findViewById(R.id.cityName);

        cityList.setOnClickListener(this);
        imSend = (Button) findViewById(R.id.imSend);
        imSend.setOnClickListener(this);
        inText = (EditText) findViewById(R.id.imText);
        inText.setOnClickListener(this);
        api_tv_result = (TextView) findViewById(R.id.api_tv_result);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cityList:
                getNews();
                break;
            case R.id.imSend:
                im();
                break;
        }
    }

    String apikey = "8becc540b3aee80561d92c44ec14a722";
    String turing = "http://apis.baidu.com/turing/turing/turing";
    String news = "http://apis.baidu.com/txapi/keji/keji";

    //String tq="http://apis.baidu.com/heweather/weather/free";
    private void getNews() {
        // validate
        String cityNameString = cityName.getText().toString().trim();
        if (TextUtils.isEmpty(cityNameString)) {
            Toast.makeText(this, "cityName", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        final GetJson getJson = new GetDefault(mContext, RequestType.GET, news);
        getJson.getRequest().addHeader("apikey", apikey);
        getJson.requestParams.put("num", 30);
        getJson.requestParams.put("page", 1);
        //        getJson.requestParams.put("cityid", cityNameString);
        getJson.submit(new ResponseCallback() {
            @Override
            public void onSuccess() {
                        showResult(String.valueOf(getJson.getAnalysisResult()));
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtil.showSingleToast(mContext, errorMsg, Toast.LENGTH_LONG);

            }
        });
    }

    private void showResult(String result) {

//        ToastUtil.showSingleToast(mContext,result, Toast.LENGTH_LONG);
        api_tv_result.setText(result);
    }


    private void im() {
        // validate
        String inTextString = inText.getText().toString().trim();
        if (TextUtils.isEmpty(inTextString)) {
            Toast.makeText(this, "msg", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO validate success, do something
        final GetJson getJson = new GetDefault(mContext, RequestType.GET, turing);
        getJson.getRequest().addHeader("apikey", apikey);
        getJson.requestParams.put("key", "879a6cb3afb84dbf4fc84a1df2ab7319");
        getJson.requestParams.put("userid", "123");
        getJson.requestParams.put("info", inTextString);
        getJson.submit(new ResponseCallback() {
            @Override
            public void onSuccess() {
                showResult(String.valueOf(getJson.getAnalysisResult()));
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtil.showSingleToast(mContext, errorMsg, Toast.LENGTH_LONG);

            }
        });

    }
}
