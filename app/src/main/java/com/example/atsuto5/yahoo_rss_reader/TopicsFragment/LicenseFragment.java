package com.example.atsuto5.yahoo_rss_reader.TopicsFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.atsuto5.yahoo_rss_reader.R;

/**
 * Created by Atsuto5 on 2017/03/19.
 */
public class LicenseFragment extends Fragment {

    public static LicenseFragment newInstance() {
        return new LicenseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.license_layout ,container, false);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
