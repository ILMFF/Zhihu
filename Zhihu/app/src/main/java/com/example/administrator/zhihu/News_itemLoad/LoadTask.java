package com.example.administrator.zhihu.News_itemLoad;

import android.os.AsyncTask;
import android.util.Log;


import com.example.administrator.zhihu.Http.JSON;
import com.example.administrator.zhihu.Http.http;
import com.example.administrator.zhihu.ListAdapter.list_adapter;
import com.example.administrator.zhihu.entity.Image_viewpager;
import com.example.administrator.zhihu.entity.List_item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoadTask extends AsyncTask<Void,Void,List<List_item>> {
    private list_adapter adapter;
    private onFinishListener listener;

    private static String NEWS_LATEST="http://news-at.zhihu.com/api/4/news/latest";



    public LoadTask(list_adapter adapter) {
        super();
        this.adapter = adapter;
    }
    public LoadTask(list_adapter adapter, onFinishListener listener) {
        super();
        this.adapter = adapter;
        this.listener = listener;
    }

    protected List<List_item> doInBackground(Void... params){
        List<List_item> news_list=null;


        try{
            String jsonData;


            //获取列表
           jsonData= http.sendHttpRequest(NEWS_LATEST);
           news_list= JSON.parseJSONWithJSONObject(jsonData);



        }
        catch (Exception e){
           e.printStackTrace();
        }finally {
            return news_list;
        }
    }

    protected void onPostExecute(List<List_item> newsList){
        adapter.refreshNewsList(newsList);
        if (listener != null) {
            listener.afterTaskFinish();
        }
    }

    public interface onFinishListener {
        public void afterTaskFinish();
    }


}
