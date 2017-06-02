package com.lee.base;

import android.content.Intent;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lee.base.activity.SplashActivity;
import com.lee.base.business.Constant;
import com.lee.base.core.BaseApplication;
import com.lee.base.core.business.AppManager;
import com.lee.base.core.log.LogHelper;
import com.lee.base.core.log.MyLog;
import com.lee.base.core.utils.DialogUtil;
import com.umeng.analytics.MobclickAgent;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;


/**
 * Created by liqg
 * 2016/7/8 10:55
 * Note :
 */
public class MyApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.setDebugMode(true);
        NoHttp.initialize(this);
        Logger.setDebug(true);
        Logger.setTag("BaseLibrary_NoHttp");
        LogHelper.init(context, null, null, true);
        Fresco.initialize(context);
        JMessageClient.init(context);
        JMessageClient.setDebugMode(true);

        //设置Notification的模式
        JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_NO_NOTIFICATION);
        //注册Notification点击的接收器
        //       new NotificationClickEventReceiver(getApplicationContext());

        JMessageClient.registerEventReceiver(this);

        Log.e(tag, Constant.getDeviceInfo(this));
    }

    //接受消息的事件
    public void onEventMainThread(MessageEvent event) {
        Message msg = event.getMessage();
        switch (msg.getContentType()) {
            case text:

                List<Conversation> conversations = JMessageClient.getConversationList();
                Conversation conversationliqg = JMessageClient.getSingleConversation("liqg");
                List<Message> messages = conversationliqg.getAllMessage();
                //                conversationliqg.deleteAllMessage();
                MyLog.d(tag, "weidu    " + conversationliqg.getUnReadMsgCnt());
                for (Message message : messages) {
                    MyLog.d(tag, message.getFromUser().getUserName() + "   " +
                            message.getCreateTime() + "    " +
                            message.getFromType() + "    " +
                            message.getContentType() + "    " +
                            message.getContent().getBooleanExtra("isReaded") + "    " +
                            message.getStatus() + "    " +
                            ((TextContent) message.getContent()).getText());
                }
                // 处理文字消息

                //                tvRecvMsg.setText(tvRecvMsg.getText()+"\n\n"+
                //                        msg.getFromUser().getUserName()+ "  "+msg.getCreateTime()+"\n"+
                //                        ((TextContent)msg.getContent()).getText());
                break;
        }
    }

    @Override
    protected void onUnCatchEclipcation(Thread thread, final Throwable throwable) {
        MobclickAgent.reportError(AppManager.getAppManager().currentActivity(),throwable );
        DialogUtil.showOkCancel(AppManager.getAppManager().currentActivity(), throwable.getLocalizedMessage(), "确定", null, new DialogUtil.OkCancelDialogListener() {
            @Override
            public void onOkClickListener() {
                AppManager.getAppManager().AppExit();
            }

            @Override
            public void onCancelClickListener() {

                AppManager.getAppManager().AppExit();
            }
        });
    }

    @Override
    protected BaseApplication initApplication() {
        pauseToReloadTime = 150000;
        return this;
    }

    /**
     * 应用Resume时间，应用从后台回前端是调用
     */
    @Override
    protected void onApplicationResume() {
        if (pauseTime > 0 && System.currentTimeMillis() - pauseTime > pauseToReloadTime) {

            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
