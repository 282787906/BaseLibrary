package com.lee.base.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.lee.base.core.BaseApplication;
import com.lee.base.core.business.AppManager;
import com.lee.base.core.business.Permission.CheckPermissionCallback;
import com.lee.base.core.business.Permission.Permission;
import com.lee.base.core.utils.DialogUtil;
import com.lee.base.core.utils.LiPackageUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Activity 基类
 * Created by liqg
 * 2015/11/10 18:48
 * Note :
 */
public abstract class PermissionActivity extends AppCompatActivity {
    protected Context mContext;
    protected String mTag;
    protected Handler handler;
    protected Activity activity;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        mTag = getClass().getSimpleName();
        mContext=this;
        handler = new Handler();
        AppManager.getAppManager().addActivity(this);
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        AppManager.getAppManager().finishActivity(this);
    }


    @Override
    protected void onPause() {

        super.onPause();


        BaseApplication.getInstance().onAppPause();

    }

    @Override
    protected void onResume() {
        super.onResume();


        BaseApplication.getInstance().onAppResume();


    }

    /**
     * 必要权限授权界面返回
     */
    private final int NEEDFUL_PERMISSIONS_RESULT = 101;
    /**
     * 非必要权限授权界面返回
     */
    private final int NEEDLESS_PERMISSIONS_RESULT = 102;
    private CheckPermissionCallback checkPermissionCallback;

    List<Permission> permissionList;

    /**
     * 权限 验证
     * （必要应用程序启动时调用 如loading界面，
     * 非必要权限再使用时验证）
     *
     * @param checkPermissionCallback
     */
    protected void checkPermission(List<Permission> permissionList, boolean isNeedful, CheckPermissionCallback checkPermissionCallback) {
        this.checkPermissionCallback = checkPermissionCallback;
        this.permissionList = permissionList;
        List<Permission> temp = new ArrayList<>();
        for (Permission permission : permissionList) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(permission.getPermissionName()) == PackageManager.PERMISSION_DENIED) {//启动时检测是否时必要权限且没有授权
                    temp.add(permission);
                }
            } else {
                //  6.0以下只能自行判断 如try catch  获取返回值等
            }
        }

        if (temp.size() > 0) {
            String[] permissions = new String[temp.size()];
            for (int i = 0; i < temp.size(); i++) {
                permissions[i] = temp.get(i).getPermissionName();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (isNeedful) {
                    requestPermissions(permissions, NEEDFUL_PERMISSIONS_RESULT);
                } else {
                    requestPermissions(permissions, NEEDLESS_PERMISSIONS_RESULT);
                }
            } else {
                showPremissionDialog(permissionList, permissions[0], isNeedful);
            }
        } else {
            checkPermissionCallback.checkOverPass();
        }
    }

    public void showPremissionDialog(List<Permission> permissions, String permissionsName, boolean isNeedful) {
        String reason = "";
        for (Permission permission : permissions) {
            if (permission.getPermissionName().equals(permissionsName)) {
                reason = permission.getReason();
                break;
            }
        }

        if (isNeedful) {
            DialogUtil.showOkCancel(AppManager.getAppManager().currentActivity(), reason + "\n设置路径：设置 ->应用管理 ->" + LiPackageUtil.getApplicationName(activity) + " ->权限", "确定", "退出", new DialogUtil.OkCancelDialogListener() {
                @Override
                public void onOkClickListener() {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivityForResult(intent, NEEDFUL_PERMISSIONS_RESULT);
                }

                @Override
                public void onCancelClickListener() {
                    checkPermissionCallback.checkOverCancel();
                }
            });
        } else {
            DialogUtil.showOkCancel(AppManager.getAppManager().currentActivity(), reason + "\n设置路径：设置 ->应用管理 ->" + LiPackageUtil.getApplicationName(activity) + " ->权限", "确定", "取消", new DialogUtil.OkCancelDialogListener() {
                @Override
                public void onOkClickListener() {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivityForResult(intent, NEEDLESS_PERMISSIONS_RESULT);
                }

                @Override
                public void onCancelClickListener() {
                    checkPermissionCallback.checkOverCancel();
                }
            });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == NEEDFUL_PERMISSIONS_RESULT) {
            for (int g : grantResults) {
                if (g == PackageManager.PERMISSION_GRANTED) {
                    checkPermission(permissionList, true, checkPermissionCallback);
                    return;
                }
            }
            showPremissionDialog(permissionList, permissions[0], true);
        } else {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPermission(permissionList, false, checkPermissionCallback);
                return;
            }
            showPremissionDialog(permissionList, permissions[0], false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (NEEDFUL_PERMISSIONS_RESULT == requestCode) {
            checkPermission(permissionList, true, checkPermissionCallback);
        }
    }

}
