package com.example.atsuto5.yahoo_rss_reader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Atsuto5 on 2017/02/12.
 */
public class WebViewActivity extends AppCompatActivity {

    String URL_KEY = "URL";
    String TAG ="WebViewActivity";
    WebView mYahooWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        mYahooWebView = (WebView)findViewById(R.id.yahooWebView);
        mYahooWebView.setWebViewClient(new WebViewClient(){
           @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
               return false;
           }
        });

        //WebViewのJavaScriptを有効にする。
        mYahooWebView.getSettings().setJavaScriptEnabled(true);
        Log.i(TAG, "getJavaScriptEnabled: " + mYahooWebView.getSettings().getJavaScriptEnabled());

        //MainActivityからのIntentを取得
        Intent webViewIntent = getIntent();
        mYahooWebView.loadUrl(webViewIntent.getStringExtra(URL_KEY));
    }

   //バックキー押下時、WebView内でページバックする
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //戻るページがある場合
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_BACK
                && mYahooWebView.canGoBack() == true) {
            mYahooWebView.goBack();
            return true;
        }
        //トップページの場合
        else {
            finish();
            return true;
        }
    }
}
