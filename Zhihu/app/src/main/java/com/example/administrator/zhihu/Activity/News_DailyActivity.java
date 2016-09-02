package com.example.administrator.zhihu.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.zhihu.Application.MyApplication;
import com.example.administrator.zhihu.News_itemLoad.LoadNewsDaily;
import com.example.administrator.zhihu.R;
import com.example.administrator.zhihu.entity.List_item;
import com.example.administrator.zhihu.entity.news_daily;
import com.example.administrator.zhihu.network.Network;


public class News_DailyActivity extends Activity{

    private WebView webView;
    private List_item new_list;
    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_daily);

        toolbar=(Toolbar)findViewById(R.id.toolbar_daily);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        webView=(WebView)findViewById(R.id.webview);

        webView.setHorizontalScrollBarEnabled(false);//取消水平下拉条显示
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);//取消垂直下拉条显示

        new_list=(List_item)getIntent().getSerializableExtra("news_list");
        String s=Integer.toString(new_list.getItem_id());
        Log.d("idData", s);
        new LoadNewsDaily(webView).execute(new_list.getItem_id());

    }
    public static void startActivity(Context context, List_item news_list) {
        if (Network.checkNetworkConnection(context)) {
            Intent intent = new Intent(context, News_DailyActivity.class);
            intent.putExtra("news_list", news_list);
            context.startActivity(intent);
        } else {
            Network.noNetworkAlert(context);
        }
    }





}
