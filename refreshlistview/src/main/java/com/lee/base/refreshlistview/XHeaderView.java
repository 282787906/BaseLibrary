package com.lee.base.refreshlistview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public abstract class XHeaderView extends LinearLayout {

    private String tag = XHeaderView.class.getSimpleName();
    private LinearLayout mContainer;
    private RelativeLayout mRelativeLayout;
    Context mContext;

    public XHeaderView(Context context) {
        super(context);
        mContext = context;
        addView(initView(mContext));

    }

    public void addView(View view) {

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        mContainer = new LinearLayout(mContext);
        mRelativeLayout = new RelativeLayout(mContext);
        int h = (int) mContext.getResources().getDimension(R.dimen.header_height);
        LayoutParams lpn = new LayoutParams(LayoutParams.MATCH_PARENT, h);

        RelativeLayout.LayoutParams lpng = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, h);

        mRelativeLayout.addView(view, lpng);
        mContainer.addView(mRelativeLayout, lpn);
        mContainer.setGravity(Gravity.BOTTOM);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);
    }

    public abstract View initView(Context context);

    /**
     * Set the header view visible height.
     *
     * @param height
     */
    public void setVisibleHeight(int height) {

        if (mContainer == null) {
            return;
        }
        if (height < 0) {
            height = 0;
        }
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    /**
     * Get the header view visible height.
     *
     * @return
     */
    public int getVisibleHeight() {
        if (mContainer == null) {
            return 0;
        }

        return mContainer.getHeight();
    }

    public RelativeLayout getHeaderContent() {
        return mRelativeLayout;
    }

    public abstract IPullToRefresh getPullToRefresh() ;
}
