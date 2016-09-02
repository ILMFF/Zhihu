package com.example.administrator.zhihu.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zhihu.R;
import com.example.administrator.zhihu.entity.Image_viewpager;
import com.example.administrator.zhihu.entity.List_item;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/7/19.
 */
public class list_adapter extends ArrayAdapter<List_item> {

    private int resource;
    private LayoutInflater inflater;
    private DisplayImageOptions options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .build();


    public list_adapter(Context context, int resource) {
        super(context, resource);
        inflater = LayoutInflater.from(context);
        this.resource = resource;
    }

    public list_adapter(Context context, int resource, List<List_item> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        this.resource = resource;
    }
    public View getView(int position, View convertview , ViewGroup parent){
        ViewHolder holder;
        if (convertview==null){
            convertview=inflater.inflate(R.layout.list_card,parent,false);
            holder=new ViewHolder();
            holder.item_image=(ImageView)convertview.findViewById(R.id.head);
            holder.item_title=(TextView)convertview.findViewById(R.id.name);
            convertview.setTag(holder);
        }
        else {
            holder=(ViewHolder)convertview.getTag();
        }
        List_item news_list=getItem(position);
        holder.item_title.setText(news_list.getItem_text());

        ImageLoader.getInstance().displayImage(news_list.getItem_image()  ,holder.item_image,options);
        return convertview;
    }

    class ViewHolder {
        TextView item_title;
        ImageView item_image;
    }

    public void refreshNewsList(List<List_item> newsList) {
        clear();
        addAll(newsList);
        notifyDataSetChanged();
    }
}




