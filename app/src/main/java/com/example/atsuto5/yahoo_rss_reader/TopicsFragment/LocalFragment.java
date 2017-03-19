package com.example.atsuto5.yahoo_rss_reader.TopicsFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.atsuto5.yahoo_rss_reader.MainActivity;
import com.example.atsuto5.yahoo_rss_reader.PrefsUtils;
import com.example.atsuto5.yahoo_rss_reader.R;
import com.example.atsuto5.yahoo_rss_reader.RssAdapter;
import com.example.atsuto5.yahoo_rss_reader.BackgroundTask.RssAsyncTask;

/**
 * Created by Atsuto5 on 2017/02/18.
 */
public class LocalFragment extends Fragment {
    private SwipeRefreshLayout mRefreshLayout;
    private RssAdapter mRssAdapter;
    private ListView mRssList;
    private MainActivity mMainActivity;
    private String url = "http://news.yahoo.co.jp/pickup/local/rss.xml";

    public static LocalFragment newInstance() {
        return new LocalFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.topics_layout, container, false);

        mMainActivity = (MainActivity) getActivity();
        mRssAdapter = new RssAdapter(mMainActivity, R.layout.rss_beans, PrefsUtils.LOCAL_KEY);
        mRssList = (ListView) root.findViewById(R.id.Rss_ListView);

        //データ取得開始
        RssAsyncTask rssAsync = new RssAsyncTask(mRssList, mRssAdapter, mMainActivity, mRefreshLayout, true);
        rssAsync.execute(url);

        //下にフリックした際、更新処理を行う
        mRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        mRefreshLayout.setColorSchemeResources(R.color.red,R.color.blue,R.color.green,R.color.yellow);
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
