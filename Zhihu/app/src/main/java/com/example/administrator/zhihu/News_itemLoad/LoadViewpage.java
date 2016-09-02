package com.example.administrator.zhihu.News_itemLoad;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.example.administrator.zhihu.Http.JSON;
import com.example.administrator.zhihu.Http.http;
import com.example.administrator.zhihu.R;
import com.example.administrator.zhihu.entity.Image_viewpager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;


public class LoadViewpage extends AsyncTask<Void,Void,List<Image_viewpager>> {
    Context context;
    private static String NEWS_THEME="http://news-at.zhihu.com/api/4/themes";

    public LoadViewpage(Context context){
        this.context=context;
    }

    protected List<Image_viewpager> doInBackground(Void... params) {
         List<Image_viewpager>image_viewpagers;

        try {
            String imageData;

            imageData= http.sendHttpRequest(NEWS_THEME);
            image_viewpagers= JSON.parseJSONWithImage(imageData);
            return image_viewpagers;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }



}
