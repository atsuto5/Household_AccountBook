package com.example.atsuto5.yahoo_rss_reader;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainTopicsFragment = new MainTopicsFragment();

        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,mMainTopicsFragment);
        fragmentTransaction.commit();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("メイントピックス");
        mToolbar.setTitleMargin(140,0,0,0);
        setSupportActionBar(mToolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.maintopics_logo) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, MainTopicsFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("メイントピックス");
            mToolbar.setTitleMargin(140,0,0,0);
        } else if (id == R.id.international_logo) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, InternationalFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("国際");
            mToolbar.setTitleMargin(320,0,0,0);
        } else if (id == R.id.entertainment) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, EntertainmentFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("エンタメ");
            mToolbar.setTitleMargin(260,0,0,0);
        } else if (id == R.id.it_logo) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, ItFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("IT");
            mToolbar.setTitleMargin(340,0,0,0);
        } else if (id == R.id.local_logo) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, LocalFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("地域");
            mToolbar.setTitleMargin(320,0,0,0);
        } else if (id == R.id.domestic_logo){
            android.app.FragmentManager fragmentManager = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, DomesticFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("国内");
            mToolbar.setTitleMargin(320,0,0,0);
        } else if (id == R.id.economy_logo){
            android.app.FragmentManager fragmentManager = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, EconomyFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("経済");
            mToolbar.setTitleMargin(320,0,0,0);
        } else if (id == R.id.sports_logo){
            android.app.FragmentManager fragmentManager = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, SportsFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("スポーツ");
            mToolbar.setTitleMargin(260,0,0,0);
        } else if (id == R.id.science_logo){
            android.app.FragmentManager fragmentManager = getFragmentManager();
            android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, ScienceFragment.newInstance());
            fragmentTransaction.commit();
            mToolbar.setTitle("科学");
            mToolbar.setTitleMargin(320,0,0,0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
