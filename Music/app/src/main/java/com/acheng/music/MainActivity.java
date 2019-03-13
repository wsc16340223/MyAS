package com.acheng.music;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;



public class MainActivity extends AppCompatActivity {

    private MusicService musicService;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    private CircleImageView cirImg = null;
    private TextView name = null;
    private TextView singer = null;
    private TextView curTime = null;
    private TextView endTime = null;
    private ImageView playView = null;
    private ImageView stopView = null;
    private ImageView quitView = null;
    private SeekBar seekBar = null;
    private ImageView file = null;
    private IBinder mBinder;
    private String state;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //在Activity中调用bindService保持与Service的通信（写在Activity类）： Activity启动时绑定Service
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

        cirImg = findViewById(R.id.cirImg);
        name = findViewById(R.id.name);
        singer = findViewById(R.id.singer);
        curTime = findViewById(R.id.currentTime);
        endTime = findViewById(R.id.endTime);
        playView = findViewById(R.id.playView);
        stopView = findViewById(R.id.stopView);
        quitView = findViewById(R.id.quitView);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setEnabled(false);
        file = findViewById(R.id.file);

        playView.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setEnabled(true);
                if (state == null || state == "pause" || state == "stop"){
                    state = "play";
                    playView.setImageResource(R.mipmap.pause);
                    try {
                        int code = 101;
                        Parcel data = Parcel.obtain();
                        Parcel reply = Parcel.obtain();
                        mBinder.transact(code, data, reply, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    playing();
                }
                else if (state == "play"){
                    state = "pause";
                    playView.setImageResource(R.mipmap.play);
                    try {
                        int code = 102;
                        Parcel data = Parcel.obtain();
                        Parcel reply = Parcel.obtain();
                        mBinder.transact(code, data, reply, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

//                handler.postDelayed(runnable, 10);
//                //为0表示没有在播放
//                if (state != "play") {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            playView.setImageResource(R.mipmap.pause);
//                            try {
//                                int code = 101;
//                                Parcel data = Parcel.obtain();
//                                Parcel reply = Parcel.obtain();
//                                mBinder.transact(code, data, reply, 0);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                  state = "play";
//                }
//
//                else if (state == "play") {
//                    handler.removeCallbacks(runnable);
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            playView.setImageResource(R.mipmap.play);
//                            try {
//                                int code = 102;
//                                Parcel data = Parcel.obtain();
//                                Parcel reply = Parcel.obtain();
//                                mBinder.transact(code, data, reply, 0);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                  state = "pause";
//                }
            }
        });
        stopView.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
//                handler.removeCallbacks(runnable);
//                if (musicService != null) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                int code = 103;
//                                Parcel data = Parcel.obtain();
//                                Parcel reply = Parcel.obtain();
//                                mBinder.transact(code, data, reply, 0);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                  state = "stop";
//                }
                state = "stop";
                try {
                    int code = 103;
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    mBinder.transact(code, data, reply, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                playView.setImageResource(R.mipmap.play);
                cirImg.setRotation(0);
                playing();
            }
        });
        quitView.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handler.removeCallbacks(runnable);
                unbindService(serviceConnection);
                try {
                    MainActivity.this.finish();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    curTime.setText(time.format(progress));
                    musicService.mediaPlayer.seekTo(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        file.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType(“image/*”);//选择图片
                intent.setType("audio/*"); //选择音频
                //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
                //intent.setType(“video/*;image/*”);//同时选择视频和图片
                //intent.setType("*/*");//无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
        });

    }
    private void playing(){
        final io.reactivex.Observable<String> observable = io.reactivex.Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                while (state == "play"){
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException exception) {
                        if (!e.isDisposed()) {
                            e.onError(exception);
                        }
                    }
                    e.onNext(state);
                }
                if (state == "stop")
                    e.onNext(state);
            }
        });
        DisposableObserver<String> disposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                if (musicService != null && musicService.mediaPlayer != null) {
                    //更新当前播放时间
                    curTime.setText(time.format(musicService.mediaPlayer.getCurrentPosition()));
                    //更新结束时间
                    endTime.setText(time.format(musicService.mediaPlayer.getDuration()));
                    //更新进度条
                    seekBar.setProgress(musicService.mediaPlayer.getCurrentPosition());
                    //更新进度条最大值
                    seekBar.setMax(musicService.mediaPlayer.getDuration());
                    //图片旋转
                    if (musicService.mediaPlayer.isPlaying()) {
                        cirImg.setRotation((cirImg.getRotation() + 1) % 360);
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }
    //bindService成功后回调onServiceConnected函数，通过IBinder获取Service对象，实现Activity与Service的绑定
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder) service).getService();
            mBinder = service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };


//    private Handler handler = new Handler();
//    private Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            if (musicService != null && musicService.mediaPlayer != null) {
//                //更新当前播放时间
//                curTime.setText(time.format(musicService.mediaPlayer.getCurrentPosition()));
//                //更新结束时间
//                endTime.setText(time.format(musicService.mediaPlayer.getDuration()));
//                //更新进度条
//                seekBar.setProgress(musicService.mediaPlayer.getCurrentPosition());
//                //更新进度条最大值
//                seekBar.setMax(musicService.mediaPlayer.getDuration());
//                //图片旋转
//                if (musicService.mediaPlayer.isPlaying()) {
//                    cirImg.setRotation((cirImg.getRotation() + 1) % 360);
//                }
//                handler.postDelayed(this, 10);
//            }
//        }
//    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        compositeDisposable.clear();
    }
    String path;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开
                path = uri.getPath();
                //tv.setText(path);
                Toast.makeText(this,path+"11111",Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = uri.getPath();
                //tv.setText(path);

                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(getApplicationContext(), uri);
                musicService.setPath(uri);
                name.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
                singer.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                byte[] imgData = mmr.getEmbeddedPicture();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                cirImg.setImageBitmap(bitmap);

                Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
                mmr.release();
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
                //tv.setText(path);
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(path);
                musicService.setPath(uri);
                name.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
                singer.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
                byte[] imgData = mmr.getEmbeddedPicture();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                cirImg.setImageBitmap(bitmap);
                Toast.makeText(MainActivity.this, path+"222222", Toast.LENGTH_SHORT).show();
                mmr.release();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://storage/emulated/0/Download"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
