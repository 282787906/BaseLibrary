package com.lee.base.module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liqg
 * 2015/10/23
 */
public class Module implements Parcelable {


    private String name;

    private String userRole;
    private String icon;
    private String openWith;
    private String bgColor;
    private String url;
    private String keyId;
    private String type;
    private boolean hasNews;

    public boolean isHasNews() {
        return hasNews;
    }

    public void setHasNews(boolean hasNews) {
        this.hasNews = hasNews;
    }

    public int getNewTag() {
        return newTag;
    }

    public void setNewTag(int newTag) {
        this.newTag = newTag;
    }

    private int newTag;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return this.userRole;
    }

    public void setOpenWith(String openWith) {
        this.openWith = openWith;
    }

    public String getOpenWith() {
        return this.openWith;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.userRole);
        dest.writeString(this.icon);
        dest.writeString(this.openWith);
        dest.writeString(this.bgColor);
        dest.writeString(this.url);
        dest.writeString(this.keyId);
        dest.writeString(this.type);
        dest.writeByte(hasNews ? (byte) 1 : (byte) 0);
        dest.writeInt(this.newTag);
    }

    public Module() {
    }

    protected Module(Parcel in) {
        this.name = in.readString();
        this.userRole = in.readString();
        this.icon = in.readString();
        this.openWith = in.readString();
        this.bgColor = in.readString();
        this.url = in.readString();
        this.keyId = in.readString();
        this.type = in.readString();
        this.hasNews = in.readByte() != 0;
        this.newTag = in.readInt();
    }

    public static final Parcelable.Creator<Module> CREATOR = new Parcelable.Creator<Module>() {
        public Module createFromParcel(Parcel source) {
            return new Module(source);
        }

        public Module[] newArray(int size) {
            return new Module[size];
        }
    };
}
