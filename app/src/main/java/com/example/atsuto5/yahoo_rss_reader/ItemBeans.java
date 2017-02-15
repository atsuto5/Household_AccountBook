package com.example.atsuto5.yahoo_rss_reader;

/**
 * Created by Atsuto5 on 2017/02/11.
 */
public class ItemBeans {
    private String mTitle;
    private String mUrl;


    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setUrl(String summary) {
        this.mUrl = summary;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getUrl() {
        return this.mUrl;
        }

}
