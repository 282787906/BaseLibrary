package com.lee.base.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lee.base.R;
import com.lee.base.core.utils.ProgressDialogUtil;
import com.lee.base.http.DownloadTest;
import com.lee.base.http.GetDefault;
import com.lee.base.http.GetLon;
import com.lee.base.http.RequestType;
import com.lee.base.http.ResponseCallback;
import com.lee.base.http.ResponseCallbackProgress;
import com.lee.base.module.LonLat;
import com.yolanda.nohttp.FileBinary;
import com.yolanda.nohttp.OnUploadListener;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoHttp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoHttp extends Fragment implements View.OnClickListener {

    private TextView tv_Msg;
    GetDefault submit;

    public NoHttp() {
        // Required empty public constructor
    }

    public static NoHttp newInstance() {
        NoHttp fragment = new NoHttp();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_nohttp, null);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.nohttp_but_GetLon).setOnClickListener(this);
        view.findViewById(R.id.nohttp_but_upload).setOnClickListener(this);
        view.findViewById(R.id.nohttp_but_Download).setOnClickListener(this);
        view.findViewById(R.id.nohttp_but_KuaYu).setOnClickListener(this);
        view.findViewById(R.id.nohttp_but_GetHead).setOnClickListener(this);
        view.findViewById(R.id.nohttp_but_DownloadManager).setOnClickListener(this);
        tv_Msg = (TextView) view.findViewById(R.id.nohttp_tv_Msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nohttp_but_GetLon:


                String url = "http://gc.ditu.aliyun.com/geocoding";
                final GetLon getLon = new GetLon(getActivity(), RequestType.GET, url);
                getLon.requestParams.put("a", "长春市");
                getLon.submit(new ResponseCallback() {
                    @Override
                    public void onSuccess() {

                        LonLat lonLat = new Gson().fromJson(getLon.getResult(), LonLat.class);
                        tv_Msg.setText("onSucceed:" + getLon.getResult());
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {

                        tv_Msg.setText("onFailed:" + errorCode + "  " + errorMsg);
                    }
                });
                break;
            case R.id.nohttp_but_Download:

                //                String fileUrl = "http://12366app.tax.sh.gov.cn/downloads/swj.apk";
                String fileUrl = "http://shlgservice.com/cmsDownLoad/downLoadFile?id=5";
                //                String fileUrl = "https://raw.githubusercontent.com/yanzhenjie/NoHttp/master/Jar/nohttp1.0.1.jar";
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
                final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil();
                progressDialogUtil.showProgress(getActivity());


                DownloadTest downloadTest = new DownloadTest(getActivity(), RequestType.GET, fileUrl, path, "qwe.pdf");
                downloadTest.submit(new ResponseCallbackProgress() {
                                        @Override
                                        public void onSuccess() {
                                            tv_Msg.setText("onSucceed");
                                            progressDialogUtil.dismiss();
                                        }

                                        @Override
                                        public void onFail(int errorCode, String errorMsg) {
                                            tv_Msg.setText("onFailed:" + errorCode + "  " + errorMsg);
                                            progressDialogUtil.dismiss();
                                        }

                                        @Override
                                        public void onProgress(int progress, long fileCount) {
                                            tv_Msg.setText("onProgress" + progress + "/%");
                                            progressDialogUtil.setProgress(progress);
                                        }
                                    }
                );

                break;

            case R.id.nohttp_but_KuaYu:
                String ky121 = "http://121.40.43.32:8080/static/data.json";
                final GetDefault getDefault = new GetDefault(getActivity(), RequestType.GET, ky121);

                getDefault.submit(new ResponseCallback() {
                    @Override
                    public void onSuccess() {

                        //                        LonLat lonLat=    new Gson().fromJson( getLon.getResult(),LonLat.class);
                        tv_Msg.setText("onSucceed:" + getDefault.getResult());
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {

                        tv_Msg.setText("onFailed:" + errorCode + "  " + errorMsg);
                    }
                });
                break;
            case R.id.nohttp_but_upload:

                File file = new File("/sdcard/Download/logo28.png");
                tv_Msg.setText("File: " + file.getAbsolutePath());
                FileBinary binary = new FileBinary(file);
                binary.setUploadListener(123123, mOnUploadListener);
                //
//                submit = new GetDefault(getActivity(), RequestType.POST, "http://api.nohttp.net/upload");
                submit = new GetDefault(getActivity(), RequestType.POST, "http://192.168.1.140:8080/file/upload");
                submit.requestParams.put("name", "namename");
                submit.requestParams.put("files", binary);

                submit.submit(new ResponseCallback() {
                    @Override
                    public void onSuccess() {

                        tv_Msg.setText(tv_Msg.getText() + "\nsubmit onSuccess\n"+submit.getAnalysisResult().toString());
                    }

                    @Override
                    public void onFail(int i, String s) {

                        tv_Msg.setText("submit onFail: " + s);
                    }
                });

                break;
            case R.id.nohttp_but_GetHead:
//                String url1 = "http://shlgservice.com/cmsDownLoad/downLoadFile?id=5";
                String url1 = ((EditText) rootView.findViewById(R.id.nohttp_et_url)).getText().toString();
                submit = new GetDefault(getActivity(), RequestType.HEAD, url1);
                //                submit.requestParams.put("name", "name");
                //                submit.requestParams.put("head", binary);

                submit.submit(new ResponseCallback() {
                    @Override
                    public void onSuccess() {

                        tv_Msg.setText("submit onSuccess\n" + submit.getAnalysisResult().toString());
                    }

                    @Override
                    public void onFail(int i, String s) {

                        tv_Msg.setText(tv_Msg.getText() + "submit onFail: " + s);
                    }
                });

                break;

            case R.id.nohttp_but_DownloadManager:


                break;
        }
    }

    private OnUploadListener mOnUploadListener = new OnUploadListener() {
        @Override
        public void onStart(int what) {// 文件开始上传。
            tv_Msg.setText(tv_Msg.getText() + "\n OnUploadListener onStart " + what);

        }

        @Override
        public void onCancel(int what) {// 文件的上传被取消时。

            tv_Msg.setText(tv_Msg.getText() + "\n OnUploadListener onCancel " + what);
        }

        @Override
        public void onProgress(int what, int progress) {// 文件的上传进度发生变化。
            tv_Msg.setText(tv_Msg.getText() + "\n OnUploadListener onProgress " + what + "   " + progress);
        }

        @Override
        public void onFinish(int what) {// 文件上传完成
            tv_Msg.setText(tv_Msg.getText() + "\n OnUploadListener onFinish " + what);
        }

        @Override
        public void onError(int what, Exception exception) {// 文件上传发生错误。
            tv_Msg.setText(tv_Msg.getText() + "\n OnUploadListener onError " + what + "    " + exception.getMessage());
        }
    };

}
