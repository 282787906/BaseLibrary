package com.lee.base.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lee.base.R;
import com.lee.base.adapter.ImAdapter;
import com.lee.base.core.utils.ToastUtil;
import com.lee.base.http.GetDefault;
import com.lee.base.http.GetJson;
import com.lee.base.http.RequestType;
import com.lee.base.http.ResponseCallback;
import com.lee.base.module.IM_Msg;
import com.lee.base.view.MyRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WeiIMActivity extends BaseActivity implements View.OnClickListener {

    private MyRecyclerView mMyRecyclerView;
    List<IM_Msg> mMsgList;
    private EditText mImEditEtStr;
    ImAdapter mImAdapter;
    private ImageView mImEditIvChangeMedia;
    private ImageView mImEditIvEmoji;
    private ImageView mImEditIvAdd;
    private Button mImEditButSend;
    private RelativeLayout mImTopRlBack;
    private TextView mImTopTvUserName;
    private RelativeLayout mImTopRlUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_im);
        mMsgList = new ArrayList<>();
        initView();

    }

    private void initView() {
        mMyRecyclerView = (MyRecyclerView) findViewById(R.id.im_mrv);

        mImEditEtStr = (EditText) findViewById(R.id.im_edit_et_Str);

        mImEditIvChangeMedia = (ImageView) findViewById(R.id.im_edit_iv_change_media);
        mImEditIvChangeMedia.setOnClickListener(this);
        mImEditIvEmoji = (ImageView) findViewById(R.id.im_edit_iv_emoji);
        mImEditIvEmoji.setOnClickListener(this);
        mImEditIvAdd = (ImageView) findViewById(R.id.im_edit_iv_add);
        mImEditIvAdd.setOnClickListener(this);
        mImEditButSend = (Button) findViewById(R.id.im_edit_but_send);
        mImEditButSend.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mMyRecyclerView.setLayoutManager(layoutManager);
        mMyRecyclerView.setHasFixedSize(true);
        mImAdapter = new ImAdapter(mContext, mMsgList, new ImAdapter.OnRecyclerViewListener() {
            @Override
            public void OnItemClick(int position) {

            }

            @Override
            public void OnHeadClick(int position) {

            }

            @Override
            public void OnItemLongClick(int position) {

            }
        });
        mMyRecyclerView.setAdapter(mImAdapter);
        mImEditEtStr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mImEditEtStr.getText().toString().trim().length() > 0) {
                    mImEditButSend.setVisibility(View.VISIBLE);
                    mImEditIvAdd.setVisibility(View.GONE);
                } else {
                    mImEditButSend.setVisibility(View.GONE);
                    mImEditIvAdd.setVisibility(View.VISIBLE);
                }
            }
        });
        mImTopRlBack = (RelativeLayout) findViewById(R.id.im_top_rl_back);
        mImTopRlBack.setOnClickListener(this);
        mImTopTvUserName = (TextView) findViewById(R.id.im_top_tv_userName);
        mImTopTvUserName.setOnClickListener(this);
        mImTopRlUserInfo = (RelativeLayout) findViewById(R.id.im_top_rl_userInfo);
        mImTopRlUserInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_edit_but_send:
                send();
                break;
            case R.id.im_edit_iv_emoji:
                addRedPacket(IM_Msg.FromType.SEND,0);
                break;
            case R.id.im_edit_iv_add:
                Toast.makeText(mContext,"add",Toast.LENGTH_SHORT).show();
                break;

            case R.id.im_top_rl_back:
                finish();
               break;
            case R.id.im_top_rl_userInfo:
                Toast.makeText(mContext,"userInfo",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void send() {
        // validate
        String str = mImEditEtStr.getText().toString().trim();
        mImEditEtStr.setText("");
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(this, "Str不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        addMsg(IM_Msg.FromType.SEND, str);
        sendToToring(str);
    }

    String apikey = "8becc540b3aee80561d92c44ec14a722";

    String turing = "http://apis.baidu.com/turing/turing/turing";

    private void sendToToring(String str) {

        final GetJson getJson = new GetDefault(mContext, RequestType.GET, turing);
        getJson.getRequest().addHeader("apikey", apikey);
        getJson.requestParams.put("key", "879a6cb3afb84dbf4fc84a1df2ab7319");
        getJson.requestParams.put("userid", "123");
        getJson.requestParams.put("info", str);
        getJson.submit(new ResponseCallback() {
            @Override
            public void onSuccess() {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject((String) getJson.getAnalysisResult());
                    addMsg(IM_Msg.FromType.RECV, jsonObject.getString("text"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(int errorCode, String errorMsg) {
                ToastUtil.showSingleToast(mContext, errorMsg, Toast.LENGTH_LONG);

            }
        });

    }

    private void addMsg(IM_Msg.FromType fromType, String str) {
        mMsgList.add(IM_Msg.newTextMsg(str, fromType));
        notifyAfterAdd();
    }
    private void addRedPacket(IM_Msg.FromType fromType, float amount) {
        mMsgList.add(IM_Msg.newRedPacket(  fromType,amount ));
        notifyAfterAdd();
    }
    private  void  notifyAfterAdd(){
        mImAdapter.setData(mMsgList);
        mImAdapter.notifyDataSetChanged();
        mMyRecyclerView.smoothScrollToPosition(mMsgList.size() - 1);
    }
}
