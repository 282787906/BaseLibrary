package com.lee.base.module;

import com.lee.base.core.utils.DateUtil;

/**
 * Created by liqg
 * 2016/11/9 10:03
 * Note :
 */
public class IM_Msg {

    FromType mFromType;
    String msg;
    String date;
    MsgType msgType;



    public enum MsgType{
        TEXT,IMG,RED_PACKET
    }
    public enum FromType {
        SEND,RECV
    }
    public static IM_Msg newTextMsg(String str,FromType fromType) {
        IM_Msg im_msg=new IM_Msg();
        im_msg.  setDate(DateUtil.getCurrentDateTime("MM月dd日 HH:mm"));
        im_msg.setFromType(fromType);
        im_msg. setMsg(str);
        im_msg .setMsgType(MsgType.TEXT);
        return  im_msg;
    }
    public static IM_Msg newRedPacket(FromType fromType, float amount) {
        IM_Msg im_msg=new IM_Msg();
        im_msg.  setDate(DateUtil.getCurrentDateTime("MM月dd日 HH:mm"));
        im_msg.setFromType(fromType);
        im_msg .setMsgType(MsgType.RED_PACKET);
        return  im_msg;
    }
    public FromType getFromType() {
        return mFromType;
    }

    public void setFromType(FromType fromType) {
        this.mFromType = fromType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

}
