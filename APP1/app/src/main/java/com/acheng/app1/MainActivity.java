package com.acheng.app1;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;


public class MainActivity extends AppCompatActivity{
      //方式二
//    private Button button;
//    private RadioGroup RG;
//    private RadioButton RB1;
//    private RadioButton RB2;
//    private RadioButton RB3;
//    private RadioButton RB4;
//    private EditText editText;

    //在onCreate函数之外的话，运行会闪退
//    Button button = (Button) findViewById(R.id.button);
//    RadioGroup RG = (RadioGroup) findViewById(R.id.RG);
//    RadioButton RB1 = (RadioButton) findViewById(R.id.RB1);
//    RadioButton RB2 = (RadioButton) findViewById(R.id.RB2);
//    RadioButton RB3 = (RadioButton) findViewById(R.id.RB3);
//    RadioButton RB4 = (RadioButton) findViewById(R.id.RB4);
//    EditText editText = (EditText)findViewById(R.id.editText);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);
        //方式一
        Button button = (Button) findViewById(R.id.button);
        final RadioGroup RG = (RadioGroup) findViewById(R.id.RG);
        RadioButton RB1 = (RadioButton) findViewById(R.id.RB1);
        RadioButton RB2 = (RadioButton) findViewById(R.id.RB2);
        RadioButton RB3 = (RadioButton) findViewById(R.id.RB3);
        RadioButton RB4 = (RadioButton) findViewById(R.id.RB4);
        final EditText editText = (EditText)findViewById(R.id.editText);

          //方式二续
//        button = super.findViewById(R.id.button);
//        RG = super.findViewById(R.id.RG);
//        RB1 = super.findViewById(R.id.RB1);
//        RB2 = super.findViewById(R.id.RB2);
//        RB3 = super.findViewById(R.id.RB3);
//        RB4 = super.findViewById(R.id.RB4);
//        editText = super.findViewById(R.id.editText);

        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.RB1:
                        Toast.makeText(getApplication(), R.string.image1,
                            Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RB2:
                        Toast.makeText(getApplication(), R.string.video1,
                            Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RB3:
                        Toast.makeText(getApplication(), R.string.question1,
                            Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RB4:
                        Toast.makeText(getApplication(), R.string.message1,
                            Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        //对话框
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("提示");
        //写在点击事件外面的对话框，这里更改的message无效
//        RadioButton checkedB = findViewById(RG.getCheckedRadioButtonId());
//        if (editText.getText().toString().equals("Health")) {
//            alertDialog.setMessage(checkedB.getText() + "搜索成功");
//        }
//        else {
//            alertDialog.setMessage("搜索失败");
//        }
        alertDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "对话框“确定”按钮被点击",Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "对话框“取消”按钮被点击",Toast.LENGTH_SHORT).show();
                    }
                }).create();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton checkedB = findViewById(RG.getCheckedRadioButtonId());
                //搜索内容不为空
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplication(), R.string.empty,
                            Toast.LENGTH_SHORT).show();
                }
                else if (editText.getText().toString().equals("Health")) {
                    alertDialog.setMessage(checkedB.getText() + "搜索成功");
                    alertDialog.show();
                }
                else {
                    alertDialog.setMessage("搜索失败");
                    alertDialog.show();
                }


            }
        });
    }
}
