package com.lee.base.business;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.lee.base.R;
import com.lee.base.core.business.AppManager;
import com.lee.base.core.utils.DialogUtil;
import com.lee.base.core.utils.FileUtil;
import com.lee.base.core.utils.LiPackageUtil;
import com.lee.base.core.utils.Md5Util;
import com.lee.base.core.utils.ProgressDialogUtil;
import com.lee.base.core.utils.ResUtil;
import com.lee.base.core.utils.ToastUtil;
import com.lee.base.core.utils.WaitDialog;
import com.lee.base.http.DownloadApk;
import com.lee.base.http.RequestType;
import com.lee.base.http.ResponseCallback;
import com.lee.base.http.ResponseCallbackProgress;
import com.lee.base.http.UpdateTask;
import com.lee.base.module.UpdateModule;

import java.io.File;

public class AppUpdateManager {
    String tag = AppUpdateManager.class.getName();
    /**
     * 无更新
     */
    private final int UPDATE_TAG_NONE = 0;
    /**
     * 启动时提示 强制更新
     */
    private final int UPDATE_TAG_FORCE = 1;
    /**
     * 启动时提示 非强制更新
     */
    private final int UPDATE_TAG_UNFORCE = 2;

    private Activity activity;
    private AppUpdateCallBack appUpdateCallBack;

    String filePath;
    String fileName;

    public AppUpdateManager(Activity activity, AppUpdateCallBack appUpdateCallBack) {
        this.activity = activity;
        this.appUpdateCallBack = appUpdateCallBack;
        this.filePath = FileUtil.getRootFilePath(activity) + Constant.NEW_VERSION_APK_PATH;
        this.fileName = Constant.NEW_VERSION_APK_NAME;
    }

    /**
     * 提交更新请求
     */
    public void submitUpdate() {
        WaitDialog.show(activity);
        final UpdateTask updateTask = new UpdateTask(activity, RequestType.GET, "http://12366app.tax.sh.gov.cn/phone/update");
        updateTask.requestParams.put("version_code", LiPackageUtil.getVersionCode(activity));
        updateTask.requestParams.put("system_id", "1001");

        updateTask.submit(new ResponseCallback() {
            @Override
            public void onSuccess() {

                final UpdateModule updateModule = (UpdateModule) updateTask.getAnalysisResult();
                //解析返回结果

                if (updateModule.getUpdateTag() == UPDATE_TAG_NONE) {//无新版本
                    appUpdateCallBack.toMain();
                } else {                //有新版本
                    WaitDialog.hide();
                    DialogUtil.showOkCancel(activity, "强制更新", "是", "否", new DialogUtil.OkCancelDialogListener() {
                        @Override
                        public void onOkClickListener() {
                            //先测本地是已下载安装文件

                            if (apkIsExist(updateModule)) {//是  安装
                                installNewApk(filePath + "/", Constant.NEW_VERSION_APK_NAME);

                            } else {//否  下载
                                downloadApk(updateModule);
                            }

                        }

                        @Override
                        public void onCancelClickListener() {
                            if (updateModule.getUpdateTag() == UPDATE_TAG_FORCE) { //强制更新 退出程序
                                AppManager.getAppManager().AppExit();
                            } else {//非强制更新 取消升级
                                appUpdateCallBack.toMain();
                            }

                        }
                    });
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                WaitDialog.hide();
                ToastUtil.showSingleToast(activity,errorMsg,Toast.LENGTH_LONG);
                appUpdateCallBack.toMain();
            }
        });

    }

    /**
     * 下载APk文件
     *
     * @param updateModule
     */
    public void downloadApk(final UpdateModule updateModule) {
        if (!FileUtil.createDirectory(filePath)) {
            return  ;
        }
        final ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil();
        progressDialogUtil.showProgress(activity);
        DownloadApk downloadTask = new DownloadApk(activity,RequestType.GET,"http://12366app.tax.sh.gov.cn"+  updateModule.getUpdateUrl(), filePath, fileName);

                downloadTask.submit(new ResponseCallbackProgress() {
            @Override
            public void onSuccess() {
                progressDialogUtil.dismiss();

                //Md5验证本地文件与要下载的文件是否相同
                String MD5 = Md5Util.fileToMD5(filePath + "/" + fileName).toLowerCase();
                if (updateModule.getUpdateMd5().toLowerCase().equals(MD5)) {
                    installNewApk(filePath + "/", fileName);
                } else {
                    downloadApkFail(updateModule);
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                progressDialogUtil.dismiss();
                downloadApkFail(updateModule);
            }

            @Override
            public void onProgress(final int progress, final long fileCount) {

                        progressDialogUtil.setProgress(progress );


                Log.d(tag,"progress："+progress+"%");
                ToastUtil.showSingleToast(activity,"progress："+progress+"%",Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 是否强制更新页面
     *
     * @param updateModule
     */
    public void downloadApkFail(final UpdateModule updateModule) {
        DialogUtil.showOkCancel(activity, ResUtil.getString(activity, R.string.downloadFail)
                , ResUtil.getString(activity, R.string.downloadAgain)
                , ResUtil.getString(activity, R.string.uploadExit), new DialogUtil.OkCancelDialogListener() {
                    @Override
                    public void onOkClickListener() {
                        downloadApk(updateModule);
                    }

                    @Override
                    public void onCancelClickListener() {
                        if (updateModule.getUpdateTag() == UPDATE_TAG_FORCE) {//强制更新
                            AppManager.getAppManager().AppExit();
                        } else {
                            appUpdateCallBack.toMain();
                        }
                    }
                });

    }


    /**
     * 查找本地是否有更新文件
     *
     * @param updateModule
     */
    public boolean apkIsExist(UpdateModule updateModule) {

        if (FileUtil.fileIsExist(filePath + "/" + fileName)) {

            //Md5验证本地文件与要下载的文件是否相同
            String MD5 = Md5Util.fileToMD5(filePath + "/" + fileName).toLowerCase();
            if (updateModule.getUpdateMd5().toLowerCase().equals(MD5)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 安装APK程序
     *
     * @param updatePath
     * @param updateAppName
     */
    public void installNewApk(String updatePath, String updateAppName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(updatePath, updateAppName)), "application/vnd.android.package-archive");
        activity.startActivity(intent);
        AppManager.getAppManager().AppExit();
    }

    public interface AppUpdateCallBack {
        void toMain();
    }
}