package com.acheng.app5;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button search;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        search = findViewById(R.id.searchBtn);
        recyclerView = findViewById(R.id.recycler);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断网络是否连接
                ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected()){
                    Toast.makeText(getApplication(), "网络连接失败", Toast.LENGTH_SHORT).show();
                }
                else{
                    //判断输入是否是数据
                    String text = editText.getText().toString();
                    Pattern pattern = Pattern.compile("[0-9]*");
                    Matcher matcher = pattern.matcher(text);
                    if (!matcher.matches()){
                        Toast.makeText(getApplication(), "需要整数类型数据", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (Integer.parseInt(text) <= 0){
                            Toast.makeText(getApplication(),"需要大于0的user_id",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }
}
