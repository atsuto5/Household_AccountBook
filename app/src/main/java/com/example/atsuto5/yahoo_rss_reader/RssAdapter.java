package com.example.atsuto5.yahoo_rss_reader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Atsuto5 on 2017/02/11.
 */
public class RssAdapter extends ArrayAdapter<ItemBeans> {
    private LayoutInflater mInflater;
    private ArrayList<ItemBeans> itemList = new ArrayList<>();
    private String TAG = "RssAdapter";
    private static final String URL_KEY = "URL";
    private static final String PACKAGE_NAME = "com.example.atsuto5.yahoo_rss_reader";
    private static final String WebViewActivity_NAME = "com.example.atsuto5.yahoo_rss_reader.WebViewActivity";
    private Context mContext;
    private String TOPIC_NAME;
    private int mainCount = 0;

    static class ViewHolder{
        TextView titleText;
        Button urlButton;
        ImageButton webViewButton;
    }


    public RssAdapter(Context context, int id, String topicName) {
        super(context, id);
        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TOPIC_NAME = topicName;
        }


    public View getView(final int position, View view, final ViewGroup parent){

        ViewHolder holder;

        if(view == null) {
            view = mInflater.inflate(R.layout.rss_beans, null);
            //ViewHolderを作成
            holder = new ViewHolder();
            holder.titleText = (TextView) view.findViewById(R.id.titleTextView);
            holder.urlButton = (Button) view.findViewById(R.id.urlButton);
            holder.webViewButton = (ImageButton) view.findViewById(R.id.webViewButton);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }

            final ItemBeans item = this.getItem(position);

            if(item != null){

                holder.titleText.setText(item.getTitle());
                holder.urlButton.setText(item.getUrl());
                holder.urlButton.setOnClickListener(new View.OnClickListener()  {
                    //URLをタップしたときWebViewActivityに遷移する
                    public void onClick(View v) {
                        if(TOPIC_NAME.equals("MainTopics")){
                            mainCount += 1 ;
                        } else if (TOPIC_NAME.equals("International")){

                        }

                        Intent webViewIntent = new Intent();
                        webViewIntent.setClassName(PACKAGE_NAME,WebViewActivity_NAME);
                        webViewIntent.putExtra(URL_KEY, item.getUrl());
                        Log.i(TAG, "onClick: " + item.getUrl());
                        mContext.startActivity(webViewIntent);

                        Log.i(TAG, "onClick: " + mainCount);
                    }
                });

                holder.webViewButton.setBackgroundResource(R.drawable.button_selector);
//                holder.webViewButton.setOnClickListener(new View.OnClickListener()  {
//                    //ボタンを押したときWebViewActivityに遷移する
//                    public void onClick(View v) {
//                        Intent webViewIntent = new Intent();
//                        webViewIntent.setClassName(PACKAGE_NAME,WebViewActivity_NAME);
//                        webViewIntent.putExtra(URL_KEY, item.getUrl());
//                        Log.i(TAG, "onClick: " + item.getUrl());
//                        mContext.startActivity(webViewIntent);
//                    }
//                });
            }
        return view;
        }
}
