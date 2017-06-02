package com.lee.base.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.lee.base.adapter.FrescoAdapter;
import com.lee.base.core.utils.ToastUtil;
import com.lee.base.http.GetLon;
import com.lee.base.http.RequestType;

public class RefreshListFrescoFragment extends BaseListFragment {


    public static BaseFragment newInstance() {
        fragment = new RefreshListFrescoFragment();
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

        String[] images = new String[] { "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383264_3954.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383264_4787.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383264_8243.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383248_3693.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383243_5120.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383242_3127.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383242_9576.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383242_1721.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383219_5806.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383214_7794.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383213_4418.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383213_3557.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383210_8779.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383172_4577.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383166_3407.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383166_2224.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383166_7301.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383165_7197.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383150_8410.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383131_3736.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383130_5094.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383130_7393.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383129_8813.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383100_3554.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383093_7894.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383092_2432.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383092_3071.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383091_3119.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383059_6589.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383059_8814.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383059_2237.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383058_4330.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383038_3602.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382942_3079.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382942_8125.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382942_4881.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382941_4559.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382941_3845.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382924_8955.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382923_2141.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382923_8437.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382922_6166.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382922_4843.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382905_5804.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382904_3362.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382904_2312.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382904_4960.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382900_2418.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382881_4490.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382881_5935.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382880_3865.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382880_4662.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382879_2553.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382862_5375.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382862_1748.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382861_7618.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382861_8606.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382861_8949.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382841_9821.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382840_6603.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382840_2405.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382840_6354.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382839_5779.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382810_7578.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382810_2436.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382809_3883.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382809_6269.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382808_4179.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382790_8326.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382789_7174.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382789_5170.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382789_4118.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382788_9532.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382767_3184.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382767_4772.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382766_4924.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382766_5762.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406382765_7341.jpg" };

        for (int i = 0; i < images.length; i++) {
            data.add(images[i]);
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
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
        imagePipeline.clearDiskCaches();
        imagePipeline.clearCaches();
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
        mAdapter = new FrescoAdapter(getActivity(), layoutInflater);
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
