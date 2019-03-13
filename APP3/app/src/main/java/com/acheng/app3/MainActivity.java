package com.acheng.app3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("savePassword", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final String myPassword = sharedPreferences.getString("Password", null);

        Button ok = findViewById(R.id.ok);
        Button clear = findViewById(R.id.clear);
        final EditText pass1 = findViewById(R.id.pass1);
        final EditText pass2 = findViewById(R.id.pass2);
        if (myPassword == null)
        {
            ok.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    if (pass1.getText().toString().isEmpty())
                    {
                        Toast.makeText(getApplication(), "Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    }
                    else if (!pass1.getText().toString().equals(pass2.getText().toString()))
                    {
                        Toast.makeText(getApplication(), "Password Mismatch.", Toast.LENGTH_SHORT).show();
                    }
                    else if (pass1.getText().toString().equals(pass2.getText().toString()))
                    {
                        editor.putString("Password", pass1.getText().toString());
                        editor.commit();
                        //转入编辑界面
                        Intent intent = new Intent(MainActivity.this, FileEditor.class);
                        startActivityForResult(intent, 0);
                    }
                }
            });

            clear.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    pass1.setText("");
                    pass2.setText("");
                }
            });
        }
        else
        {
            pass1.setVisibility(View.INVISIBLE);
            pass2.setHint("Password");

            ok.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    if (pass2.getText().toString().equals(myPassword))
                    {
                        //转入编辑界面
                        Intent intent = new Intent(MainActivity.this, FileEditor.class);
                        startActivityForResult(intent, 0);
                    }
                    else
                        Toast.makeText(getApplication(), "Invalid Password.", Toast.LENGTH_SHORT).show();
                }
            });
            clear.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    pass2.setText("");
                }
            });
        }
    }
}
