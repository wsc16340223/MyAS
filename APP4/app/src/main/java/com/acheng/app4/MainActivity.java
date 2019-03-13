package com.acheng.app4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private myDB mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RadioButton login = findViewById(R.id.login);
        final RadioButton register = findViewById(R.id.register);
        final EditText userName = findViewById(R.id.userName);
        final EditText pass1 = findViewById(R.id.pass1);
        final EditText pass2 = findViewById(R.id.pass2);
        final ImageView userImg = findViewById(R.id.regisImg);
        final Button ok = findViewById(R.id.ok);
        final Button clear = findViewById(R.id.clear);
        mydb = new myDB(this);

        login.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                userImg.setVisibility(View.GONE);
                pass2.setVisibility(View.GONE);
                pass1.setHint("Password");
                pass1.setText(null);
                pass2.setText(null);
            }
        });
        register.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                userImg.setVisibility(View.VISIBLE);
                pass1.setHint("New Password");
                pass2.setVisibility(View.VISIBLE);
                pass1.setText(null);
                pass2.setText(null);
            }
        });

        ok.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                String name = userName.getText().toString();
                String password = pass1.getText().toString();

                if (name.isEmpty())
                {
                    Toast.makeText(getApplication(), "Username cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty())
                {
                    Toast.makeText(getApplication(), "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                }

                else if (login.isChecked())
                {
                    if (mydb.queryDB(name) == null)
                    {
                        Toast.makeText(getApplication(), "Username not existed.", Toast.LENGTH_SHORT).show();
                    }
                    else if (mydb.queryDB(name) != null && !mydb.queryDB(name).getPass().equals(password))
                    {
                        Toast.makeText(getApplication(), "Invalid Password.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplication(),"Correct Password.",Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", mydb.queryDB(name));
                        Intent intent = new Intent(MainActivity.this, Comments.class);
                        intent.putExtras(bundle);

                        startActivityForResult(intent, 0);
                    }
                }
                else if (register.isChecked())
                {
                    if (!password.equals(pass2.getText().toString()))
                    {
                        Toast.makeText(getApplication(), "Password Mismatch.", Toast.LENGTH_SHORT).show();
                    }
                    else if (mydb.queryDB(name) != null)
                    {
                        Toast.makeText(getApplication(), "Username already existed.", Toast.LENGTH_SHORT).show();
                    }
                    else if (mydb.queryDB(name) == null)
                    {
                        mydb.insert2DB(name, password);
                        Toast.makeText(getApplication(),"Register success...",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        clear.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                userName.setText(null);
                pass1.setText(null);
                pass2.setText(null);
            }
        });
    }
}
