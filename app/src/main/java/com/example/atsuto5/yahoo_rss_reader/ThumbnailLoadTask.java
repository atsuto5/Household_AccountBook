package com.example.atsuto5.yahoo_rss_reader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Atsuto5 on 2017/03/12.
 */
public class ThumbnailLoadTask extends AsyncTask <ArrayList<ItemBeans>, Void, ArrayList<ItemBeans>> {

    private Document doc = null;
    private final String TAG = "ThumbnailLoadTask";
    private ListView mRssListView;
    private RssAdapter mRssAdapter;
    private Activity mActivity;
    private ProgressDialog mLoadingDialog;
    private boolean mDialogFlag;
    private SwipeRefreshLayout mRefreshLayout;

    public ThumbnailLoadTask(ListView listView, RssAdapter rssAdapter, Activity activity, SwipeRefreshLayout refreshLayout, Boolean dialogFlag, ProgressDialog progressDialog) {
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
                Log.i(TAG, "doInBackground: ");
                doc = Jsoup.connect(item.getUrl()).get();

                String imageUrl = "";
                Elements images = doc.select("meta[name=item-image]");
                if ( images != null) {
                    imageUrl = images.attr("content");
                }

                item.setThumbNailUrl(imageUrl);
                Log.i(TAG, "TEST: " + item.getThumbNailUrl());
                itemList.set(i,item);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return itemList;
    }

    @Override
    protected void onPostExecute(ArrayList<ItemBeans> itemList) {
        super.onPostExecute(itemList);

        Log.i(TAG, "onPostExecute: " + itemList.get(0).getUrl());
        Log.i(TAG, "onPostExecute: " + itemList.get(0).getThumbNailUrl());

        //[name=item-imag]と一致するURLにアクセスしimgを取得しBitmapで格納する。
        BitmapSetTask bitmapSetTask = new BitmapSetTask(mRssListView, mRssAdapter, mActivity, mRefreshLayout, mDialogFlag, mLoadingDialog);
        bitmapSetTask.execute(itemList);

    }
}
