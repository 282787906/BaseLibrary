package com.lee.base.fragment;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.lee.base.R;
import com.lee.base.core.utils.ToastUtil;
import com.lee.base.http.GetJson;
import com.lee.base.http.ResponseCallback;
import com.lee.base.refreshlistview.DefaultHeaderView;
import com.lee.base.refreshlistview.IPullToRefresh;
import com.lee.base.refreshlistview.MyBaseAdapter;
import com.lee.base.refreshlistview.XHeaderView;
import com.lee.base.refreshlistview.XListView;

import java.util.ArrayList;
import java.util.List;

//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.controller.BaseControllerListener;
//import com.facebook.drawee.controller.ControllerListener;
//import com.facebook.drawee.interfaces.DraweeController;
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.facebook.imagepipeline.image.ImageInfo;

public abstract class BaseListFragment extends BaseFragment implements XListView.IXListViewListener {


    static BaseListFragment fragment;
    XListView mListView;
    List<Object> data;
    private int LIST_DATA_SIZE = 10;
    MyBaseAdapter mAdapter;

    Handler handler;
    RelativeLayout rl_errorBg;
    TextView tv_ErrorMsg;
    SearchType enableSearch;
    GetJson getJson;
    EditText keyWord;

    int refWait = 1000;
    private String tag = BaseListFragment.class.getSimpleName();

    public BaseListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //         setShowLog(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        handler = new Handler();
        view = inflater.inflate(R.layout.fragment_list, container, false);
        rl_errorBg = (RelativeLayout) view.findViewById(R.id.fragment_list_rl_ErrorBg);
        tv_ErrorMsg = (TextView) view.findViewById(R.id.fragment_list_tv_ErrorMsg);
        mListView = (XListView) view.findViewById(R.id.fragment_list_List);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setOnItemClickListener(onItemClickListener);
        data = new ArrayList<>();
        mListView.setDividerHeight(0);

