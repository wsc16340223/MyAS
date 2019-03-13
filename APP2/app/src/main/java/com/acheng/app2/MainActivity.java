package com.acheng.app2;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;


public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    //private RecyclerView.LayoutManager layoutManager;

    private ListView listView;
    private MyListViewAdapter myListViewAdapter;

    private FloatingActionButton floatButton;
    private boolean isCollect;

    private int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        myRecyclerViewAdapter = new MyRecyclerViewAdapter();
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(myRecyclerViewAdapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        //初始化recyclerView，在Adapter中设置了主页的数据
        myRecyclerViewAdapter = new MyRecyclerViewAdapter();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myRecyclerViewAdapter = new MyRecyclerViewAdapter());
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(myRecyclerViewAdapter);

        //myRecyclerViewAdapter的点击和长按，点击进入详情界面，长按删除
        myRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int i)
            {
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Select food", myRecyclerViewAdapter.getItem(i));
                Intent intent1 = new Intent(MainActivity.this, Infomation.class);
                intent1.putExtras(bundle1);
                startActivityForResult(intent1,0);
            }
            @Override
            public void onItemLongClick(View view, int i)
            {
                String deleteName = myRecyclerViewAdapter.getItem(i).getName();

                myListViewAdapter.deleteItem(deleteName);
                myRecyclerViewAdapter.deleteItem(i);

                Toast.makeText(getApplication(), "删除"+deleteName, Toast.LENGTH_LONG).show();
            }
        });

        //初始化listView
        listView = findViewById(R.id.listView);
        myListViewAdapter = new MyListViewAdapter();
        listView.setAdapter(myListViewAdapter);

        //按钮图标为收藏以及显示主页面的内容
        isCollect = false;
        change();

        //listView的点击事件，进入详情界面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (i == 0) return;

                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("Select food", myListViewAdapter.getItem(i));
                Intent intent2 = new Intent(MainActivity.this, Infomation.class);
                intent2.putExtras(bundle2);
                startActivityForResult(intent2,0);
            }
        });
        //listView的长按事件，删除对话框
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l)
            {
                if (i == 0)
                    return false;
                final AlertDialog.Builder myDialog = new AlertDialog.Builder(MainActivity.this);
                myDialog.setTitle("提示").setMessage("是否确定删除"+myListViewAdapter.getItem(i).getName()+"？").setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myRecyclerViewAdapter.updateData(myListViewAdapter.getItem(i).getName(), false);
                                myListViewAdapter.deleteItem(i);
                            }
                        }).setNegativeButton("取消",  null).create().show();
                return  true;
            }
        });

        Random random = new Random();
        i = random.nextInt(myRecyclerViewAdapter.getItemCount());
        //进入APP时的静态广播
        Bundle bundle = new Bundle();
        bundle.putSerializable("broadcast_start", myRecyclerViewAdapter.getItem(i));
        Intent intentStart = new Intent("com.acheng.app2.MyStaticFilter");
        intentStart.setComponent(new ComponentName("com.acheng.app2", "com.acheng.app2.StaticReceiver"));
        intentStart.putExtras(bundle);
        sendBroadcast(intentStart);

        onRestart();

        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        //注册widget的静态广播
        Bundle bundleWidget = new Bundle();
        bundleWidget.putSerializable("widget_start",myRecyclerViewAdapter.getItem(i));
        Intent widgetIntentBroadcast = new Intent("com.example.hasee.myapplication2.MyWidgetStaticFilter");
        widgetIntentBroadcast.setComponent(new ComponentName("com.acheng.app2", "com.acheng.app2.NewAppWidget"));
        widgetIntentBroadcast.putExtras(bundleWidget);
        sendBroadcast(widgetIntentBroadcast);
    }

    //处理界面调回来的数据改变
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 0)
        {
            //内容更改,即收藏
            if (resultCode == 1)
            {
                foods food = (foods)data.getSerializableExtra("show");

                myRecyclerViewAdapter.updateData(food.getName(), food.getCollect());
                myListViewAdapter.addItem(food);
            }
        }
    }
    //点击按钮，切换列表
    public void change()
    {
        floatButton = findViewById(R.id.floatButton);
        floatButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //显示收藏页面
                if (isCollect)
                {
                    findViewById(R.id.recyclerView).setVisibility(View.INVISIBLE);
                    findViewById(R.id.listView).setVisibility(View.VISIBLE);
                    floatButton.setImageResource(R.mipmap.mainpage);
                    isCollect = false;
                }
                //显示主页面
                else
                {
                    findViewById(R.id.recyclerView).setVisibility(View.VISIBLE);
                    findViewById(R.id.listView).setVisibility(View.INVISIBLE);
                    floatButton.setImageResource(R.mipmap.collect);
                    isCollect = true;
                }
            }
        });
    }
    //点击后看见的是收藏页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(foods food)
    {
        myRecyclerViewAdapter.updateData(food.getName(), food.getCollect());

        myListViewAdapter.addItem(food);

        isCollect = true;
        recyclerView.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
        floatButton.setImageResource(R.mipmap.mainpage);
    }
    //解除注册
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
