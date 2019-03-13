package com.acheng.app4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<CommentInfo> list;

    public MyAdapter()
    {
        list = new ArrayList<>();
    }
    @Override
    public int getCount()
    {
        if (list == null)
            return 0;
        return list.size();
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public CommentInfo getItem(int i)
    {
        if (list == null)
            return null;
        return list.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        // 新声明一个View变量和ViewHoleder变量,ViewHolder类在下面定义。
        //View convertView;
        ViewHolder viewHolder;
        // 当view为空时才加载布局，否则，直接修改内容
        if (view == null) {
            // 通过inflate的方法加载布局，context需要在使用这个Adapter的Activity中传入。
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.userName = (TextView) view.findViewById(R.id.userName);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.comment = (TextView) view.findViewById(R.id.comment);
            viewHolder.likeNum = (TextView) view.findViewById(R.id.likeNum);

            view.setTag(viewHolder); // 用setTag方法将处理好的viewHolder放入view中
        }
        else { // 否则，让convertView等于view，然后从中取出ViewHolder即可

            viewHolder = (ViewHolder) view.getTag();
        }
        // 从viewHolder中取出对应的对象，然后赋值给他们
        viewHolder.userName.setText(list.get(i).getName());
        viewHolder.time.setText(list.get(i).getTime());
        viewHolder.comment.setText(list.get(i).getComment());
        viewHolder.likeNum.setText(""+list.get(i).getLikeCount());

        // 将这个处理好的view返回
        return view;
    }

    public void addItem(CommentInfo commentInfo)
    {
        if (list == null)
            list = new ArrayList<>();

        list.add(commentInfo);
        notifyDataSetChanged();
    }

    public void deleteItem(int i)
    {
        if (list == null || list.isEmpty())
            return;
        list.remove(list.get(i));
        notifyDataSetChanged();
    }


    public void reLoad(ArrayList<CommentInfo> mylist)
    {
        list = mylist;
        notifyDataSetChanged();
    }
    class ViewHolder
    {
        public TextView userName;
        public TextView time;
        public TextView comment;
        public TextView likeNum;
    }

}
