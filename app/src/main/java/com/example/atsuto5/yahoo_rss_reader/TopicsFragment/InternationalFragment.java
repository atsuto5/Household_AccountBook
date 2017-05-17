package com.example.atsuto5.yahoo_rss_reader.TopicsFragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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

import org.chromium.customtabsclient.shared.CustomTabsHelper;

/**
 * Created by Atsuto5 on 2017/02/18.
 */
public class InternationalFragment extends Fragment {
    private SwipeRefreshLayout mRefreshLayout;
    private RssAdapter mRssAdapter;
    private ListView mRssList;
    private MainActivity mMainActivity;
    private String url = "http://news.yahoo.co.jp/pickup/world/rss.xml";

    public static InternationalFragment newInstance() {
        return new InternationalFragment();
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
                PrefsUtils.storeCount(PrefsUtils.INTERNATIONAL_KEY, getActivity());

                //parentからListViewを取得
                ListView listView = (ListView) parent;
                ItemBeans item = (ItemBeans) listView.getItemAtPosition(position);

                //ChromeCustomTabの起動処理開始
                final CustomTabsIntent tabsIntent = new CustomTabsIntent.Builder()
                        .setShowTitle(true)
                        .setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary))
                        .build();
                String packageName = CustomTabsHelper.getPackageNameToUse(mMainActivity);
                tabsIntent.intent.setPackage(packageName);
                // Chromeの起動
                tabsIntent.launchUrl(getActivity(), Uri.parse(item.getUrl()));

                //WebViewActivity起動
//                Intent webViewIntent = new Intent();
//                webViewIntent.setClassName(NetworkUtil.PACKAGE_NAME,NetworkUtil.WebViewActivity_NAME);
//                webViewIntent.putExtra(NetworkUtil.URL_KEY, item.getUrl());
//                getActivity().startActivity(webViewIntent);
            }
        });


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
