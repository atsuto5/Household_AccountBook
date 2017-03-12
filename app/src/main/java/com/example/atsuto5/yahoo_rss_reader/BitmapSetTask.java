package com.example.atsuto5.yahoo_rss_reader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Atsuto5 on 2017/03/12.
 */
public class BitmapSetTask extends AsyncTask <ArrayList<ItemBeans>, Void, ArrayList<ItemBeans>> {

    private Bitmap bit = null;
    private final String TAG = "BitmapSetTask";
    private ListView mRssListView;
    private RssAdapter mRssAdapter;
    private Activity mActivity;
    private ProgressDialog mLoadingDialog;
    private boolean mDialogFlag;
    private SwipeRefreshLayout mRefreshLayout;

    public BitmapSetTask(ListView listView, RssAdapter rssAdapter, Activity activity, SwipeRefreshLayout refreshLayout, Boolean dialogFlag, ProgressDialog progressDialog) {
        this.mRssListView = listView;
        this.mRssAdapter = rssAdapter;
        this.mActivity = activity;
        this.mDialogFlag = dialogFlag;
        this.mRefreshLayout = refreshLayout;
        this.mLoadingDialog = progressDialog;
    }



    @Override
    protected ArrayList<ItemBeans> doInBackground(ArrayList<ItemBeans>... params) {

        ArrayList<ItemBeans> itemList = params[0];

        for(int i = 0; itemList.size()>i;i++) {
            ItemBeans item = (ItemBeans) itemList.get(i);
            Log.i(TAG, "TEST: " + item.getUrl());

            try {
                URL url = new URL(item.getThumbNailUrl());
                Log.i(TAG, "doInBackground: " + item.getThumbNailUrl());
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                InputStream in = con.getInputStream();
                bit = BitmapFactory.decodeStream(in);
                in.close();

                item.setThumbNail(bit);
                itemList.set(i,item);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return itemList;
    }

    @Override
    protected void onPostExecute(ArrayList<ItemBeans> itemList) {
        super.onPostExecute(itemList);

        for(int i = 0; itemList.size()>i;i++){
            mRssAdapter.add((ItemBeans) itemList.get(i));
        }

        mRssListView.setAdapter(mRssAdapter);

        if(mDialogFlag) {
            //ダイアログを消去
            mLoadingDialog.dismiss();
        }else{
            //下スワイプのインジケータをストップ
            mRefreshLayout.setRefreshing(false);
            Toast.makeText(mActivity, "更新しました。", Toast.LENGTH_SHORT).show();
        }
    }
}
