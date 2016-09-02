package com.example.administrator.zhihu.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.zhihu.Http.JSON;
import com.example.administrator.zhihu.Http.http;
import com.example.administrator.zhihu.ListAdapter.MyAdapter;
import com.example.administrator.zhihu.ListAdapter.list_adapter;
import com.example.administrator.zhihu.News_itemLoad.LoadTask;
import com.example.administrator.zhihu.News_itemLoad.LoadViewpage;
import com.example.administrator.zhihu.R;
import com.example.administrator.zhihu.entity.Image_viewpager;
import com.example.administrator.zhihu.entity.List_item;
import com.example.administrator.zhihu.network.Network;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.L;

import java.util.*;

public class MainActivity extends AppCompatActivity  {

    private Toolbar toolbar;

    private ListView news_item;
    private RelativeLayout relativeLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private list_adapter adapter;
    private boolean isConnected;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    //private LinearLayout linearLayout;
    private CardView cardView;
    private TextView descrition;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private MyAdapter myPagerAdapter;
    private MyPagerListener myPagerListener;
    private  DisplayImageOptions options;

    public static List<Image_viewpager>image_viewpagerList;
    private int flag = 1;//夜间模式
    private int pointIndex = 0; //圆圈标志位

    private boolean isStop = false;   // 线程标志
    private int[] banner={R.drawable.t1,R.drawable.t2,R.drawable.t3,R.drawable.t4,R.drawable.t5};
    private String[] bannerText={"nihao","你个老东西","hello world","个人热点","世界你好，我来了!"};
    private List<ImageView>mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view_pager=inflater.inflate(R.layout.viewpager,null);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiper);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        viewPager=(ViewPager)view_pager.findViewById(R.id.viewpager);
        linearLayout=(LinearLayout)view_pager.findViewById(R.id.point);
        descrition=(TextView)view_pager.findViewById(R.id.descrition);

        LayoutInflater inflater_cardview = LayoutInflater.from(this);
        View view_cardview=inflater_cardview.inflate(R.layout.list_card,null);
        cardView=(CardView)view_cardview.findViewById(R.id.card_item);


        toolbar.inflateMenu(R.menu.actionmenu);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);




        //-------------------------------------------------------会变化的菜单按钮

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //-----------------------------------------------------判断是否滑动到顶，到顶解封下拉

        news_item = (ListView) findViewById(R.id.new_item);
        news_item.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (news_item != null && news_item.getChildCount() > 0) {
                    boolean firstItemVisible = news_item.getFirstVisiblePosition() == 0;
                    boolean topOfFirstItemVisible = news_item.getChildAt(0).getTop() == 0;
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeRefreshLayout.setEnabled(enable);
            }
        });

        news_item.addHeaderView(view_pager);



        //----------------------------------------------------Banner广告轮播

        initData();
        initAction();
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (!isStop) {
                    SystemClock.sleep(6000);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            isConnected = Network.checkNetworkConnection(MainActivity.this);
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }).start();







        //---------------------------------------------------列表获取

        adapter = new list_adapter(this, R.layout.list_card);
        news_item.setAdapter(adapter);

        isConnected = Network.checkNetworkConnection(this);
        if (isConnected) {
            new LoadTask(adapter).execute();

        } else {
            Network.noNetworkAlert(this);
        }
        news_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(MainActivity.this, "你选择了第"+(position-1)+"Item"  , Toast.LENGTH_SHORT).show();
                News_DailyActivity.startActivity(MainActivity.this , adapter.getItem(position-1));

            }
        });

        //------------------------------------------------------------下拉刷新
        swipeRefreshLayout.setColorSchemeResources(
                R.color.red,
                R.color.yellow,
                R.color.blue
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isConnected) {
                    new LoadTask(adapter, new LoadTask.onFinishListener() {
                        @Override
                        public void afterTaskFinish() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }).execute();


                } else {
                    Network.noNetworkAlert(MainActivity.this);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }


    //------------------------------------------------------------------------------------列表跳转，传列表ID

//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        News_DailyActivity.startActivity(this, adapter.getItem(position));
//    }




    //----------------------------------------------------------------------------------ActionMenu

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_setting:
                    if (flag == 1) {
                       relativeLayout.setBackgroundResource(R.color.cardview_dark_background);
                        cardView.setCardBackgroundColor(getResources().getColor(R.color.card_black));

                        flag = 0;
                    } else {
                        relativeLayout.setBackgroundResource(R.color.white);
                        flag = 1;
                    }
                    break;
                case R.id.action_setting1:
                    Toast.makeText(MainActivity.this, "暂时没有内容", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };


    //---------------------------------------------------------------------------



    private void initAction() {
        myPagerListener = new MyPagerListener();
        viewPager.setOnPageChangeListener(myPagerListener);
        //取中间数来作为起始位置
        int index = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2 % mlist.size());
        //用来触发监听器
        viewPager.setCurrentItem(index);
        linearLayout.getChildAt(pointIndex).setEnabled(true);
    }


    private void initData() {

        mlist = new ArrayList<ImageView>();
        View view;

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT      );
        for (int i = 0; i < banner.length; i++) {
            // 设置广告图
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setBackgroundResource(banner[i]);
            //Log.d("theme_image1",LoadTask.image_viewpagers.size() );

            //ImageLoader.getInstance().displayImage(banner_image[i],imageView,options);//图片异步加载

            mlist.add(imageView);//添加进imageview数组
            // 设置圆圈点
            view = new View(MainActivity.this);
            params = new LinearLayout.LayoutParams(10,10);
            params.leftMargin=10;
            view.setBackgroundResource(R.drawable.selector);
            view.setLayoutParams(params);
            view.setEnabled(false);

            linearLayout.addView(view);
        }
        myPagerAdapter = new MyAdapter(mlist);
        viewPager.setAdapter(myPagerAdapter);
    }





    class MyPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            int newPosition = position % bannerText.length;
                descrition.setText(bannerText[newPosition]);
            linearLayout.getChildAt(newPosition).setEnabled(true);
            linearLayout.getChildAt(pointIndex).setEnabled(false);
            // 更新标志位
            pointIndex = newPosition;

        }

    }
    protected void onDestroy() {
        // 关闭定时器
        isStop = true;
        super.onDestroy();
    }



}



