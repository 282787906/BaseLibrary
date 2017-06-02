package com.lee.base.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lee.base.R;
import com.lee.base.core.utils.DialogUtil;
import com.lee.base.core.utils.ResUtil;

public class DialogActivity extends BaseActivity implements View.OnClickListener {

    private Button mActivityDialogBigData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        initView();
    }

    private void initView() {
        mActivityDialogBigData = (Button) findViewById(R.id.activity_dialog_BigData);

        mActivityDialogBigData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_dialog_BigData:
                DialogUtil.showOkCancel(mContext, ResUtil.getString(mContext,R.string.tips),"OK",null, new DialogUtil.OkCancelDialogListener() {
                    @Override
                    public void onOkClickListener() {

                    }

                    @Override
                    public void onCancelClickListener() {

                    }
                });
                break;
        }
    }
}
