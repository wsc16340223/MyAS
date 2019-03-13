package com.acheng.app2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.widget.RemoteViews;

import org.greenrobot.eventbus.EventBus;

public class DynamicReceiver extends BroadcastReceiver {
    private static final String DYNAMICACTION = "com.acheng.APP2.MyDynamicFilter";
    private foods temp;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DYNAMICACTION)) {    //动作检测
            Bundle bundle = intent.getExtras();
            temp = (foods)bundle.getSerializable("broadcast_collect");

            Intent intentDy = new Intent(context, MainActivity.class);
            intentDy.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentDy,PendingIntent.FLAG_CANCEL_CURRENT);

            //添加Notification部分
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            String MyChannel = "channel2";
            NotificationChannel dynamicChannel = null;
            if (dynamicChannel == null)
            {
                String name = "new_channel2";
                String description = "description_channel2";

                int importance = NotificationManager.IMPORTANCE_HIGH;
                dynamicChannel = new NotificationChannel(MyChannel, name, importance);
                dynamicChannel.setDescription(description);
                dynamicChannel.enableLights(true);
                manager.createNotificationChannel(dynamicChannel);
            }

            Notification.Builder builder = new Notification.Builder(context, MyChannel);
            builder.setContentTitle("已收藏")
                    .setContentText(temp.getName())
                    .setTicker("您有一条新消息")
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.full_star)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            //绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(0,notify);
            EventBus.getDefault().post(temp);
        }
        else if (intent.getAction().equals("com.example.hasee.myapplication2.MyWidgetDynamicFilter"))
        {
            Bundle bundle = intent.getExtras();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            temp = (foods)bundle.getSerializable("widget_collect");
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            if(temp != null)
                views.setTextViewText(R.id.appwidget_text, "已收藏 " + temp.getName());
            Intent intentDy = new Intent(context, MainActivity.class);
            intentDy.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentDy,PendingIntent.FLAG_CANCEL_CURRENT);
            // Construct the RemoteViews object
            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
            ComponentName me = new ComponentName(context, NewAppWidget.class);
            appWidgetManager.updateAppWidget(me, views);
            EventBus.getDefault().post(temp);
        }
    }
}
