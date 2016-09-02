package com.example.administrator.zhihu.Http;

import android.widget.Toast;

import com.example.administrator.zhihu.Activity.MainActivity;
import com.example.administrator.zhihu.entity.List_item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class http {

    public static String sendHttpRequest( String address){
                HttpURLConnection connection=null;
                try {
                    URL url=new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream in=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response= new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    return  response.toString();

                }catch (Exception e){
                    e.printStackTrace();
                    return e.getMessage();

                }finally {
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }
}

