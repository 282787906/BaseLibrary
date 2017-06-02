package com.lee.base.refreshlistview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;


public class XListView extends ListView implements OnScrollListener {
    private final String tag = "XListView";
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;
    private final static int SCROLL_BACK_HEADER = 0;
    private final static int SCROLL_BACK_FOOTER = 1;

    private final static int SCROLL_DURATION = 500;

    // when pull up >= 50px
    private final static int PULL_LOAD_MORE_DELTA = 50;

    // support iOS like pull
    private final static float OFFSET_RADIO = 1.8f;

    private float mLastY = -1;

    // used for scroll back
    private Scroller mScroller;
    // user's scroll listener
    private OnScrollListener mScrollListener;
    // for mScroller, scroll back from header or footer.
    private int mScrollBack;

    // the interface to trigger refresh and load more.
    private IXListViewListener mListener;
    private IPullToRefresh mPullToRefresh;
    private XHeaderView mHeader;
    // header view content, use it to calculate the Header's height. And hide it when disable pull refresh.
    private RelativeLayout mHeaderContent;

    private int mHeaderHeight;

    private LinearLayout mFooterLayout;
    private XFooterView mFooterView;
    private boolean mIsFooterReady = false;

    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = false;
    private boolean mInitLoading = false;
    private boolean mEnablePullLoad = true;
    private boolean mEnableAutoLoad = false;
    private boolean mPullLoading = false;

    private int mTotalItemCount;
    Context mContext;
    int headerHeightForInit = 0;
    boolean showLog = true;

    public void setShowLog(boolean showLog) {
        this.showLog = showLog;
    }

    public XListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void setHeaderView(XHeaderView myHeaderView) {
        initWithContext(mContext, myHeaderView);
    }

    private void initWithContext(Context context, XHeaderView myHeaderView) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        super.setOnScrollListener(this);

        // init header view
        if (myHeaderView != null) {
            mHeader = myHeaderView;
        } else {
            mHeader = new DefaultHeaderView(context);
        }

        mPullToRefresh = mHeader.getPullToRefresh();
        mHeaderContent = mHeader.getHeaderContent();

        addHeaderView(mHeader);
        mPullToRefresh.onInitialize();
        // init footer view
        mFooterView = new XFooterView(context);
        mFooterLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                .LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        mFooterLayout.addView(mFooterView, params);

