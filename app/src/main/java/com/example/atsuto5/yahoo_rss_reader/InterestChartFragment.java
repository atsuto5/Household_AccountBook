package com.example.atsuto5.yahoo_rss_reader;

import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by Atsuto5 on 2017/02/19.
 */
public class InterestChartFragment extends Fragment {

    private PieChart mPieChart;
    private FloatingActionButton mDeleteFab;
    private final String TAG = "InterestCF";

    public static InterestChartFragment newInstance() {
        return new InterestChartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.your_chart, container, false);
        mDeleteFab = (FloatingActionButton) root.findViewById(R.id.deleteFab);
        mDeleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("アクセスカウントの初期化")
                        .setIcon(R.drawable.garbage)
                        .setMessage("アクセスカウントを初期化しますか？")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //初期化処理
                                PrefsUtils.initCount(getActivity());
                                Toast.makeText(getActivity()," 初期化しました! ", Toast.LENGTH_LONG).show();
                                createPieChart();
                            }
                        })
                        .setNegativeButton("キャンセル", null)
                        .show();
            }
        });

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

        yVals.add(new Entry((float) PrefsUtils.getMainTopicsRate(getActivity()), 0));
        yVals.add(new Entry((float) PrefsUtils.getInternationalRate(), 1));
        yVals.add(new Entry((float) PrefsUtils.getEntertainmentRate(), 2));
        yVals.add(new Entry((float) PrefsUtils.getItRate(), 3));
        yVals.add(new Entry((float) PrefsUtils.getLocalRate(), 4));
        yVals.add(new Entry((float) PrefsUtils.getDomesticRate(), 5));
        yVals.add(new Entry((float) PrefsUtils.getEconomyRate(), 6));
        yVals.add(new Entry((float) PrefsUtils.getSportsRate(), 7));
        yVals.add(new Entry((float) PrefsUtils.getScienceRate(), 8));

        PieDataSet dataSet = new PieDataSet(yVals, "トピックス");
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
