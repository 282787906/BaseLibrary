package com.lee.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lee.base.R;
import com.lee.base.core.utils.ToastUtil;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class Scan extends BaseFragment
implements ZBarScannerView.ResultHandler {
    ZBarScannerView zbar_View;
    public static Scan newInstance( ) {
        Scan fragment = new Scan();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_scan, container, false);
    zbar_View= (ZBarScannerView) view.findViewById(R.id.fragment_scan_zbar_View);
    return view;
    }

    @Override
    public void handleResult(Result result) {
        ToastUtil.showSingleToast(getActivity(),result.getContents(), Toast.LENGTH_LONG);
    }

    @Override
    public void onPause() {
        super.onPause();
        zbar_View.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        zbar_View.startCamera();
    }
}
