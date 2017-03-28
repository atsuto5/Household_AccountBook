package com.example.atsuto5.yahoo_rss_reader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Atsuto5 on 2017/02/11.
 */
public class RssAdapter extends ArrayAdapter<ItemBeans> {
    private LayoutInflater mInflater;
    private ArrayList<ItemBeans> itemList = new ArrayList<>();
    private String TAG = "RssAdapter";
    private Context mContext;
    //private String TOPIC_NAME;

    static class ViewHolder{
        TextView titleText;
        TextView subText;
        ImageView thumbNailView;
        ImageView userLikeView;
    }


    public RssAdapter(Context context, int id) {
        super(context, id);
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //TOPIC_NAME = topicName;
        }


    public View getView(final int position, View view, final ViewGroup parent){

        final ViewHolder holder;

        if(view == null) {
            view = mInflater.inflate(R.layout.rss_beans, null);
            //ViewHolderを作成
            holder = new ViewHolder();
            holder.titleText = (TextView) view.findViewById(R.id.titleTextView);
            holder.subText = (TextView) view.findViewById(R.id.subTextView);
            holder.thumbNailView = (ImageView) view.findViewById(R.id.thumbNailView);
            holder.userLikeView = (ImageView) view.findViewById(R.id.userLikeView);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }

            final ItemBeans item = this.getItem(position);

            if(item != null){

                holder.titleText.setText(item.getTitle());
                holder.subText.setText(item.getUrl());
//                holder.urlButton.setOnClickListener(new View.OnClickListener()  {
//                    //URLをタップしたときWebViewActivityに遷移する
//                    public void onClick(View v) {
//
//                        switch (TOPIC_NAME) {
//
//                            case PrefsUtils.MAIN_TOPICS_KEY :
//                                PrefsUtils.storeCount(PrefsUtils.MAIN_TOPICS_KEY, mContext);
//                                break;
//
//                            case PrefsUtils.INTERNATIONAL_KEY :
//                                PrefsUtils.storeCount(PrefsUtils.INTERNATIONAL_KEY, mContext);
//                                break;
//
//                            case PrefsUtils.ENTERTAINMENT_KEY :
//                                PrefsUtils.storeCount(PrefsUtils.ENTERTAINMENT_KEY, mContext);
//                                break;
//
//                            case PrefsUtils.IT_KEY :
//                                PrefsUtils.storeCount(PrefsUtils.IT_KEY, mContext);
//                                break;
//
//                            case PrefsUtils.LOCAL_KEY :
//                                PrefsUtils.storeCount(PrefsUtils.LOCAL_KEY, mContext);
//                                break;
//
//                            case PrefsUtils.DOMESTIC_KEY :
//                                PrefsUtils.storeCount(PrefsUtils.DOMESTIC_KEY, mContext);
//                                break;
//
//                            case PrefsUtils.ECONOMY_KEY :
//                                PrefsUtils.storeCount(PrefsUtils.ECONOMY_KEY, mContext);
//                                break;
//
//                            case PrefsUtils.SPORTS_KEY :
//                                PrefsUtils.storeCount(PrefsUtils.SPORTS_KEY, mContext);
//                                break;
//
//                            case PrefsUtils.SCIENCE_KEY :
//                                PrefsUtils.storeCount(PrefsUtils.SCIENCE_KEY, mContext);
//                                break;
//                        }
//
////                        Intent webViewIntent = new Intent();
////                        webViewIntent.setClassName(PACKAGE_NAME,WebViewActivity_NAME);
////                        webViewIntent.putExtra(URL_KEY, item.getUrl());
////                        Log.i(TAG, "onClick: " + item.getUrl());
////                        mContext.startActivity(webViewIntent);
//
//                    }
//                });

                if (null == item.getThumbNailUrl()) {
                    //サムネイルローディング中の画像を表示。
                    holder.thumbNailView.setImageResource(R.drawable.yahoo_icon);
                } else {
                    //ローディングが終わったら差し替える。
                    holder.thumbNailView.setImageBitmap(item.getThumNail());
                }

                holder.userLikeView.setImageResource(R.drawable.yellow_star);
                holder.userLikeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            new AlertDialog.Builder(mContext)
                                    .setTitle("お気に入り登録")
                                    .setMessage("お気に入りに登録しますか？")
                                    .setIcon(R.drawable.yellow_star)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(mContext, "お気に入りに登録しました。", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("キャンセル", null)
                                    .show();


                    }
                });
            }
        return view;
        }

}
