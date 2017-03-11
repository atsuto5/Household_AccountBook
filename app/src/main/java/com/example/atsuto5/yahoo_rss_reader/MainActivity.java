package com.example.atsuto5.yahoo_rss_reader;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.atsuto5.yahoo_rss_reader.TopicsFragment.DomesticFragment;
import com.example.atsuto5.yahoo_rss_reader.TopicsFragment.EconomyFragment;
import com.example.atsuto5.yahoo_rss_reader.TopicsFragment.EntertainmentFragment;
import com.example.atsuto5.yahoo_rss_reader.TopicsFragment.InternationalFragment;
import com.example.atsuto5.yahoo_rss_reader.TopicsFragment.ItFragment;
import com.example.atsuto5.yahoo_rss_reader.TopicsFragment.LocalFragment;
import com.example.atsuto5.yahoo_rss_reader.TopicsFragment.MainTopicsFragment;
import com.example.atsuto5.yahoo_rss_reader.TopicsFragment.ScienceFragment;
import com.example.atsuto5.yahoo_rss_reader.TopicsFragment.SportsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = "MainActivity";
    private MainTopicsFragment mMainTopicsFragment;
    private Toolbar mToolbar;
    private TextView mUserNameTextView;
    private boolean drawerFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainTopicsFragment = new MainTopicsFragment();

        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,mMainTopicsFragment);
        fragmentTransaction.commit();

        mToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        mToolbar.setTitle("メイントピックス");
        mToolbar.setTitleMargin(140,0,0,0);
        setSupportActionBar(mToolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                String username = data.getString("USERNAME", "User Name");

                if(drawerFlag) {
                    mUserNameTextView = (TextView) drawerView.findViewById(R.id.userName);
                    mUserNameTextView.setText(username);
                    drawerFlag = false;
                }
                }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

            showDialog(this);
            return true;

            } else {
            return super.onOptionsItemSelected(item);
            }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (id == R.id.maintopics_logo) {
            fragmentTransaction.replace(R.id.fragment_container, MainTopicsFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("メイントピックス");
            mToolbar.setTitleMargin(140,0,0,0);

        } else if (id == R.id.international_logo) {
            fragmentTransaction.replace(R.id.fragment_container, InternationalFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("国際");
            mToolbar.setTitleMargin(320,0,0,0);

        } else if (id == R.id.entertainment) {
            fragmentTransaction.replace(R.id.fragment_container, EntertainmentFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("エンタメ");
            mToolbar.setTitleMargin(260,0,0,0);

        } else if (id == R.id.it_logo) {
            fragmentTransaction.replace(R.id.fragment_container, ItFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("IT");
            mToolbar.setTitleMargin(340,0,0,0);

        } else if (id == R.id.local_logo) {
            fragmentTransaction.replace(R.id.fragment_container, LocalFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("地域");
            mToolbar.setTitleMargin(320,0,0,0);

        } else if (id == R.id.domestic_logo){
            fragmentTransaction.replace(R.id.fragment_container, DomesticFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("国内");
            mToolbar.setTitleMargin(320,0,0,0);

        } else if (id == R.id.economy_logo){
            fragmentTransaction.replace(R.id.fragment_container, EconomyFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("経済");
            mToolbar.setTitleMargin(320,0,0,0);

        } else if (id == R.id.sports_logo){
            fragmentTransaction.replace(R.id.fragment_container, SportsFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("スポーツ");
            mToolbar.setTitleMargin(260,0,0,0);

        } else if (id == R.id.science_logo){
            fragmentTransaction.replace(R.id.fragment_container, ScienceFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("科学");
            mToolbar.setTitleMargin(320,0,0,0);

        } else if (id == R.id.interestgraph_logo){
            fragmentTransaction.replace(R.id.fragment_container, InterestChartFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("あなたの傾向");
            mToolbar.setTitleMargin(200,0,0,0);

        } else if (id == R.id.your_like_logo){
            fragmentTransaction.replace(R.id.fragment_container, InterestChartFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("あなたの傾向");
            mToolbar.setTitleMargin(200,0,0,0);

        } else if ("License".equals(item.getTitle())){
            fragmentTransaction.replace(R.id.fragment_container, MainTopicsFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("メイントピックス");
            mToolbar.setTitleMargin(140,0,0,0);

        } else if ("バージョン".equals(item.getTitle())){
            fragmentTransaction.replace(R.id.fragment_container, MainTopicsFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("メイントピックス");
            mToolbar.setTitleMargin(140,0,0,0);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //バックキー押下時、WebView内でページバックする
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN
                && keyCode == KeyEvent.KEYCODE_BACK) {

            showDialog(this);
            return true;

        } else {
            return false;
        }
    }

    public void showDialog(Context context){

        new AlertDialog.Builder(context)
                .setTitle("確認")
                .setIcon(R.drawable.maintopics_logo)
                .setMessage("Yahoo!RSSアプリからログアウトしますか？")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("キャンセル", null)
                .show();
    }
}
