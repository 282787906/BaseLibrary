package com.lee.base.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lee.base.R;
import com.lee.base.core.utils.ToastUtil;
import com.lee.base.view.jpushNotification.MyNotification;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JPush#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JPush extends BaseFragment implements View.OnClickListener {

    public JPush() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance() {
        if (fragment==null) {
            fragment = new JPush();
        }
        return fragment;
    }
    public static BaseFragment getInstance() {

        return fragment;
    }
    private TextView fragmentJpushTvRegId;
    private TextView fragmentJpushTvRecvMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jpush, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.fragment_jpush_but_SetTag).setOnClickListener(this);
        view.findViewById(R.id.fragment_jpush_but_SetAlias).setOnClickListener(this);
        view.findViewById(R.id.fragment_jpush_but_GetRegId).setOnClickListener(this);
        fragmentJpushTvRegId = (TextView) view.findViewById(R.id.fragment_jpush_tv_RegId);
        view.findViewById(R.id.fragment_jpush_but_ClearNoti).setOnClickListener(this);
        view.findViewById(R.id.fragment_jpush_but_CustomNoti).setOnClickListener(this);
        fragmentJpushTvRecvMsg= (TextView) view.findViewById(R.id.fragment_jpush_tv_RecvMsg);
    }

    private EditText getFragmentJpushEtTag(){
        return (EditText) getView().findViewById(R.id.fragment_jpush_et_Tag);
    }

    private EditText getFragmentJpushEtAlias(){
        return (EditText) getView().findViewById(R.id.fragment_jpush_et_Alias);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_jpush_but_SetTag:
                Set<String> tag =new HashSet<>();
                tag.add(getFragmentJpushEtTag().getText().toString());
                JPushInterface.setTags(getActivity(), tag, new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        if (i==0){
                            ToastUtil.showSingleToast(getActivity(),"setTag Success", Toast.LENGTH_LONG);
                        }
                    }
                });

                break;
            case R.id.fragment_jpush_but_SetAlias:
                JPushInterface.setAlias(getActivity(), getFragmentJpushEtAlias().getText().toString(), new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        if (i==0){
                            ToastUtil.showSingleToast(getActivity(),"setAlias Success", Toast.LENGTH_LONG);
                        }
                    }
                });
                break;
            case R.id.fragment_jpush_but_GetRegId:
             String regId=   JPushInterface.getRegistrationID(getActivity());
                fragmentJpushTvRegId.setText(regId);
                break;
            case R.id.fragment_jpush_but_ClearNoti:
                JPushInterface.clearAllNotifications(getActivity());
                break;
            case R.id.fragment_jpush_but_CustomNoti:
                JPushInterface.setPushNotificationBuilder(2,MyNotification.newInstance(getActivity()));

                break;
        }
    }

    public void recvMsg(Bundle bundle) {
        fragmentJpushTvRecvMsg.setText(printBundle(bundle));
    }
    // 打印所有的 intent extra 数据
    private   String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(getTag(), "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(getTag(), "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
