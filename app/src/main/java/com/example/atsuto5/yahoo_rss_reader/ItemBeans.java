package com.example.atsuto5.yahoo_rss_reader;

import android.graphics.Bitmap;

/**
 * Created by Atsuto5 on 2017/02/11.
 */
public class ItemBeans {
    private String mTitle;
    private String mUrl;
    private String mThumbNailUrl;
    private Bitmap mThumbNail;
    //private boolean mLikeFlag = true;


    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public void setThumbNailUrl(String thumbNailUrl) {
        this.mThumbNailUrl = thumbNailUrl;
    }

    public void setThumbNail(Bitmap thumbNail){
        this.mThumbNail = thumbNail;
    }

//    public void setLikeFlag(boolean flag) {
//        this.mLikeFlag = flag;
//    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getUrl() {
        return this.mUrl;
        }

    public String getThumbNailUrl() {
        return this.mThumbNailUrl;
    }

    public Bitmap getThumNail(){
        return this.mThumbNail;
    }

//    public boolean getLikeflag() {
//        return this.mLikeFlag;
//    }
}
