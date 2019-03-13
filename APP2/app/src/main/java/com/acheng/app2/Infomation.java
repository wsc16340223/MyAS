package com.acheng.app2;

import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class Infomation extends AppCompatActivity {
    private foods temp;
    int flag = 0;
    DynamicReceiver dynamicReceiver = null;
    DynamicReceiver widgetDynamicReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        temp = (foods) getIntent().getSerializableExtra("Select food");
        TextView info_name = (TextView) findViewById(R.id.info_name);
        info_name.setText(temp.getName());

        RelativeLayout backColor = (RelativeLayout) findViewById(R.id.backColor);
        backColor.setBackgroundColor(Color.parseColor(temp.getColor()));

        TextView info_kind = (TextView) findViewById(R.id.info_kind);
        info_kind.setText(temp.getKind());

        TextView info_nu = (TextView) findViewById(R.id.info_nu);
        info_nu.setText("富含 " + temp.getNutrition());

        ListView info_4 = (ListView) findViewById(R.id.info_4);
        String[] operations = {"分享信息", "不感兴趣", "查看更多信息", "出错反馈"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.info_list, operations);
        info_4.setAdapter(arrayAdapter);

        ImageView backBtn = (ImageView) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                if (flag > 0) {
                    bundle.putSerializable("show", temp);
                    setResult(1, intent);
                } else {
                    setResult(0, intent);
                }
                intent.putExtras(bundle);
                finish();
            }
        });

        final ImageView star = (ImageView) findViewById(R.id.star);
        final int[] tag = new int[]{0};
        star.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tag[0] == 0) {
                    star.setImageResource(R.mipmap.full_star);
                    tag[0] = 1;

                } else if (tag[0] == 1) {
                    star.setImageResource(R.mipmap.empty_star);
                    tag[0] = 0;
                }
            }

        });

        ImageView collcet = (ImageView) findViewById(R.id.collect);
        collcet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp.getCollect() == false) {
                    flag = 1;
                    Toast.makeText(getApplicationContext(), "已收藏", Toast.LENGTH_LONG).show();
                    temp.setCollect(true);
                } else
                    Toast.makeText(getApplicationContext(), "已收藏", Toast.LENGTH_LONG).show();

                Bundle bundle = new Bundle();
                bundle.putSerializable("broadcast_collect", temp);
                Intent broadcastIntent = new Intent("com.acheng.APP2.MyDynamicFilter");

                broadcastIntent.putExtras(bundle);
                sendBroadcast(broadcastIntent);

                Bundle widgetBundle = new Bundle();
                widgetBundle.putSerializable("widget_collect", temp);
                Intent widgetIntentBroadcast = new Intent();   //定义Intent
                widgetIntentBroadcast.setAction("com.example.hasee.myapplication2.MyWidgetDynamicFilter");
                widgetIntentBroadcast.putExtras(widgetBundle);
                sendBroadcast(widgetIntentBroadcast);
            }
        });
        //收藏时弹出消息的广播注册
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction("com.acheng.APP2.MyDynamicFilter");    //添加动态广播的Action
        dynamicReceiver = new DynamicReceiver();
        registerReceiver(dynamicReceiver, dynamic_filter);    //注册自定义动态广播消息

        //widget广播注册
        IntentFilter widget_dynamic_filter = new IntentFilter();
        widget_dynamic_filter.addAction("com.example.hasee.myapplication2.MyWidgetDynamicFilter");
        widgetDynamicReceiver = new DynamicReceiver(); //添加动态广播的Action
        registerReceiver(widgetDynamicReceiver, widget_dynamic_filter); //注册自定义动态广播信息


    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(dynamicReceiver);
        unregisterReceiver(widgetDynamicReceiver);
    }
}
