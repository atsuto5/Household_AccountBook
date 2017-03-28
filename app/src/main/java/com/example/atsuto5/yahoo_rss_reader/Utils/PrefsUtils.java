package com.example.atsuto5.yahoo_rss_reader.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.LoginFilter;
import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Atsuto5 on 2017/03/15.
 */
public  class PrefsUtils {

    public static final String MAIN_TOPICS_KEY = "MAIN_TOPICS";
    public static final String INTERNATIONAL_KEY = "INTERNATIONAL";
    public static final String ENTERTAINMENT_KEY = "ENTERTAINMENT";
    public static final String IT_KEY = "IT";
    public static final String LOCAL_KEY = "LOCAL";
    public static final String DOMESTIC_KEY = "DOMESTIC";
    public static final String ECONOMY_KEY = "ECONOMY";
    public static final String SPORTS_KEY = "SPORTS";
    public static final String SCIENCE_KEY = "SCIENCE";

    private static double mainTopicsCount;
    private static double internationalCount;
    private static double entertainmentCount;
    private static double itCount;
    private static double localCount;
    private static double domesticCount;
    private static double economyCount;
    private static double sportsCount;
    private static double scienceCount;
    private static double totalCount;
    private static String TAG = "PrefsUtils";


    public static void storeCount(String TOPIC, Context context){

        SharedPreferences data = context.getSharedPreferences("SaveCount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        int count = data.getInt(TOPIC,0);
        count += 1 ;
        Log.i(TAG, "storeCount: " + count);
        editor.putInt(TOPIC,count);
        editor.apply();
    }


    private static double getTotalCount(SharedPreferences sp){

        mainTopicsCount = sp.getInt(MAIN_TOPICS_KEY, 0);
        internationalCount = sp.getInt(INTERNATIONAL_KEY, 0);
        entertainmentCount = sp.getInt(ENTERTAINMENT_KEY, 0);
        itCount = sp.getInt(IT_KEY, 0);
        localCount = sp.getInt(LOCAL_KEY, 0);
        domesticCount = sp.getInt(DOMESTIC_KEY, 0);
        economyCount = sp.getInt(ECONOMY_KEY, 0);
        sportsCount = sp.getInt(SPORTS_KEY, 0);
        scienceCount = sp.getInt(SCIENCE_KEY, 0);

        totalCount = mainTopicsCount + internationalCount + entertainmentCount
                + itCount + localCount + domesticCount + economyCount + sportsCount + scienceCount;
        Log.i("TEST", "testtest: " + totalCount);

        return totalCount;
    }


    public static double getMainTopicsRate(Context context){
        SharedPreferences data = context.getSharedPreferences("SaveCount", Context.MODE_PRIVATE);
        double totalCount = getTotalCount(data);
        if (totalCount != 0 && mainTopicsCount != 0) {
            double beforeCalc = mainTopicsCount / totalCount * 100;
            BigDecimal bd = new BigDecimal(String.valueOf(beforeCalc));
            double mainTopicsRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return mainTopicsRate;
        }else{
            return 0;
        }
    }

    public static double getInternationalRate(){
        if (totalCount != 0 && internationalCount != 0) {
            double beforeCalc = internationalCount / totalCount * 100;
            BigDecimal bd = new BigDecimal(String.valueOf(beforeCalc));
            double internationalRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return internationalRate;
        } else {
            return 0;
        }
    }

    public static double getEntertainmentRate(){
        if (totalCount != 0 && entertainmentCount != 0) {
        double beforeCalc  = entertainmentCount / totalCount * 100 ;
        BigDecimal bd = new BigDecimal(String.valueOf(beforeCalc));
        double entertainmentRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return entertainmentRate;
        } else {
            return 0;
        }
    }

    public static double getItRate(){
        if (totalCount != 0 && itCount != 0) {
        double beforeCalc  = itCount / totalCount * 100 ;
        BigDecimal bd = new BigDecimal(String.valueOf(beforeCalc));
        double itRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return itRate;
        } else {
            return 0;
        }
    }

    public static double getLocalRate(){
        if (totalCount != 0 && localCount != 0) {
        double beforeCalc  = localCount / totalCount * 100 ;
        BigDecimal bd = new BigDecimal(String.valueOf(beforeCalc));
        double localRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return localRate;
        } else {
            return 0;
        }
    }

    public static double getDomesticRate(){
        if (totalCount != 0 && domesticCount != 0) {
        double beforeCalc  = domesticCount / totalCount * 100 ;
        BigDecimal bd = new BigDecimal(String.valueOf(beforeCalc));
        double domesticRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return domesticRate;
        } else {
            return 0;
        }
    }

    public static double getEconomyRate(){
        if (totalCount != 0 && economyCount != 0) {
        double beforeCalc  = economyCount / totalCount * 100 ;
        BigDecimal bd = new BigDecimal(String.valueOf(beforeCalc));
        double economyRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return economyRate;
        } else {
            return 0;
        }
    }

    public static double getSportsRate(){
        if (totalCount != 0 && sportsCount != 0) {
        double beforeCalc  = sportsCount / totalCount * 100 ;
        BigDecimal bd = new BigDecimal(String.valueOf(beforeCalc));
        double sportsRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return sportsRate;
        } else {
            return 0;
        }
    }

    public static double getScienceRate(){
        if (totalCount != 0 && scienceCount != 0) {
        double beforeCalc  = scienceCount / totalCount * 100 ;
        BigDecimal bd = new BigDecimal(String.valueOf(beforeCalc));
        double scienceRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return scienceRate;
        } else {
            return 0;
        }
    }

    public static void initCount(Context context){
        Log.i(TAG, "initCount: " + "testest");
        SharedPreferences data = context.getSharedPreferences("SaveCount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.remove(MAIN_TOPICS_KEY);
        editor.remove(INTERNATIONAL_KEY);
        editor.remove(ENTERTAINMENT_KEY);
        editor.remove(IT_KEY);
        editor.remove(LOCAL_KEY);
        editor.remove(DOMESTIC_KEY);
        editor.remove(ECONOMY_KEY);
        editor.remove(SPORTS_KEY);
        editor.remove(SCIENCE_KEY);
        editor.apply();

    }
}
