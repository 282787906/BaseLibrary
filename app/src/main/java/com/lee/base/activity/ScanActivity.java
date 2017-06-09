package com.lee.base.activity;

import android.os.Bundle;
import android.view.View;

import com.lee.base.R;
import com.lee.base.core.utils.ToastUtil;
import com.lee.base.qrcode.zbar.OnScanListener;
import com.lee.base.qrcode.zbar.ScanView;

public class ScanActivity extends BaseActivity   {

    ScanView scanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        scanView = (ScanView) findViewById(R.id.activity_scan_zbar_View);
        scanView.init(new OnScanListener() {
            @Override
            public void onScanSuccess(String result) {
                ToastUtil.longToast(mContext,result);
            }

            @Override
            public void onScanError(Exception ex, String result) {
                ToastUtil.longToast(mContext,"onScanError"+result);
ex.printStackTrace();
            }

            @Override
            public void onScanStart() {
                ToastUtil.longToast(mContext,"onScanStart");

            }
        });
        scanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.longToast(mContext,"reScan");
                scanView.reScan();

            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        scanView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanView.onPause();
    }
}
