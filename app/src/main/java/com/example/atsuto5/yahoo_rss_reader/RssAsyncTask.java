package com.example.atsuto5.yahoo_rss_reader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.Xml;
import android.widget.ListView;
import android.widget.Toast;

import com.example.atsuto5.yahoo_rss_reader.MainActivity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by Atsuto5 on 2017/02/11.
 */
public class RssAsyncTask extends AsyncTask<Void, Integer, ArrayList> {

    private ListView mRssListView;
    private RssAdapter mRssAdapter;
    private static final String TAG = "RssAsyncTask";
    private Activity mActivity;
    private ProgressDialog mLoadingDialog;
    private boolean mDialogFlag;
    private SwipeRefreshLayout mRefreshLayout;
    private static final int HTTP_RESPONSE_OK = 200;

    public RssAsyncTask(ListView listView, RssAdapter rssAdapter, Activity activity, SwipeRefreshLayout refreshLayout, Boolean dialogFlag) {
        this.mRssListView = listView;
        this.mRssAdapter = rssAdapter;
        this.mActivity = activity;
        this.mDialogFlag = dialogFlag;
        this.mRefreshLayout = refreshLayout;

        }
    @Override
    protected void onPreExecute(){
        //アダプターをリセットする。
        mRssAdapter.clear();

        if(mDialogFlag) {
            mLoadingDialog = new ProgressDialog(mActivity);
            mLoadingDialog.setMessage("ロード中です...");
            mLoadingDialog.show();
        }
    }


    @Override
    protected ArrayList doInBackground(Void... arg0) {

        String url = "http://news.yahoo.co.jp/pickup/rss.xml";
        ArrayList<ItemBeans> itemList = new ArrayList<>();

        DefaultHttpClient client = new DefaultHttpClient();
        HttpUriRequest req = new HttpGet(url);
        HttpResponse res = null;

        try {

            res = client.execute(req);
            if (HTTP_RESPONSE_OK == res.getStatusLine().getStatusCode()){
                mRssAdapter.setNotifyOnChange(false);
                //レンダリングはせずに、オブジェクトを破棄する
                mRssAdapter.clear();
                mRssAdapter.setNotifyOnChange(true);
            }


            Log.i(TAG, "doInBackground res = : " + res.getStatusLine().getStatusCode());

            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(res.getEntity().getContent(),"UTF-8");


            ItemBeans item = null;

            for(int e = xmlPullParser.getEventType(); e != XmlPullParser.END_DOCUMENT; e = xmlPullParser.next()){

                Log.i(TAG, "doInBackground: xmlPullParser.getName()" + xmlPullParser.getName());


                if (e == XmlPullParser.START_TAG) {
                    if (xmlPullParser.getName().equals("item")) {
                        item = new ItemBeans();
                        }
                    if (xmlPullParser.getName().equals("title")) {
                        if (item != null) item.setTitle(xmlPullParser.nextText());
                        }

                    if (xmlPullParser.getName().equals("link")) {
                        if (item != null) item.setUrl(xmlPullParser.nextText());
                        }
                    }if (e == XmlPullParser.END_TAG && xmlPullParser.getName().equals("item")) {
                    itemList.add(item);
                    }
                }

            } catch (Exception e) {
            e.printStackTrace();
        }
        return itemList;
        }

    @Override
    protected void onPostExecute(ArrayList itemList) {

        for(int i = 0; itemList.size()>i;i++){
            mRssAdapter.add((ItemBeans) itemList.get(i));
        }

        if(mDialogFlag) {
            //ダイアログを消去
            mLoadingDialog.dismiss();
            mRssListView.setAdapter(mRssAdapter);
        }else{
            //下スワイプのインジケータをストップ
            mRefreshLayout.setRefreshing(false);
            Toast.makeText(mActivity, "更新しました。", Toast.LENGTH_SHORT).show();
            }
        }
    }