        mListView.setHeaderView(new DefaultHeaderView(getActivity()));
        instanceAdapter();
        mListView.setPullLoadEnable(false);
        mListView.setPullRefreshEnable(false);
        mListView.setXListViewListener(this);
        mListView.setAdapter(mAdapter);
        enableSearch = enableSearch();
        showSearchBar(view, enableSearch);
        initLocalData();
        adapterSetData();
        if (enableInitNetwork()) {
            mListView.init((int) getResources().getDimension(R.dimen.header_height));
        }
        rl_errorBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enableSearch == SearchType.Gone || enableSearch == SearchType.Disedit || enableSearch == null) {
                    rl_errorBg.setVisibility(View.GONE);

//                    mListView.setVisibility(View.VISIBLE);
                    mListView.setPullRefreshEnable(true);
                    mListView.init((int) getResources().getDimension(R.dimen.header_height));
                } else {
                    onSearch(keyWord.getText().toString().trim());
                }
            }
        });
    }



    /**
     * 显示搜索框
     *
     * @param view
     */
    public void showSearchBar(View view, SearchType edit) {

        if (edit == SearchType.Gone || edit == null) {
            return;
        }
        view.findViewById(R.id.search_bar_rl_Bg).setVisibility(View.VISIBLE);
        keyWord = (EditText) view.findViewById(R.id.search_bar_et_Key);
        view.findViewById(R.id.search_bar_rl_Search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch(keyWord.getText().toString());
                //                Phone.hideKeyboard(keyWord, getContext());
            }
        });
        if (edit == SearchType.Enedit) {
            keyWord.setFocusableInTouchMode(true);
            keyWord.setFocusable(true);
            keyWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                        onSearch(v.getText().toString());
                        //                        Phone.hideKeyboard(key, getContext());
                        return true;
                    }
                    return false;
                }
            });
        } else {
            keyWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearch(null);
                }
            });

        }


    }

    /**
     * 加载成功时刷新数据
     */
    protected void adapterSetData() {
        mAdapter.setData(data);
        mAdapter.notifyDataSetChanged();

        if (data.size() == 0) {
            mListView.setPullRefreshEnable(false);
            tv_ErrorMsg.setText("抱歉!未查到相关数据");
            rl_errorBg.setVisibility(View.VISIBLE);
        } else {
            mListView.setPullRefreshEnable(setEnableRefresh());
            rl_errorBg.setVisibility(View.GONE);
        }

        if (enableLoadMore()) {
            if (data == null || data.size() < LIST_DATA_SIZE) {
                mListView.setPullLoadEnable(false);
            } else {
                mListView.setPullLoadEnable(true);

            }
        }
    }


    /**
     * 加载失败时提示
     *
     * @param msg
     */
    private void showLoadError(String msg) {
        mListView.stopInitLoading();
        mListView.stopRefresh();
        mListView.stopLoadMore();
        if (data.size() == 0) {
//            mListView.setPullRefreshEnable(false);
            tv_ErrorMsg.setText("抱歉!查询失败\n" + msg);
            rl_errorBg.setBackgroundResource(R.mipmap.list_error);
            rl_errorBg.setVisibility(View.VISIBLE);
          mListView.setVisibility(View.INVISIBLE);
        } else {
            mListView.setPullRefreshEnable(setEnableRefresh());
            ToastUtil.showSingleToast(getActivity(), "抱歉!查询失败:", Toast.LENGTH_LONG);
            rl_errorBg.setVisibility(View.GONE);

        }

    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            onItemClickListener(adapterView, view, i, l);
        }
    };

    @Override
    public void onInit() {
        mListView.setPullRefreshEnable(true);
        rl_errorBg.setVisibility(View.GONE);
        if (enableInitNetwork()) {
            onStartInit();
            //TODO  网络加载

            getJson.submit(new ResponseCallback() {
                @Override
                public void onSuccess() {

                    onInitSuccess();
                    adapterSetData();
                    mListView.stopInitLoading();

                }

                @Override
                public void onFail(int errorCode, String errorMsg) {

                    showLoadError(errorMsg);
                }
            });
        } else {
            mListView.stopInitLoading();
        }

    }


    @Override
    public void onRefresh() {
        onStartRefresh();
        //TODO  网络加载
        getJson.submit(new ResponseCallback() {
            @Override
            public void onSuccess() {
                onInitSuccess();
                adapterSetData();
                mListView.stopRefresh();

            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                showLoadError(errorMsg);
            }
        });

    }

    @Override
    public void onLoadMore() {
        onStartLoadMore();
        //TODO  网络加载
        getJson.submit(new ResponseCallback() {
            @Override
            public void onSuccess() {
                onInitSuccess();
                adapterSetData();
                mListView.stopRefresh();

            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                showLoadError(errorMsg);
            }
        });

    }

    /**
     * 本地数据初始化
     */
    protected abstract void initLocalData();

    protected abstract SearchType enableSearch();
    /**
     * 实例化Adapter
     * 需继承MyBaseAdapter
     */
    abstract void instanceAdapter();


    /**
     * 设置是否允许加载更多
     */
    abstract boolean enableLoadMore();

    /**
     * 设置是否允许刷新
     */
    abstract boolean setEnableRefresh();

    /**
     * 初始化时调用
     * 准备网络请求的参数
     */
    abstract boolean enableInitNetwork();

    /**
     * 网络初始化前调用
     * 准备网络请求的参数
     */
    protected abstract void onStartInit();

    /**
     * 初始化成功时调用
     */
    abstract void onInitSuccess();

    /**
     * 刷新前调用
     * 准备网络请求的参数
     */
    abstract void onStartRefresh();

    /**
     * 刷新成功时调用
     */
    abstract void onRefreshSuccess();

    /**
     * 加载更多前调用
     * 准备网络请求的参数
     */
    abstract void onStartLoadMore();

    /**
     * 加载更多成功时调用
     */
    abstract void onLoadMoreSuccess();


    /**
     * 列表项点击事件
     *
     * @param adapterView
     * @param view
     * @param position
     * @param l
     */
    abstract void onItemClickListener(AdapterView<?> adapterView, View view, int position, long l);

    /**
     * 搜索事件
     *
     * @param s null表示不可编辑
     */
    abstract void onSearch(String s);

    enum SearchType {
        Gone, Enedit, Disedit
    }

    class MyHeadView extends XHeaderView implements IPullToRefresh {
        Context context;
        SimpleDraweeView sdv_Img;
        DraweeController controller;
        Animatable animatable;

        public MyHeadView(Context context) {
            super(context);
            this.context = context;
        }

        /**
         * pull down over  rafreshing
         */
        @Override
        public void onPullDown_Refresh() {
            //  animatable.start();
        }

        /**
         * pull down less Threshold
         */
        @Override
        public void onPullDown_LessThreshold() {
            //            animatable.stop();
        }

        /**
         * pull down over Threshold
         */
        @Override
        public void onPullDown_OverThreshold() {
            //            animatable.start();
        }

        /**
         * init
         */
        @Override
        public void onInitialize() {
            if (controller == null) {
                ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(
                            String id,
                            @Nullable ImageInfo imageInfo,
                            @Nullable Animatable anim) {
                        if (anim != null) {
                            // 其他控制逻辑
                            anim.start();
                        }
                    }
                };
                controller = Fresco.newDraweeControllerBuilder()
                                            .setUri("asset:///gif.gif")
//                        .setUri("mipmap://" + R.mipmap.gif)
                        .setAutoPlayAnimations(false)
                        .setControllerListener(controllerListener)
                        .build();
            }
            sdv_Img.setController(controller);
            if (animatable == null   ) {
                animatable = sdv_Img.getController().getAnimatable();
            }
        }

        @Override
        public View initView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.my_header, null);
            sdv_Img = (SimpleDraweeView) view.findViewById(R.id.my_header_sdv_Img);
            return view;
        }

        @Override
        public IPullToRefresh getPullToRefresh() {
            return this;
        }


    }

}


