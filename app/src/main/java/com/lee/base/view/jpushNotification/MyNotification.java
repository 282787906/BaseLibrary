package com.lee.base.view.jpushNotification;

import android.content.Context;

import com.lee.base.R;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;

/**
 * Created by liqg
 * 2016/8/17 10:16
 * Note :
 */
public class MyNotification extends BasicPushNotificationBuilder {
    public MyNotification(Context context) {
        super(context);
    }
public static CustomPushNotificationBuilder newInstance(Context context){
            CustomPushNotificationBuilder builder = new
                    CustomPushNotificationBuilder(context,
                    R.layout.customer_notitfication_layout,
                    R.id.customer_notitfication_iv_icon,
                    R.id.customer_notitfication_tv_title,
                    R.id.customer_notitfication_tv_text);
            // 指定定制的 Notification Layout
            builder.statusBarDrawable = R.drawable.ic_menu_gallery;
            // 指定最顶层状态栏小图标
            builder.layoutIconDrawable = R.drawable.ic_menu_share;
            // 指定下拉状态栏时显示的通知图标

    return builder;
}
}
