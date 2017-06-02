package com.lee.base.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.lee.base.R;

public class WebViewTemplate extends BaseFragment implements View.OnClickListener {
    private WebView wvView;

    public static BaseFragment newInstance() {
        if (fragment == null) {
            fragment = new WebViewTemplate();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view_template, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.but_Load).setOnClickListener(this);
        wvView = (WebView) view.findViewById(R.id.wv_View);
        WebSettings wv_setttig = wvView.getSettings();
        wv_setttig.setJavaScriptEnabled(true);
        //设置本地调用对象及其接口
        wvView.addJavascriptInterface(this, "myObj");
    }

    @JavascriptInterface
    public String getJson() {
        String data = "{\n" +
                "    \"title\": \"基本例子\",\n" +
                "    \"isAdmin\": true,\n" +
                "    \"list\": [\n" +
                "        \"文艺\",\n" +
                "        \"博客\",\n" +
                "        \"摄影\",\n" +
                "        \"电影\",\n" +
                "        \"民谣\",\n" +
                "        \"旅行\",\n" +
                "        \"吉他\",\n" +
                "        \"吉他\",\n" +
                "        \"吉他\"\n" +
                "    ]\n" +
                "}";
        return data;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_Load:
                //TODO implement phoneLgCenter/form.html
//                wvView.loadUrl("file:///android_asset/art_template/basic.html");

//                wvView.loadUrl("file:///android_asset/phone_lg_center/form.html");
                wvView.loadUrl("http://192.168.1.24:6080/login");
                break;
        }
    }
}
