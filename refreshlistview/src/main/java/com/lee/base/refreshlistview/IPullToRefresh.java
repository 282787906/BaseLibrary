package com.lee.base.refreshlistview;


/**
 * Created by liqg
 * on 2015/7/7.
 */
public interface IPullToRefresh {


    /**
     * pull down over  rafreshing
     */
    void onPullDown_Refresh();

    /**
     * pull down less Threshold
     */
    void onPullDown_LessThreshold();

    /**
     * pull down over Threshold
     */
    void onPullDown_OverThreshold();

    /**
     * init
     */
    void onInitialize();

}
