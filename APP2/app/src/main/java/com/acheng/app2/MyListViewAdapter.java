package com.acheng.app2;

import java.util.ArrayList;
import java.util.List;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;

public class MyListViewAdapter extends BaseAdapter {
    private List<foods> list;

    public MyListViewAdapter()
    {
        list = new ArrayList<>();
        list.add(new foods("收藏夹", "*", "*", "*", "#000000"));
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
    public foods getItem(int i)
    {
        if (list == null)
            return null;
        return list.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        // 新声明一个View变量和ViewHoleder变量,ViewHolder类在下面定义。
        View convertView;
        ViewHolder viewHolder;
        // 当view为空时才加载布局，否则，直接修改内容
        if (view == null) {
            // 通过inflate的方法加载布局，context需要在使用这个Adapter的Activity中传入。
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_item, null);
            viewHolder = new ViewHolder();
            viewHolder.foodKind = (TextView) view.findViewById(R.id.food_kind);
            viewHolder.foodName = (TextView) view.findViewById(R.id.food_name);

            view.setTag(viewHolder); // 用setTag方法将处理好的viewHolder放入view中
        }
        else { // 否则，让convertView等于view，然后从中取出ViewHolder即可

            viewHolder = (ViewHolder) view.getTag();
        }
        // 从viewHolder中取出对应的对象，然后赋值给他们
        viewHolder.foodKind.setText(list.get(i).getkName());
        viewHolder.foodName.setText(list.get(i).getName());

        // 将这个处理好的view返回
        return view;
    }

    public void addItem(foods food)
    {
        if (list == null)
            list = new ArrayList<>();
        for(foods i : list)
        {
            if (i.getName().equals(food.getName()))
            {
                notifyDataSetChanged();
                return;
            }
        }
        list.add(food);
        notifyDataSetChanged();
    }

    public void deleteItem(int i)
    {
        if (list == null || list.isEmpty())
            return;
        list.remove(i);
        notifyDataSetChanged();
    }

    public void deleteItem(String name)
    {
        if (list == null || list.isEmpty())
            return;
        for (foods i : list)
        {
            if (i.getName().equals(name))
            {
                list.remove(i);
                break;
            }
        }
        notifyDataSetChanged();
    }


    class ViewHolder
    {
        public TextView foodName;
        public TextView foodKind;
    }

}
