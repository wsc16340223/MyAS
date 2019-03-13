package com.acheng.app3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FileEditor extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_editor);

        final SharedPreferences sharedPreferences = getSharedPreferences("fileEditor", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        final EditText editText = findViewById(R.id.editText);
        Button saveBtn = findViewById(R.id.saveBtn);
        Button loadBtn = findViewById(R.id.loadBtn);
        Button clearBtn = findViewById(R.id.clearBtn);

        saveBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                editor.putString("fileEditor", editText.getText().toString());
                editor.commit();
                Toast.makeText(getApplication(), "Save successfully.", Toast.LENGTH_SHORT).show();
            }
        });

        loadBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if (sharedPreferences.getString("fileEditor", null) == null)
                {
                    Toast.makeText(getApplication(), "Failed to load file.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    editText.setText(sharedPreferences.getString("fileEditor", null));
                    Toast.makeText(getApplication(), "Load successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                editText.setText("");
            }
        });
    }
}
