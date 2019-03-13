package com.group33;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Heros temp = new Heros("无间傀儡", "元歌" ,"刺客", 1 , 3 ,4 ,5, 3, "上路，中路");

        TextView hello = findViewById(R.id.hello);
        hello.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Hero", temp);
                Intent intent = new Intent(MainActivity.this, Infomation.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });
    }
}
