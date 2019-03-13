package com.example.administrator.bill.Fragment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.bill.R;
import com.example.administrator.bill.SQL.Bill;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<Bill> myList = new ArrayList<Bill>();

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView time, type, money;
        ImageView picture;

        public ViewHolder(View view) {
            super(view);
            time = (TextView)view.findViewById(R.id.time);
            type = (TextView)view.findViewById(R.id.type);
            money = (TextView)view.findViewById(R.id.money);
            picture = (ImageView)view.findViewById(R.id.picture);
        }
    }

    public MyAdapter(List<Bill> myList)
    {
        super();
        this.myList= myList;
    }

    public MyAdapter(){
        super();
    }

    @Override
    public int getCount() {
        return  myList.size();
    }

    @Override
    public Object getItem(int position) {
        return  myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder item = null;
        if(convertView ==null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
            item = new ViewHolder(convertView);
            convertView.setTag(item);//绑定ViewHolder对象
        }
        else
        {
            item = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }
        Bill ope = myList.get(position);
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        item.time.setText(ope.getTime());
        item.type.setText(ope.getType());
        if(ope.getStyle() == 0) {
            item.money.setText("+ " + ope.getMoney());
            item.money.setTextColor(Color.RED);
        }
        else {
            item.money.setText("- " + ope.getMoney());
            item.money.setTextColor(Color.GREEN);
        }
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = false ;
        Bitmap bitmap = BitmapFactory.decodeByteArray(ope.getPic(), 0, ope.getPic().length, opts);
        item.picture.setImageBitmap(bitmap);
        return convertView;
    }

    public void add(Bill bill) {
        myList.add(bill);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        myList.remove(position);
        notifyDataSetChanged();
    }

    public void clear(){
        myList.clear();
        notifyDataSetChanged();
    }
}