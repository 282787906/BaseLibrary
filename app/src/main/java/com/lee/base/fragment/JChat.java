package com.lee.base.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lee.base.R;
import com.lee.base.core.utils.ToastUtil;
import com.lee.base.core.utils.WaitDialog;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by liqg
 * 2016/8/19 08:51
 * Note :
 */
public class JChat extends BaseFragment implements View.OnClickListener {

    private TextView tvRecvMsg;
    String userName;
    String pwd;
    String msg;
    String sengTo;
    private static JChat jchat;

    public static Fragment newInstance() {
        if (jchat == null) {
            jchat = new JChat();
        }
        return jchat;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册一个接受的广播
        JMessageClient.registerEventReceiver(this);
        JMessageClient.enterSingleConversation("liqg");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
        JMessageClient.deleteSingleConversation("liqg");
    }
    //接受消息的事件
    public void onEventMainThread(MessageEvent event) {
        Message msg = event.getMessage();
        switch (msg.getContentType()) {
            case text:
                // 处理文字消息

                tvRecvMsg.setText(tvRecvMsg.getText()+"\n\n"+
                        msg.getFromUser().getUserName()+ "  "+msg.getCreateTime()+"\n"+
                        ((TextContent)msg.getContent()).getText());
                break;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jchat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.fragment_jchat_but_Regist).setOnClickListener(this);
        view.findViewById(R.id.fragment_jchat_but_Login).setOnClickListener(this);
        view.findViewById(R.id.fragment_jchat_but_Logout).setOnClickListener(this);
        view.findViewById(R.id.fragment_jchat_but_Send).setOnClickListener(this);
        tvRecvMsg = (TextView) view.findViewById(R.id.fragment_jchat_tv_Msg);
    }

    private EditText getFragmentJchatEtUserName() {
        return (EditText) getView().findViewById(R.id.fragment_jchat_et_UserName);
    }

    private EditText getFragmentJchatEtPwd() {
        return (EditText) getView().findViewById(R.id.fragment_jchat_et_Pwd);
    }

    private EditText getFragmentJchatEtMsg() {
        return (EditText) getView().findViewById(R.id.fragment_jchat_et_Msg);
    }

    private EditText getFragmentJchatEtSendTo() {
        return (EditText) getView().findViewById(R.id.fragment_jchat_et_SendTo);
    }

    @Override
    public void onClick(View view) {
        if (!checkUser()) {
            return;
        }
        switch (view.getId()) {
            case R.id.fragment_jchat_but_Regist:
                WaitDialog.show(mContext);
                JMessageClient.register(userName, pwd, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        WaitDialog.hide();
                        if (i == 0) {
                            ToastUtil.showSingleToast(mContext, "Success", Toast.LENGTH_LONG);
                        } else {
                            ToastUtil.showSingleToast(mContext, "Fail:" + s, Toast.LENGTH_LONG);

                        }
                    }
                });
                break;
            case R.id.fragment_jchat_but_Login:
                WaitDialog.show(mContext);
                JMessageClient.login(userName, pwd, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        WaitDialog.hide();
                        if (i == 0) {
                            ToastUtil.showSingleToast(mContext, "Success", Toast.LENGTH_LONG);
                        } else {
                            ToastUtil.showSingleToast(mContext, "Fail:" + s, Toast.LENGTH_LONG);

                        }
                    }
                });
                break;
            case R.id.fragment_jchat_but_Logout:
                JMessageClient.logout();
                break;
            case R.id.fragment_jchat_but_Send:

                if (!checkSend()) {
                    return;
                }

                Message message = JMessageClient.createSingleTextMessage(sengTo, msg);
                JMessageClient.sendMessage(message);
                break;
        }
    }

    private boolean checkSend() {
        msg = getFragmentJchatEtMsg().getText().toString().trim();
        sengTo = getFragmentJchatEtSendTo().getText().toString().trim();
        if (msg.isEmpty()) {
            getFragmentJchatEtMsg().setError("Not Null");
            return false;
        } else {
            getFragmentJchatEtMsg().setError(null);

        }
        if (sengTo.isEmpty()) {
            getFragmentJchatEtSendTo().setError("Not Null");
            return false;
        } else {
            getFragmentJchatEtSendTo().setError(null);

        }
        return true;
    }

    private boolean checkUser() {
        userName = getFragmentJchatEtUserName().getText().toString().trim();
        pwd = getFragmentJchatEtPwd().getText().toString().trim();

        if (userName.isEmpty()) {
            getFragmentJchatEtUserName().setError("Not Null");
            return false;
        } else {
            getFragmentJchatEtUserName().setError(null);

        }
        if (pwd.isEmpty()) {
            getFragmentJchatEtPwd().setError("Not Null");
            return false;
        } else {
            getFragmentJchatEtPwd().setError(null);

        }
        return true;
    }

    public void onRecv(Object o) {

    }

}
