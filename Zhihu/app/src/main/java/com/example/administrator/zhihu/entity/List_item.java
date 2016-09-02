package com.example.administrator.zhihu.entity;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/19.
 */
public class List_item implements Serializable {
    private String item_text;
    private String item_image;
    private int item_id;

    public List_item(String item_text,String item_image,int item_id){
        this.item_text=item_text;
        this.item_image=item_image;
        this.item_id=item_id;

    }

    public void setItem_text(String item_text){
        this.item_text=item_text;
    }
    public String getItem_text(){
        return item_text;
    }

    public  void setItem_image(String item_image){
        this.item_image=item_image;
    }
    public  String getItem_image(){
        return item_image;
    }

    public void setItem_id(int item_id){
        this.item_id=item_id;
    }
    public int getItem_id(){
        return item_id;
    }
}
