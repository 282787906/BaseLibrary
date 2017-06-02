package com.lee.base.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.lee.base.adapter.RefreshListAdapter;
import com.lee.base.core.utils.ToastUtil;
import com.lee.base.http.GetLon;
import com.lee.base.http.RequestType;

public class RefreshListFragment extends BaseListFragment {


    public static BaseFragment newInstance() {
        fragment = new RefreshListFragment();
        return fragment;
    }



    @Override
    protected SearchType enableSearch() {
        return null ;
    }

    /**
     * 本地数据初始化
     */
    @Override
    protected void initLocalData() {
        for (int i = 0; i < 20; i++) {
            data.add("name" + i);
        }

    }


    /**
     * 设置是否允许加载更多
     */
    @Override
    boolean enableLoadMore() {
        return true;
    }

    /**
     * 设置是否允许刷新
     */
    @Override
    boolean setEnableRefresh() {
        return true;
    }

    /**
     * 初始化时调用
     * 准备网络请求的参数
     */
    @Override
    boolean enableInitNetwork() {
        return true;
    }
    /**
     * 网络初始化前调用
     * 准备网络请求的参数
     */
    @Override
    protected void onStartInit() {


        String url = "http://gc.ditu.aliyun.com/geocoding";
//        url="http://192.168.1.215:8080/app/admin/message/list";
        getJson=new GetLon(getActivity(), RequestType.GET,url);
        getJson.requestParams.put("a", "长春市");



    }

    /**
     * 初始化成功时调用
     */
    @Override
    void onInitSuccess() {
        ToastUtil.showSingleToast(getActivity(),"onSucceed:" + getJson.getResult(), Toast.LENGTH_LONG);
    }

    /**
     * 刷新前调用
     * 准备网络请求的参数
     */
    @Override
    void onStartRefresh() {
        getJson.requestParams.put("a", "上海市");
    }

    /**
     * 刷新成功时调用
     */
    @Override
    void onRefreshSuccess() {
        ToastUtil.showSingleToast(getActivity(),"onSucceed:" + getJson.getResult(), Toast.LENGTH_LONG);
    }

    /**
     * 加载更多前调用
     * 准备网络请求的参数
     */
    @Override
    void onStartLoadMore() {
        getJson.requestParams.put("a", "北京市");
    }

    /**
     * 加载更多成功时调用
     */
    @Override
    void onLoadMoreSuccess() {
        ToastUtil.showSingleToast(getActivity(),"onSucceed:" + getJson.getResult(), Toast.LENGTH_LONG);
    }

    /**
     * 实例化Adapter
     * 需继承MyBaseAdapter
     */
    @Override
    void instanceAdapter() {
        mAdapter = new RefreshListAdapter(getActivity(), layoutInflater);
    }

    /**
     * 列表项点击事件
     *
     * @param adapterView
     * @param view
     * @param position
     * @param l
     */
    @Override
    void onItemClickListener(AdapterView<?> adapterView, View view, int position, long l) {

    }

    /**
     * 搜索事件
     *
     * @param s null表示不可编辑
     */
    @Override
    void onSearch(String s) {

    }


}
