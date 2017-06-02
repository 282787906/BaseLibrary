package com.lee.base.core.utils;

import android.content.Context;

/**
 * Created by liqg
 * 2017/1/13 09:49
 * Note :
 */
public class ConvertUtil {

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
