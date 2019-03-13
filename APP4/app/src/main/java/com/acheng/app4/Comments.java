package com.acheng.app4;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Comments extends AppCompatActivity {
    private User myUser;
    private ListView listView;
    private MyAdapter myAdapter;
    private myDB mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        myUser = (User) getIntent().getSerializableExtra("user");

        listView = findViewById(R.id.listView);
        mydb = new myDB(this);

        myAdapter = new MyAdapter();
        init();
        Button send = findViewById(R.id.sendBtn);
        final EditText editText = findViewById(R.id.editComment);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                // 读取通信录
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = \"" + myAdapter.getItem(i).getName() + "\"", null, null);
                cursor.moveToFirst();
                String message = "Username: " + myAdapter.getItem(i).getName() + "\nPhone: ";
                if(cursor.getCount() >= 1) {
                    while (!cursor.isAfterLast()) {
                        message += cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) + "         ";
                        cursor.moveToNext();
                    }
                    cursor.close();
                } else {
                    message += "number not exist";
                }
                // 提示框
                AlertDialog dialog = new AlertDialog.Builder(Comments.this).create();//创建对话框
                dialog.setIcon(R.mipmap.ic_launcher);//设置对话框icon
                dialog.setTitle("Info");//设置对话框标题
                dialog.setMessage(message);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(CommentActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();//关闭对话框
                    }
                });
                dialog.show();//显示对话框
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l)
            {
                final AlertDialog.Builder myDialog = new AlertDialog.Builder(Comments.this);
                if (myUser.getName().equals(myAdapter.getItem(i).getName()))
                {
                    myDialog.setMessage("Delete or not?").setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(mydb.delete(i) == 1)
                                        myAdapter.deleteItem(i);
                                    init();
                                }
                            }).setNegativeButton("NO", null).create().show();
                }
                else
                {
                    myDialog.setMessage("Report or not?").setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getApplicationContext(), "Already reported.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("NO",null).create().show();
                }
                return true;
            }
        });
        send.setOnClickListener(new Button.OnClickListener(){
            @Override
            public  void onClick(View view)
            {
                if (editText.getText().toString().isEmpty())
                    return;
                CommentInfo temp = new CommentInfo();
                temp.setName(myUser.getName());
                temp.setComment(editText.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date current = new Date();
                String time = sdf.format(current.getTime());
                temp.setTime(time);
                Random random = new Random();
                Integer i = random.nextInt(10);
                temp.setLikeCount(i);

                mydb.insert2DB(myUser.getName(),time,editText.getText().toString(),i);
                myAdapter.addItem(temp);
                init();
                editText.setText(null);

            }
        });
    }
    public void init()
    {
        myAdapter.reLoad(mydb.get());
        listView.setAdapter(myAdapter);
    }
}
