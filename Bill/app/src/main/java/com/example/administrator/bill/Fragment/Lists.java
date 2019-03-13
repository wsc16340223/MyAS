package com.example.administrator.bill.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.bill.R;
import com.example.administrator.bill.SQL.Bill;
import com.example.administrator.bill.SQL.MySQL;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Lists extends Fragment {

    List<Bill> data = new ArrayList<Bill>();
    MyAdapter myAdapter;
    ListView list;
    MySQL mySQL;
    String time;
    EditText myYear;
    EditText myMonth;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = getView().findViewById(R.id.searchList);
        mySQL = new MySQL(this.getActivity());
        query();
        listview();
    }

    protected void query(){
        myYear = (EditText) getView().findViewById(R.id.year);
        myMonth = (EditText) getView().findViewById(R.id.month);
        final Button queryBtn = (Button) getView().findViewById(R.id.queryBtn);
        final LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.linearLayout);
        //放在这里是为了再次点进来的时候，能够像第一次一样显示所有的账单
        initQuery();
        queryBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                //查询时间
                String year = myYear.getText().toString();
                String month = myMonth.getText().toString();
                time = year + "-" + month + "%";
                data = mySQL.queryDate(time);
                if (data == null || data.isEmpty()){
                    if (list.getCount() > 0){
                        myAdapter.clear();
                        list.setAdapter(myAdapter);
                    }
                    Toast.makeText(getContext(), "无查询结果!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "查询成功！", Toast.LENGTH_SHORT).show();
//                    linearLayout.setVisibility(View.GONE);
//                    queryBtn.setVisibility(View.GONE);
                    myAdapter = new MyAdapter(data);
                    list.setAdapter(myAdapter);
                }
            }
        });
    }

    private void initQuery(){
        final Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext(myYear.getText().toString() + myMonth.getText().toString());
            }
        });
        DisposableObserver<String> disposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String value) {
                if (value.isEmpty()){
                    Cursor c;
                    if(mySQL.id() != 0) {
                        c = mySQL.get();
                        do {
                            Bill item = new Bill(c.getInt(c.getColumnIndex("id")), c.getString(c.getColumnIndex("type")), Float.parseFloat(c.getString(c.getColumnIndex("money"))), c.getString(c.getColumnIndex("time")), c.getString(c.getColumnIndex("tip")), c.getInt(c.getColumnIndex("style")), c.getBlob(c.getColumnIndex("pic")));
                            data.add(item);
                        }while (c.moveToNext());
                    }
                    myAdapter = new MyAdapter(data);
                    list.setAdapter(myAdapter);
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

    protected void listview() {
        final AlertDialog.Builder alertbuild1 = new AlertDialog.Builder(this.getActivity());
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertbuild1.setTitle("备注").setMessage(mySQL.getTip(data.get(position).getId())).setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).create().show();
            }
        });
        final AlertDialog.Builder alertbuild2 = new AlertDialog.Builder(this.getActivity());
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                alertbuild2.setTitle("删除").setMessage("是否删除该条账目信息？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                        Float value = sharedPreferences.getFloat("total", 0);
                        if(data.get(position).getStyle() == 0) {
                            value -= data.get(position).getMoney();
                        }
                        else {
                            value += data.get(position).getMoney();
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putFloat("total", value);
                        //切记最后要使用commit方法将数据写入文件
                        editor.commit();
                        mySQL.delete(position);
                        myAdapter.remove(position);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }).create().show();
                return true;
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        compositeDisposable.clear();
    }
}

