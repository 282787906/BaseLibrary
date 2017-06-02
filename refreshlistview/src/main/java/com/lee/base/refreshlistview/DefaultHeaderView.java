package com.lee.base.refreshlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class DefaultHeaderView extends XHeaderView implements IPullToRefresh {

    private String tag = DefaultHeaderView.class.getSimpleName();

    private ProgressBar mProgressBar;
    private TextView mHintTextView;

    public DefaultHeaderView(Context context) {
        super(context);
    }


    public View initView(Context context) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.vw_header, null);

        mHintTextView = (TextView) view.findViewById(R.id.header_hint_text);
        mProgressBar = (ProgressBar) view.findViewById(R.id.header_progressbar);

        return view;
    }


    @Override
    public void onPullDown_Refresh() {
        mProgressBar.setVisibility(VISIBLE);
        mHintTextView.setText(R.string.pullDown_Refreshing);
    }


    @Override
    public void onPullDown_LessThreshold() {
        mProgressBar.setVisibility(INVISIBLE);
        mHintTextView.setText(R.string.pullDown_LessThreshold);
    }

    @Override
    public void onPullDown_OverThreshold() {
        mHintTextView.setText(R.string.pullDown_OverThreshold);

        mProgressBar.setVisibility(INVISIBLE);
    }

    @Override
    public void onInitialize() {
        mProgressBar.setVisibility(INVISIBLE);
        mHintTextView.setText(R.string.pullDown_Initialize);

    }

    @Override
    public IPullToRefresh getPullToRefresh() {
        return this;
    }
}
