package com.acheng.music;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MusicService extends Service {
    // 这里设置为public属性，以便activity里面能直接获取
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public MusicService() {
        try {
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/data/t.mp3");
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public final IBinder binder = new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return binder;
    }
    @Override
    public void onCreate(){
        super.onCreate();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
    public void setPath(Uri uri) {
        try {
            mediaPlayer.release();
            mediaPlayer = new MediaPlayer();

            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // 通过Binder来保持Activity和Service的通信

    public class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            switch (code) {
                //service solve
                case 101://初始或暂停状态,点击开始或继续播放
                    mediaPlayer.start();
                    break;
                case 102://正在播放状态，点击暂停
                    mediaPlayer.pause();
                    break;
                case 103://点击停止
                    mediaPlayer.stop();
                    try {
                        mediaPlayer.prepare();
                        mediaPlayer.seekTo(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
            return super.onTransact(code, data, reply, flags);
        }
        MusicService getService() {
            return MusicService.this;
        }
    }
}
