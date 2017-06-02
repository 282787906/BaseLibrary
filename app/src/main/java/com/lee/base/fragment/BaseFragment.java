package com.lee.base.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by liqg on 2015/10/22.
 */
public class BaseFragment extends Fragment {
    LayoutInflater layoutInflater;
    Context mContext;
    View view;
    public static BaseFragment fragment;
Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = getActivity();

        handler=new Handler();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected String getClassName() {
        return getClass().getSimpleName();
    }

    @Override
    public void onResume() {
        mContext = getActivity();
        super.onResume();
        //        MobclickAgent.onPageStart(getClassName());
    }

    @Override
    public void onPause() {
        super.onPause();
        //        MobclickAgent.onPageEnd(getClassName());
    }
}
