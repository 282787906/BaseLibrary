package com.lee.base.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqg
 * 2016/11/3 09:47
 * Note :
 */
public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        super(context);
        mViewConfig = ViewConfiguration.get(getContext());
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mViewConfig = ViewConfiguration.get(getContext());
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mViewConfig = ViewConfiguration.get(getContext());
    }

    /**
     * Invalid position.
     */
    private static final int INVALID_POSITION = -1;
    protected SwipeMenuLayout mOldSwipedLayout;
    protected int mOldTouchedPosition = INVALID_POSITION;

    protected ViewConfiguration mViewConfig;
    private boolean isInterceptTouchEvent = true;
    private int mDownX;
    private int mDownY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean isIntercepted = super.onInterceptTouchEvent(e);
        if (!isInterceptTouchEvent) {
            return isIntercepted;
        } else {
            if (e.getPointerCount() > 1) return true;
            int action = e.getAction();
            int x = (int) e.getX();
            int y = (int) e.getY();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = x;
                    mDownY = y;
                    isIntercepted = false;

                    int touchingPosition = getChildAdapterPosition(findChildViewUnder(x, y));
                    if (touchingPosition != mOldTouchedPosition && mOldSwipedLayout != null && mOldSwipedLayout.isMenuOpen()) {
                       mOldSwipedLayout.smoothCloseMenu();
                        isIntercepted = true;
                    }
                    if (isIntercepted) {
                        mOldSwipedLayout = null;
                        mOldTouchedPosition = INVALID_POSITION;
                    } else {
                        ViewHolder vh = findViewHolderForAdapterPosition(touchingPosition);
                        if (vh != null) {
                            View itemView = getSwipeMenuView(vh.itemView);
                            if (itemView != null && itemView instanceof SwipeMenuLayout) {
                                mOldSwipedLayout = (SwipeMenuLayout) itemView;
                                mOldTouchedPosition = touchingPosition;
                            }
                        }
                    }
                    break;
                // They are sensitive to retain sliding and inertia.
                case MotionEvent.ACTION_MOVE:
                    isIntercepted = handleUnDown(x, y, isIntercepted);
                    ViewParent viewParent = getParent();
                    if (viewParent != null) {
                        viewParent.requestDisallowInterceptTouchEvent(!isIntercepted);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    isIntercepted = handleUnDown(x, y, isIntercepted);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    isIntercepted = handleUnDown(x, y, isIntercepted);
                    break;
            }
        }
        return isIntercepted;

    }

    private View getSwipeMenuView(View itemView) {
        if (itemView instanceof SwipeMenuLayout) return itemView;
        List<View> unvisited = new ArrayList<>();
        unvisited.add(itemView);
        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            if (!(child instanceof ViewGroup)) { // view
                continue;
            }
            if (child instanceof SwipeMenuLayout) return child;
            ViewGroup group = (ViewGroup) child;
            final int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) unvisited.add(group.getChildAt(i));
        }
        return itemView;
    }
    private boolean handleUnDown(int x, int y, boolean defaultValue) {
        int disX = mDownX - x;
        int disY = mDownY - y;
        // swipe
        if (Math.abs(disX) > mViewConfig.getScaledTouchSlop())
            defaultValue = false;
        // click
        if (Math.abs(disY) < mViewConfig.getScaledTouchSlop() && Math.abs(disX) < mViewConfig.getScaledTouchSlop())
            defaultValue = false;
        return defaultValue;
    }


    public void closeMenu(){
        if ( mOldSwipedLayout != null && mOldSwipedLayout.isMenuOpen()) {
            mOldSwipedLayout.smoothCloseMenu();

        }
    }
}
