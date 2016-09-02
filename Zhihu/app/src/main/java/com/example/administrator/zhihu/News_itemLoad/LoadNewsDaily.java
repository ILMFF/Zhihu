package com.example.administrator.zhihu.News_itemLoad;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import com.example.administrator.zhihu.Http.JSON;
import com.example.administrator.zhihu.Http.http;
import com.example.administrator.zhihu.entity.news_daily;

/**
 * Created by Administrator on 2016/7/29.
 */
public class LoadNewsDaily extends AsyncTask<Integer,Void,news_daily> {

    public static String NEWSDETAIL = "http://news-at.zhihu.com/api/4/news/";
    private WebView webView;

    public LoadNewsDaily(WebView webView){this.webView=webView;}

    protected news_daily doInBackground(Integer...params){
        news_daily newsDaily=null;
        String jsonData;
        try{

            jsonData= http.sendHttpRequest(NEWSDETAIL+params[0]);
            Log.d("jsonData:", jsonData);
            newsDaily= JSON.GSONfrom(jsonData);

        }catch (Exception e) {

        }finally {
            return newsDaily;
        }
    }


    protected void onPostExecute(news_daily mNewsDetail) {
        String headerImage;
        headerImage = mNewsDetail.getImage();
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"img-wrap\">")
                .append("<h1 class=\"headline-title\">")
                .append(mNewsDetail.getTitle()).append("</h1>")
                .append("<span class=\"img-source\">")
                .append(mNewsDetail.getImage_source()).append("</span>")
                .append("<img src=\"").append(headerImage)
                .append("\" alt=\"\">")
                .append("<div class=\"img-mask\"></div>");
        String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
                + mNewsDetail.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
        webView.loadDataWithBaseURL("file:///android_asset/", mNewsContent, "text/html", "UTF-8", null);
    }

}
