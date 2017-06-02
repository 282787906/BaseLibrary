package com.lee.base.view.banner;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liqg
 * on 2015/12/28.
 */
public class MyBanner_Adapter extends PagerAdapter {


    List<View> viewsList;
    private LayoutInflater inflater;

    Context context;

    MyBanner_Adapter(Context context) {
        this.context = context;
        viewsList = new ArrayList<>();
        inflater = LayoutInflater.from(context);

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return viewsList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        View view = viewsList.get(position);
        viewGroup.addView(view);
        return view;
    }

    public void setData(List<MyBannerModel> list) {
        MyBannerView myBannerView = new MyBannerView(context, inflater);
        if (list == null || list.size() == 0) {
            return;
        } else if (list.size() == 1) {
            viewsList.add(myBannerView.newView(list.get(0)));
        } else if (list.size() > 1) {
            viewsList.add(myBannerView.newView(list.get(list.size() - 1)));
            for (int i = 0; i < list.size(); i++) {
                viewsList.add(myBannerView.newView(list.get(i)));
            }
            viewsList.add(myBannerView.newView(list.get(0)));
        }
        notifyDataSetChanged();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}