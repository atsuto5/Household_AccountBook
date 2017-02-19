package com.example.atsuto5.yahoo_rss_reader;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Atsuto5 on 2017/02/19.
 */
public class InterestChartFragment extends Fragment {

    private PieChart mPieChart;

    public static InterestChartFragment newInstance() {
        return new InterestChartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.your_chart, container, false);
        mPieChart = (PieChart) root.findViewById(R.id.your_interest_chart);

        createPieChart();

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void createPieChart() {
        //PieChart pieChart = (PieChart) mRootView.findViewById(R.id.your_interest_chart);

        mPieChart.setDrawHoleEnabled(true); // 真ん中に穴を空けるかどうか
        mPieChart.setHoleRadius(50f);       // 真ん中の穴の大きさ(%指定)
        mPieChart.setHoleColorTransparent(true);
        mPieChart.setTransparentCircleRadius(55f);
        mPieChart.setRotationAngle(270);          // 開始位置の調整
        mPieChart.setRotationEnabled(true);       // 回転可能かどうか
        mPieChart.getLegend().setEnabled(true);   //
        mPieChart.setDescription("あなたの興味がある分野");
        mPieChart.setData(createPieChartData());

        // 更新
        mPieChart.invalidate();
        // アニメーション
        mPieChart.animateXY(2000, 2000); // 表示アニメーション
    }

    // pieChartのデータ設定
    private PieData createPieChartData() {
        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        xVals.add("メイントピックス");
        xVals.add("国際");
        xVals.add("エンタメ");
        xVals.add("IT");
        xVals.add("地域");
        xVals.add("国内");
        xVals.add("経済");
        xVals.add("スポーツ");
        xVals.add("科学");

        yVals.add(new Entry(20, 0));
        yVals.add(new Entry(10, 1));
        yVals.add(new Entry(10, 2));
        yVals.add(new Entry(10, 3));
        yVals.add(new Entry(10, 4));
        yVals.add(new Entry(10, 5));
        yVals.add(new Entry(10, 6));
        yVals.add(new Entry(10, 7));
        yVals.add(new Entry(10, 8));

        PieDataSet dataSet = new PieDataSet(yVals, "トピック");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(1f);

        // 色の設定
        colors.add(ColorTemplate.COLORFUL_COLORS[0]);
        colors.add(ColorTemplate.COLORFUL_COLORS[1]);
        colors.add(ColorTemplate.COLORFUL_COLORS[2]);
        colors.add(ColorTemplate.COLORFUL_COLORS[3]);
        colors.add(ColorTemplate.COLORFUL_COLORS[4]);
        colors.add(ColorTemplate.JOYFUL_COLORS[0]);
        colors.add(ColorTemplate.JOYFUL_COLORS[1]);
        colors.add(ColorTemplate.JOYFUL_COLORS[4]);
        colors.add(ColorTemplate.JOYFUL_COLORS[3]);
        dataSet.setColors(colors);
        dataSet.setDrawValues(true);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());

        // テキストの設定
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.WHITE);
        return data;
    }
}
