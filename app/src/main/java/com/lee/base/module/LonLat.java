package com.lee.base.module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liqg
 * 2016/7/22 09:38
 * Note :
 */
public class LonLat implements Parcelable {

    /**
     * lon : 125.32354
     * level : 2
     * address :
     * cityName :
     * alevel : 4
     * lat : 43.81707
     */

    private double lon;
    private int level;
    private String address;
    private String cityName;
    private int alevel;
    private double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getAlevel() {
        return alevel;
    }

    public void setAlevel(int alevel) {
        this.alevel = alevel;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.lon);
        dest.writeInt(this.level);
        dest.writeString(this.address);
        dest.writeString(this.cityName);
        dest.writeInt(this.alevel);
        dest.writeDouble(this.lat);
    }

    public LonLat() {
    }

    protected LonLat(Parcel in) {
        this.lon = in.readDouble();
        this.level = in.readInt();
        this.address = in.readString();
        this.cityName = in.readString();
        this.alevel = in.readInt();
        this.lat = in.readDouble();
    }

    public static final Parcelable.Creator<LonLat> CREATOR = new Parcelable.Creator<LonLat>() {
        @Override
        public LonLat createFromParcel(Parcel source) {
            return new LonLat(source);
        }

        @Override
        public LonLat[] newArray(int size) {
            return new LonLat[size];
        }
    };
}
