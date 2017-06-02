package com.lee.base.activity;

import android.content.Context;
import android.os.Bundle;

import com.lee.base.core.activity.PermissionActivity;

/**
 * Created by liqg
 * 2016/7/8 10:41
 * Note :
 */
public class BaseActivity extends PermissionActivity {
    public  Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
mContext=this;
    }

}
