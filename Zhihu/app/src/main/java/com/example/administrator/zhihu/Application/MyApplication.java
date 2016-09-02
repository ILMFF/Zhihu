package com.example.administrator.zhihu.Application;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Administrator on 2016/7/21.
 */
public class MyApplication extends Application {
    public static void init(Context context){
        ImageLoaderConfiguration configuration =new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory()
                .build();
        ImageLoader.getInstance().init(configuration);

    }

    public void onCreate(){
        super.onCreate();
        init(this);
    }
}

