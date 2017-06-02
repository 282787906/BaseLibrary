package com.lee.base.app_framework;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.lee.base.R;
import com.lee.base.activity.BaseActivity;
import com.lee.base.core.utils.DateUtil;
import com.lee.base.core.utils.ResUtil;
import com.lee.base.view.bott_nav.BottomNavigation;
import com.lee.base.view.bott_nav.BottomTabClickListener;
import com.lee.base.view.bott_nav.MainTab;

import java.util.ArrayList;
import java.util.List;

public class BottomNavigationActivity extends BaseActivity {
    private BottomNavigation mMainBottomNavigation;
    private FragmentManager fragmentManager;

    Fragment[] mFragments = new Fragment[4];
    private List<MainTab> mTabs;
    private int currentTab = -1;
    private int defaultTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        fragmentManager = getFragmentManager();
        mTabs = new ArrayList<>();
        initView();
    }

    private void initView() {

        mMainBottomNavigation = (BottomNavigation) findViewById(R.id.main_bottomNavigation);
        mTabs.add(new MainTab(R.string.tab_1, R.mipmap.zy, R.mipmap.zyx));
        mTabs.add(new MainTab(R.string.tab_2, R.mipmap.fw, R.mipmap.fwx));
        mTabs.add(new MainTab(R.string.tab_3, R.mipmap.fx, R.mipmap.fxx));
        mTabs.add(new MainTab(R.string.tab_4, R.mipmap.wd, R.mipmap.wdx));
        mMainBottomNavigation.init(mBottomNavigationBottomTabClickListener, mTabs, defaultTab, ResUtil.getColor(mContext, R.color.A1), ResUtil.getColor(mContext, R.color.B3));
        tabSelect(defaultTab);
    }
    private int from;
    private int to;
    BottomTabClickListener mBottomNavigationBottomTabClickListener = new BottomTabClickListener() {
        @Override
        public void onClick(int index) {

//            if (!Constant.isLogin(mContext) && (index == 3||index == 1)) {
//                from = currentTab;
//                to = index;
//                startActivityForResult(LoginActivity.class, REQUEST_CODE_UNLOGIN);
//                return;
//            }

            tabSelect(index);
        }
    };

    private void tabSelect(int index) {

        currentTab = index;
        mMainBottomNavigation.setCurrentTab(index);

        for (int i = 0; i < mFragments.length; i++) {
            if (mFragments[i] != null) {
                fragmentManager.beginTransaction()
                        .hide(mFragments[i]).commit();
            }
        }

        if (mFragments[index] == null) {

            switch (index) {
                case 0:
                    mFragments[index] = BlankFragment.newInstance(DateUtil.getCurrentDateTime(DateUtil.DATA_FORMAT_MICROSECOND),"");
                    break;
                case 1:
                    mFragments[index] =BlankFragment.newInstance(DateUtil.getCurrentDateTime(DateUtil.DATA_FORMAT_MICROSECOND),"");
                    break;
                case 2:
                    mFragments[index] =BlankFragment.newInstance(DateUtil.getCurrentDateTime(DateUtil.DATA_FORMAT_MICROSECOND),"");
                    break;
                case 3:
                    mFragments[index] = BlankFragment.newInstance(DateUtil.getCurrentDateTime(DateUtil.DATA_FORMAT_MICROSECOND),"");
                    break;


            }
            fragmentManager.beginTransaction()
                    .add(R.id.main_rl_container, mFragments[index]).commit();
        } else {
            fragmentManager.beginTransaction()
                    .show(mFragments[index]).commit();
        }
    }
}
