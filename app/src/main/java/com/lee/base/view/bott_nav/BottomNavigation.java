package com.lee.base.view.bott_nav;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.GridView;

import java.util.List;

/**
 * Created by liqg
 * 2016/12/15 14:27
 * Note :
 */
public class BottomNavigation extends GridView {
    private Context mContext;
    BottomNavigationAdapter mBottomNavigationAdapter;


    public BottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setBackgroundColor(Color.TRANSPARENT);

    }

    List<MainTab> tabs;

    public void init(BottomTabClickListener clickListener, List<MainTab> tabs, int selectedIndex, int textColor_selected, int textColor_normal) {
        this.tabs = tabs;
        setNumColumns(tabs.size());

        tabs.get(selectedIndex).setSelected(true);
        mBottomNavigationAdapter = new BottomNavigationAdapter(mContext, tabs, clickListener);
        mBottomNavigationAdapter.setTextColor(textColor_selected, textColor_normal);
        clickListener.onClick(selectedIndex);
        setAdapter(mBottomNavigationAdapter);
    }


    public void setCurrentTab(int index) {
        for (int i = 0; i < tabs.size(); i++) {
            if (i == index) {

                tabs.get(i).setSelected(true);
            } else {

                tabs.get(i).setSelected(false);

            }
        }
        mBottomNavigationAdapter.setData(tabs);
        mBottomNavigationAdapter.notifyDataSetChanged();
    }
}
