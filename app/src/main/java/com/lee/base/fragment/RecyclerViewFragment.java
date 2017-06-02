package com.lee.base.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lee.base.R;
import com.lee.base.adapter.RecyclerViewAdapter;
import com.lee.base.business.DataProvider;
import com.lee.base.core.utils.ToastUtil;
import com.lee.base.view.MyRecyclerView;

public class RecyclerViewFragment extends BaseFragment {
    MyRecyclerView mMyRecyclerView;
    RecyclerViewAdapter mRecyclerViewAdapter;

    public static BaseFragment newInstance() {


        fragment = new RecyclerViewFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        mMyRecyclerView = (MyRecyclerView) view.findViewById(R.id.rv_Test);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mMyRecyclerView.setLayoutManager(layoutManager);
        mMyRecyclerView.setHasFixedSize(true);

        mRecyclerViewAdapter = new RecyclerViewAdapter(mContext, DataProvider.getPersonList(0), onRecyclerViewListener);
        mMyRecyclerView.setAdapter(mRecyclerViewAdapter);

//        mMyRecyclerView.addItemDecoration(new MyDividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));

//        mMyRecyclerView.addItemDecoration(
//                new RecycleViewDivider(mContext,
//                LinearLayout.VERTICAL ,
//                LiConvertUtil.dp2px(mContext, 4),
//                ResUtil.getColor(mContext, R.color.black)));
        return view;
    }


    RecyclerViewAdapter.OnRecyclerViewListener onRecyclerViewListener = new RecyclerViewAdapter.OnRecyclerViewListener() {
        @Override
        public void OnItemClick(int position) {
            ToastUtil.showSingleToast(mContext, "onItemClick:" + position, Toast.LENGTH_LONG);

        }

        @Override
        public void OnMenuAddClick(int position) {
            mMyRecyclerView.closeMenu();
            ToastUtil.showSingleToast(mContext, "OnMenuAddClick:" + position, Toast.LENGTH_LONG);
        }

        @Override
        public void OnMenuEditClick(int position) {
            ToastUtil.showSingleToast(mContext, "OnMenuEditClick:" + position, Toast.LENGTH_LONG);
            mMyRecyclerView.closeMenu();
        }

        @Override
        public void OnMenuChangeClick(int position) {
            ToastUtil.showSingleToast(mContext, "OnMenuChangeClick:" + position, Toast.LENGTH_LONG);
            mMyRecyclerView.closeMenu();
        }

        @Override
        public void OnItemLongClick(int position) {
            ToastUtil.showSingleToast(mContext, "onItemLongClick:" + position, Toast.LENGTH_LONG);
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mMyRecyclerView.setLayoutManager(layoutManager);

    }
}
