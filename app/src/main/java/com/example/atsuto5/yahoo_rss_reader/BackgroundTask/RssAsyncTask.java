package com.example.atsuto5.yahoo_rss_reader.BackgroundTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.Xml;
import android.widget.ListView;
import android.widget.Toast;

import com.example.atsuto5.yahoo_rss_reader.BackgroundTask.ThumbnailLoadTask;
import com.example.atsuto5.yahoo_rss_reader.ItemBeans;
import com.example.atsuto5.yahoo_rss_reader.RssAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.nodes.Document;
import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by Atsuto5 on 2017/02/11.
 */
public class RssAsyncTask extends AsyncTask<String, Integer, ArrayList> {

    private ListView mRssListView;
    private RssAdapter mRssAdapter;
    private static final String TAG = "RssAsyncTask";
    private Activity mActivity;
    private ProgressDialog mLoadingDialog;
    private boolean mDialogFlag;
    private SwipeRefreshLayout mRefreshLayout;
    private static final int HTTP_RESPONSE_OK = 200;

    String pageUrl = "";
    Document doc = null;

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
        mLoadingDialog = new ProgressDialog(mActivity);

        if(mDialogFlag) {
            mLoadingDialog.setMessage("ロード中です...");

        } else {
            mRefreshLayout.setRefreshing(false);
            mLoadingDialog.setMessage("更新中です...");

        }
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.show();
    }


    @Override
    protected ArrayList doInBackground(String... arg0) {

        String[] url = arg0;
        ArrayList<ItemBeans> itemList = new ArrayList<>();

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpUriRequest httpRequest = new HttpGet(url[0]);
        HttpResponse httpResponse = null;

        try {

            httpResponse = httpClient.execute(httpRequest);
            if (HTTP_RESPONSE_OK == httpResponse.getStatusLine().getStatusCode()){
                mRssAdapter.setNotifyOnChange(false);
                //レンダリングはせずに、オブジェクトを破棄する
                mRssAdapter.clear();
                mRssAdapter.setNotifyOnChange(true);
            }

            Log.i(TAG, "doInBackground res = : " + httpResponse.getStatusLine().getStatusCode());

            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(httpResponse.getEntity().getContent(),"UTF-8");

            ItemBeans item = null;

            for(int e = xmlPullParser.getEventType(); e != XmlPullParser.END_DOCUMENT; e = xmlPullParser.next()){

                if (e == XmlPullParser.START_TAG) {
                    if (xmlPullParser.getName().equals("item")) {
                        item = new ItemBeans();
                        }
                    if (xmlPullParser.getName().equals("title")) {
                        if (item != null) item.setTitle(xmlPullParser.nextText());
                        }

                    if (xmlPullParser.getName().equals("link")) {
                        if (item != null) {
                            pageUrl = xmlPullParser.nextText();
                            item.setUrl(pageUrl);
                            }
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

        mRssListView.setAdapter(mRssAdapter);

        if(!mDialogFlag) Toast.makeText(mActivity, "更新しました。", Toast.LENGTH_SHORT).show();

        mLoadingDialog.dismiss();

        //RSSで取得したURLを元にHTMLを取得しに行く。
        ThumbnailLoadTask thumbGetTask = new ThumbnailLoadTask(mRssListView, mRssAdapter, mActivity, mRefreshLayout, mDialogFlag, mLoadingDialog);
        thumbGetTask.execute(itemList);

        }
}