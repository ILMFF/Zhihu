package com.example.administrator.zhihu.entity;

import java.io.Serializable;

public class Image_viewpager implements Serializable {

    private String thumbnail;
    private String description;
    private int id;
//    private int color;
//    private String name;
//    private int limit;

    public Image_viewpager(String thumbnail,String description,int id){
        this.thumbnail=thumbnail;
        this.description=description;
        this.id=id;
    }

    public void setThumbnail(String thumbnail){
        this.thumbnail=thumbnail;
    }
    public String getThumbnail(){
        return  thumbnail;
    }

    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return description;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return  id;
    }


//    public String getName(){
//        return name;
//    }
//    public void setName(String name){
//        this.name=name;
//    }
//
//
//    public void setColor(int color){
//        this.color=color;
//    }
//    public int getColor(){
//        return  color;
//    }
//
//
//    public void setLimit(int limit){
//        this.limit=limit;
//    }
//    public int getLimit(){
//        return limit;
//    }

}
