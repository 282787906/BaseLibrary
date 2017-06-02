package com.lee.base.core.business.Permission;

/**
 * Created by liqg
 * 2016/7/11 14:34
 * Note :
 */
public interface CheckPermissionCallback {
    /**
     * 权限授权完成
     */
    void checkOverPass();

    /**
     * 权限申请未授权且点击提示框取消按钮
     */
    void checkOverCancel();
}
