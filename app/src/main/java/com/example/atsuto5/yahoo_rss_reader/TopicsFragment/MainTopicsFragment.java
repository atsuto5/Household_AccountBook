package com.example.atsuto5.yahoo_rss_reader.TopicsFragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.atsuto5.yahoo_rss_reader.ItemBeans;
import com.example.atsuto5.yahoo_rss_reader.MainActivity;
import com.example.atsuto5.yahoo_rss_reader.Utils.NetworkUtil;
import com.example.atsuto5.yahoo_rss_reader.Utils.PrefsUtils;
import com.example.atsuto5.yahoo_rss_reader.R;
import com.example.atsuto5.yahoo_rss_reader.RssAdapter;
import com.example.atsuto5.yahoo_rss_reader.BackgroundTask.RssAsyncTask;

/**
 * Created by Atsuto5 on 2017/02/18.
 */
public class MainTopicsFragment extends Fragment {

    private SwipeRefreshLayout mRefreshLayout;
    private RssAdapter mRssAdapter;
    private ListView mRssList;
    private MainActivity mMainActivity;
    private String url = "http://news.yahoo.co.jp/pickup/rss.xml";
    private final String TAG = "MainTopicFragment";


    public static MainTopicsFragment newInstance() {
        return new MainTopicsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.topics_layout, container, false);

        mMainActivity = (MainActivity) getActivity();
        mRssAdapter = new RssAdapter(mMainActivity, R.layout.rss_beans);
        mRssList = (ListView) root.findViewById(R.id.Rss_ListView);
        mRssList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Main Topicsのアクセスカウントを追加
                PrefsUtils.storeCount(PrefsUtils.MAIN_TOPICS_KEY, getActivity());

                //parentからListViewを取得
                ListView listView = (ListView) parent;
                ItemBeans item = (ItemBeans) listView.getItemAtPosition(position);

                //WebViewActivity起動
                Intent webViewIntent = new Intent();
                webViewIntent.setClassName(NetworkUtil.PACKAGE_NAME,NetworkUtil.WebViewActivity_NAME);
                webViewIntent.putExtra(NetworkUtil.URL_KEY, item.getUrl());
                Log.i(TAG, "onClick: " + item.getUrl());
                getActivity().startActivity(webViewIntent);
            }
        });

        //データ取得開始
        RssAsyncTask rssAsync = new RssAsyncTask(mRssList, mRssAdapter, mMainActivity, mRefreshLayout, true);
        rssAsync.execute(url);

        //下にフリックした際、更新処理を行う
        mRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        mRefreshLayout.setColorSchemeResources(R.color.red,R.color.blue,R.color.green,R.color.yellow);
        mRefreshLayout.setRefreshing(false);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RssAsyncTask rssAsync = new RssAsyncTask(mRssList, mRssAdapter, mMainActivity, mRefreshLayout, false);
                rssAsync.execute(url);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
