package com.lee.base.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lee.base.R;
import com.lee.base.core.business.AppManager;
import com.lee.base.core.business.Permission.CheckPermissionCallback;
import com.lee.base.core.business.Permission.Permission;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity {
   Activity  sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp=this;
        setContentView(R.layout.activity_splash);


        List<Permission> permissions = new ArrayList<>();
//        permissions.add(new Permission(Manifest.permission.ACCESS_FINE_LOCATION, "使用设备位置信息"));
        permissions.add(new Permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, "访问设备存储空间"));//读取文件不需要申请权限
//        permissions.add(new Permission(Manifest.permission.WRITE_CALENDAR, "访问日历信息"));
//        permissions.add(new Permission(Manifest.permission.CALL_PHONE, "拨打电话和管理通话"));//调用拨号界面不需要申请权限
        permissions.add(new Permission(Manifest.permission.CAMERA, "拍摄照片和录制视频"));
//        permissions.add(new Permission(Manifest.permission.SEND_SMS, "发送和查看短信"));
//        permissions.add(new Permission(Manifest.permission.BODY_SENSORS, "访问传感器数据"));
//        permissions.add(new Permission(Manifest.permission.WRITE_CONTACTS, "使用通讯录"));
//        permissions.add(new Permission(Manifest.permission.RECORD_AUDIO, "录制音频"));


        checkPermission(permissions, true,new CheckPermissionCallback() {
            @Override
            public void checkOverPass() {
                new Handler().postDelayed(new Runnable(){

                    @Override
                    public void run() {
                         startActivity(new Intent(sp,MainActivity.class));//跳转到MainActivity
                        AppManager.getAppManager().finishActivity(sp );
                        finish();
                    }
                }, 500);//给postDelayed()方法传递延迟参数



            }

            @Override
            public void checkOverCancel() {
                AppManager.getAppManager().AppExit();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
