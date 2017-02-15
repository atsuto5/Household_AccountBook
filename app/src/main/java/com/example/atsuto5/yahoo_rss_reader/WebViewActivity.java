package com.example.atsuto5.yahoo_rss_reader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Atsuto5 on 2017/02/12.
 */
public class WebViewActivity extends AppCompatActivity {

    String URL_KEY = "URL";
    String TAG ="WebViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        WebView yahooWebView = (WebView)findViewById(R.id.yahooWebView);
        yahooWebView.setWebViewClient(new WebViewClient(){
           @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
               return false;
           }
        });

        //WebViewのJavaScriptを有効にする。
        yahooWebView.getSettings().setJavaScriptEnabled(true);
        Log.i(TAG, "getJavaScriptEnabled: " + yahooWebView.getSettings().getJavaScriptEnabled());

        //MainActivityからのIntentを取得
        Intent webViewIntent = getIntent();
        yahooWebView.loadUrl(webViewIntent.getStringExtra(URL_KEY));
    }
}