        // init header height
        ViewTreeObserver observer = mHeader.getViewTreeObserver();
        if (null != observer) {
            observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation")
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onGlobalLayout() {
                    mHeaderHeight = mHeaderContent.getHeight();
                    ViewTreeObserver observer = getViewTreeObserver();

                    if (null != observer) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            observer.removeGlobalOnLayoutListener(this);
                        } else {
                            observer.removeOnGlobalLayoutListener(this);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // make sure com.lee.myxlistview.XFooterView is the last footer view, and only add once.
        if (!mIsFooterReady) {
            mIsFooterReady = true;
            addFooterView(mFooterLayout);
        }

        super.setAdapter(adapter);
    }

    /**
     * Enable or disable pull down refresh feature.
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;

        // disable, hide the content
        mHeaderContent.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * Enable or disable pull up load more feature.
     *
     * @param enable
     */
    public void setPullLoadEnable(boolean enable) {
        mEnablePullLoad = enable;

        if (!mEnablePullLoad) {
            mFooterView.setBottomMargin(0);
            mFooterView.hide();
            mFooterView.setPadding(0, 0, 0, mFooterView.getHeight() * (-1));
            mFooterView.setOnClickListener(null);

        } else {
            mPullLoading = false;
            mFooterView.setPadding(0, 0, 0, 0);
            mFooterView.show();
            mFooterView.setState(XFooterView.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * Enable or disable auto load more feature when scroll to bottom.
     *
     * @param enable
     */
    public void setAutoLoadEnable(boolean enable) {
        mEnableAutoLoad = enable;
    }

    /**
     * Start init
     * headerHeight  in dimen
     *
     * @param headerHeight
     */
    public void init(int headerHeight) {
        headerHeightForInit = headerHeight;
        if (null != mListener) {
            mInitLoading = true;
            mPullToRefresh.onPullDown_Refresh();
            mState = STATE_REFRESHING;
            mHeader.setVisibleHeight(headerHeight);
            mListener.onInit();
            if (showLog) {
                Log.d(tag, "startInit");
            }
        }
    }

    private void refresh() {
        if (mEnablePullRefresh && null != mListener) {
            mListener.onRefresh();
            if (showLog) {
                Log.d(tag, "refresh");
            }
        }
    }

    private void loadMore() {
        if (mEnablePullLoad && null != mListener) {
            mListener.onLoadMore();
            if (showLog) {
                Log.d(tag, "loadMore");
            }
        }
    }
    public void stopInitLoading() {

        if (mInitLoading) {
            mInitLoading = false;
            resetHeaderHeight();
            if (showLog) {
                Log.d(tag, "stopInitLoading");
            }
        }


    }

    /**
     * Stop refresh, reset header view.
     */
    public void stopRefresh() {
        if (mPullRefreshing) {
            mPullRefreshing = false;
            resetHeaderHeight();
            if (showLog) {
                Log.d(tag, "stopRefresh");
            }
        }




    }

    /**
     * Stop load more, reset footer view.
     */
    public void stopLoadMore() {
        if (mPullLoading) {
            mPullLoading = false;
            mFooterView.setState(XFooterView.STATE_NORMAL);
            if (showLog) {
                Log.d(tag, "stopLoadMore");
            }
        }
    }


    /**
     * Set listener.
     *
     * @param listener
     */
    public void setXListViewListener(IXListViewListener listener) {
        mListener = listener;
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnXScrollListener) {
            OnXScrollListener listener = (OnXScrollListener) mScrollListener;
            listener.onXScrolling(this);
        }
    }

    private int mState = STATE_NORMAL;

    private void updateHeaderHeight(float delta) {
        mHeader.setVisibleHeight((int) delta + mHeader.getVisibleHeight());
        if (mEnablePullRefresh && !mPullRefreshing) {
            if (mHeader.getVisibleHeight() > mHeaderHeight) {// chao guo yu zhi
                if (mState != STATE_READY) {
                    mPullToRefresh.onPullDown_OverThreshold();
                    mState = STATE_READY;
                }
            } else {
                if (mState != STATE_NORMAL) {
                    mPullToRefresh.onPullDown_LessThreshold();
                    mState = STATE_NORMAL;
                }
            }
        }
        setSelection(0);
    }

    private void resetHeaderHeight() {
        int height = mHeader.getVisibleHeight();

        if (height == 0) {
            return;
        }
        if (mPullRefreshing && height <= mHeaderHeight) {
            return;
        }

        int finalHeight = 0;
        if (mPullRefreshing && height > mHeaderHeight) {
            finalHeight = mHeaderHeight;
        }

        mScrollBack = SCROLL_BACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);

        invalidate();

    }


    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;

        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) {
                mFooterView.setState(XFooterView.STATE_READY);
            } else {
                mFooterView.setState(XFooterView.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);
    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();

        if (bottomMargin > 0) {
            mScrollBack = SCROLL_BACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(XFooterView.STATE_LOADING);
        loadMore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();

                if (getFirstVisiblePosition() == 0 && (mHeader.getVisibleHeight() > 0 ||
                        deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();

                } else if (getLastVisiblePosition() == mTotalItemCount - 1 && (mFooterView
                        .getBottomMargin() > 0 || deltaY < 0)) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;

            default:
                // reset
                mLastY = -1;
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    int h = mHeader.getVisibleHeight();
                    if (mEnablePullRefresh && h > mHeaderHeight && mPullRefreshing == false) {
                        mPullRefreshing = true;
                        mPullToRefresh.onPullDown_Refresh();
                        mState = STATE_REFRESHING;
                        isInitialize = false;
                        refresh();

                    }
                    resetHeaderHeight();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more.
                    if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * HeaderView isInitialize
     */
    boolean isInitialize = false;

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLL_BACK_HEADER) {
                mHeader.setVisibleHeight(mScroller.getCurrY());
                if (mScroller.getCurrY() == 0 && !isInitialize) {

                    mPullToRefresh.onInitialize();
                    mState = STATE_NORMAL;
                    isInitialize = true;
                }
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }

            postInvalidate();
            invokeOnScrolling();
        }

        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }

        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            if (mEnableAutoLoad && getLastVisiblePosition() == getCount() - 1) {
                startLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }




    /**
     * You can listen ListView.OnScrollListener or this one. it will invoke
     * onXScrolling when header/footer scroll back.
     */
    public interface OnXScrollListener extends OnScrollListener {
        public void onXScrolling(View view);
    }

    /**
     * Implements this interface to get refresh/load more event.
     *
     * @author markmjw
     */
    public interface IXListViewListener {

        public void onInit();

        public void onRefresh();

        public void onLoadMore();
    }


}
