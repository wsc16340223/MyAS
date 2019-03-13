package com.acheng.app2;

import java.util.List;
import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>
{
    private List<foods> datas;
    //private foods temp;
    private MyRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter()
    {
        initData();
    }
    protected void initData()
    {
        datas = new ArrayList<>();
        datas.add(new foods("大豆","粮", "粮食","蛋白质", "#BB4C3B"));
        datas.add(new foods("十字花科蔬菜","蔬", "蔬菜","维生素C", "#C48D30"));
        datas.add(new foods("牛奶","饮", "饮品","钙", "#4469B0"));
        datas.add(new foods("海鱼","肉", "肉食","蛋白质", "#20A17B"));
        datas.add(new foods("菌菇类","蔬", "蔬菜","微量元素", "#BB4C3B"));
        datas.add(new foods("番茄","蔬", "蔬菜","番茄红素", "#4469B0"));
        datas.add(new foods("胡萝卜","蔬", "蔬菜","胡萝卜素", "#20A17B"));
        datas.add(new foods("荞麦","粮", "粮食","膳食纤维", "#BB4C3B"));
        datas.add(new foods("鸡蛋","杂", "杂","几乎所有营养物质", "#C48D30"));


//用这种逐个添加之后，程序闪退
//        temp.setAll("大豆","粮", "粮食","蛋白质", "#BB4C3B");
//        datas.add(temp);
//
//
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        holder.food_kind.setText(datas.get(position).getkName());
        holder.food_name.setText(datas.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if (onItemClickListener != null)
                {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                if (onItemClickListener != null)
                {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    public void addItem(foods food)
    {
        if (datas == null)
        {
            datas = new ArrayList<>();
        }
        datas.add(0,food);
        notifyItemInserted(0);
    }
    //删除对应food
    public void deleteItem(int pos)
    {
        if (datas == null || datas.isEmpty())
            return;
        datas.remove(pos);
        notifyItemRemoved(pos);
    }
    //返回对应位置的food
    public foods getItem(int pos)
    {
        if (datas == null || datas.isEmpty())
            return null;
        return datas.get(pos);
    }

    //用来更新主页面的数据，更改collect的值
    public boolean updateData(String name, Boolean collect)
    {
        for(foods i : datas)
        {
            if (i.getName().equals(name))
            {
                if (i.getCollect().equals(collect))
                    return false;
                i.setCollect(collect);
                return true;
            }
        }
        return false;
    }
    //设置监听器
    public void setOnItemClickListener(MyRecyclerViewAdapter.OnItemClickListener listener)
    {
        this.onItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView food_kind;
        TextView food_name;

        public MyViewHolder(View view)
        {
            super(view);
            food_kind = view.findViewById(R.id.food_kind);
            food_name = view.findViewById(R.id.food_name);
        }
    }
}
