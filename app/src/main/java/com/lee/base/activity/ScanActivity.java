package com.lee.base.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.lee.base.R;
import com.lee.base.core.utils.ToastUtil;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanActivity extends BaseActivity implements ZBarScannerView.ResultHandler {

    ZBarScannerView zbar_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        zbar_View = (ZBarScannerView) findViewById(R.id.activity_scan_zbar_View);
    }

    @Override
    public void handleResult(Result result) {
        ToastUtil.showSingleToast(this, result.getContents(), Toast.LENGTH_LONG);

    }

    @Override
    protected void onResume() {
        super.onResume();
        zbar_View.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        zbar_View.stopCamera();
    }
}
