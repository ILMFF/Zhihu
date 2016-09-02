package com.example.administrator.zhihu.Http;

import android.util.Log;
import android.view.View;

import com.example.administrator.zhihu.Activity.MainActivity;
import com.example.administrator.zhihu.entity.Image_viewpager;
import com.example.administrator.zhihu.entity.List_item;
import com.example.administrator.zhihu.entity.news_daily;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class JSON {
    public static String[] banner_image=new String[20];
    public static String[] banner_descrition=new String[20];


    public static List<List_item> parseJSONWithJSONObject(String jsonData){
        try {
            List<List_item>new_item=new ArrayList<List_item>();
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONArray jsonArray=jsonObject.getJSONArray("stories");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);
                String title=object.getString("title");
                int id=object.getInt("id");
                String image="";
                if (object.has("images")){
                    image=(String)object.getJSONArray("images").get(0);
                }
                List_item list_item=new List_item(title,image,id);
                new_item.add(list_item);


            }
            return new_item;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static List<Image_viewpager> parseJSONWithImage(String jsonData){
        try {
            List<Image_viewpager>theme_view=new ArrayList<Image_viewpager>();
            JSONObject jsonObject=new JSONObject(jsonData);
            JSONArray jsonArray=jsonObject.getJSONArray("others");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object=jsonArray.getJSONObject(i);

                String theme_descrition=object.getString("description");
                int theme_id=object.getInt("id");
                String theme_image=object.getString("thumbnail");
                banner_descrition[i]=theme_descrition;
                banner_image[i]=theme_image;

                Log.d("banner_image", banner_image[i]);

                Image_viewpager list=new Image_viewpager(theme_image,theme_descrition,theme_id);
                theme_view.add(list);

            }
            String i=Integer.toString(theme_view.size());
            Log.d("theme_view2", i);

            return theme_view;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


//
//    public static Image_viewpager GSONfrom_ViewPager(String jsonData){
//        Gson gson=new Gson();
//        return gson.fromJson(jsonData,Image_viewpager.class);
//    }


    public static news_daily GSONfrom(String jsonData){

        Gson gson=new Gson();
        return  gson.fromJson(jsonData,news_daily.class);
    }



}
