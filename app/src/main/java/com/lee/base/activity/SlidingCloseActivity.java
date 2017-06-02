package com.lee.base.activity;

import android.os.Bundle;
import android.util.Log;

import com.lee.base.R;
import com.lee.base.view.SwipeBackLayout;



public class SlidingCloseActivity extends BaseActivity {
    SwipeBackLayout swipeBackLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_close);
          swipeBackLayout = (SwipeBackLayout) findViewById(R.id.swipe_layout_two);
        swipeBackLayout.setCallBack(new SwipeBackLayout.CallBack() {
            @Override
            public void onFinish() {
                Log.e(getClass().getSimpleName(),"SwipeBack onFinish");
                finish();
            }
        });

        swipeBackLayout.setEnableMoveClose(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(getClass().getSimpleName(),"onDestroy");
    }
}
