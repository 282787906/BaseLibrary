package com.lee.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import a.a.a.a.a.c;
import cn.jpush.android.util.aa;
import cn.jpush.android.util.s;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.c.a.b;
import cn.jpush.im.android.helpers.e;
import cn.jpush.im.android.helpers.f;

/**
 * Created by liqg
 * 2016/8/22 09:19
 * Note :
 */
public class MyIMReceiver extends BroadcastReceiver {
    public MyIMReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        JMessageClient.init(context);
        String action;
        if((action = intent.getAction()) == null) {
            s.b();
        } else {
            s.b();
            if(action.equals("cn.jpush.im.android.action.IM_RESPONSE")) {
                action = intent.getStringExtra("push_to_im_data");
                String push_type = intent.getStringExtra("push_type");
                if(aa.a(action)) {
                    if(!aa.a(push_type)) {
                        Bundle extras;
                        if("push_login".equals(push_type)) {
                            extras = intent.getExtras();
                            s.a();
                            b.a(context, extras);
                        }

                        if("push_logout".equals(push_type)) {
                            extras = intent.getExtras();
                            s.a();
                            b.b(context, extras);
                            return;
                        }
                    } else {
                        byte[] im_response = intent.getByteArrayExtra("im_response");
                        f.a(context, im_response);
                    }


                } else {
                    s.b();
//                    IMReceiver.PendingResult pendingResult = (IMReceiver.PendingResult) i.a(action, new a.a.a.a.c.a() {
//                    });
//                    if(0L != pendingResult.a && 0L != pendingResult.b) {
//                        cn.jpush.im.android.a.b(pendingResult.a);
//                        cn.jpush.im.android.a.c(pendingResult.b);
//                    } else {
//                        Map var7;
//                        if((var7 = i.b(action)) != null && null != var7.get("push_network_connected")) {
//                            cn.jpush.im.android.a.a(((Boolean)var7.get("push_network_connected")).booleanValue());
//                        } else {
//                            s.d("IMReceiver", "format extra data error for some reason. map = " + var7);
//                        }
//                    }

                    e.b(context, JMessageClient.getSdkVersionString(), cn.jpush.im.android.a.a());
                }
            } else if(action.equals("cn.jpush.im.android.action.NOTIFICATION_CLICK_PROXY")) {
                f.a(context, intent);
            } else {
                s.d("IMReceiver", "unhandled action! abort it.");
            }
        }
    }

    class a {
        @c(
                a = "push_login_local_time"
        )
        public long a;
        @c(
                a = "push_login_server_time"
        )
        public long b;
    }
}

