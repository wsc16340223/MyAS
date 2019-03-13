package com.acheng.app5;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{
    private List<MyObject> data;
    private Context context;
    private int layoutId;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public MyRecyclerViewAdapter(Context context, int layoutId, List<MyObject> data){
        this.context = context;
        this.layoutId = layoutId;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position){
        //隐藏加载条
        if (data.get(position).getMyData().getImage() != null){
            holder.getView(R.id.progressBar).setVisibility(View.INVISIBLE);
        }

        if (data.get(position).getMyImages() == null){
            holder.getView(R.id.seekBar).setEnabled(false);
        }
        else{
            holder.getView(R.id.seekBar).setEnabled(true);
            String duration = data.get(position).getMyData().getDuration();
            String[] timeArray = new String[2];
            timeArray = duration.split(":");
            int mins = Integer.valueOf(timeArray[0]);
            int secs = Integer.valueOf(timeArray[1]);
            int time = mins * 60 + secs;
            ((SeekBar)holder.getView(R.id.seekBar)).setProgress(0);
            ((SeekBar)holder.getView(R.id.seekBar)).setMax(time);

            ((SeekBar)holder.getView(R.id.seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    ArrayList<MyObject.MyImage> images = data.get(position).getMyImages();
                    for (int i = 0; i < images.size(); i++){
                        if (images.get(i).getIndex() == progress){
                            Bitmap bitmap = images.get(i).getBitmap();
                            ((ImageView)holder.getView(R.id.img)).setImageBitmap(bitmap);
                            break;
                        }
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //初始化
                    seekBar.setProgress(0);
                    ((ImageView)holder.getView(R.id.img)).setImageBitmap(data.get(position).getMyData().getImage());
                }
            });
        }
        ((ImageView)holder.getView(R.id.img)).setImageBitmap(data.get(position).getMyData().getImage());
        ((TextView)holder.getView(R.id.title)).setText(data.get(position).getMyData().getTitle());
        ((TextView)holder.getView(R.id.playNum)).setText(data.get(position).getMyData().getPlayNum());
        ((TextView)holder.getView(R.id.commentNum)).setText(data.get(position).getMyData().getCommentNum());
        ((TextView)holder.getView(R.id.duration)).setText(data.get(position).getMyData().getDuration());
        ((TextView)holder.getView(R.id.createTime)).setText(data.get(position).getMyData().getCreateTime());
        ((TextView)holder.getView(R.id.resume)).setText(data.get(position).getMyData().getResume());
    }

    @Override
    public int getItemCount(){
        if (!data.isEmpty())
            return data.size();
        return 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private SparseArray<View> views;
        private View view;

        public MyViewHolder(View v){
            super(v);
            view = v;
            views = new SparseArray<View>();
        }
        public <T extends View> T getView(int id){
            View v = views.get(id);
            if (v == null){
                //创建view
                v = view.findViewById(id);
                views.put(id, v);
            }
            return (T) v;
        }
    }
}
