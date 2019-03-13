package com.acheng.app2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;

public class StaticReceiver extends BroadcastReceiver {
    private static final String STATICACTION = "com.acheng.app2.MyStaticFilter";
    private foods temp;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(STATICACTION)){
            Bundle bundle = intent.getExtras();
            temp = (foods)bundle.getSerializable("broadcast_start") ;

            bundle.putSerializable("Select food", temp);
            Intent intentStart = new Intent(context,Infomation.class);
            intentStart.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentStart, PendingIntent.FLAG_CANCEL_CURRENT);


            //添加Notification部分
            NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            String MyChannel = "channel1";
            NotificationChannel staticChannel = null;
            if (staticChannel == null)
            {
                String name = "new_channel1";
                String description = "description_channel1";

                int importance = NotificationManager.IMPORTANCE_HIGH;
                staticChannel = new NotificationChannel(MyChannel, name, importance);
                staticChannel.setDescription(description);
                staticChannel.enableLights(true);
                manager.createNotificationChannel(staticChannel);
            }

            Notification.Builder builder=new Notification.Builder(context, MyChannel);
            builder.setContentTitle("今日推荐")
                    .setContentText(temp.getName())
                    .setTicker("您有一条新消息")
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.empty_star)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            //绑定Notification，发送通知请求
            Notification notify = builder.build();
            manager.notify(0,notify);
        }
    }
}