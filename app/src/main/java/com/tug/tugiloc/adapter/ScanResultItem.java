package com.tug.tugiloc.adapter;

public class ScanResultItem {
    private int mImageResource;
    private String mSSID;
    private String mBSSID;
    private String mRSS;

    public ScanResultItem(int imageResource, String ssid, String bssid, String rss){
        this.mImageResource = imageResource;
        this.mSSID = ssid;
        this.mBSSID = bssid;
        this.mRSS = rss;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmSSID() {
        return mSSID;
    }

    public String getmBSSID() {
        return mBSSID;
    }

    public String getmRSS() {
        return mRSS;
    }
}
