package com.example.files;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnsave;
    Button btnload;
    EditText et;
    TextView tv;
    FileOutputStream out;
    InputStream in;
    String fileText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnsave = findViewById(R.id.btnsave);
        btnload = findViewById(R.id.btnload);
        et = findViewById(R.id.etTitle);
        tv = findViewById(R.id.textView1);
        btnsave.setOnClickListener(this);
        btnload.setOnClickListener(this);
        logAllFiles();
    }

    public void logAllFiles()
    {
        File mydir = this.getFilesDir();
        File lister = mydir.getAbsoluteFile();
        for (String list : lister.list())
        {
            Log.d("files", list);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnsave) {
            try {
                fileText = et.getText().toString();
                out = openFileOutput("Remus", MODE_PRIVATE|MODE_APPEND);
                if (fileText != null) {
                    try {
                        out.write(fileText.getBytes(), 0, fileText.length());
                        out.close();

                        in = openFileInput("Remus");
                        byte[] buffer = new byte[4096];
                        try {
                            in.read(buffer);
                            fileText = new String(buffer);
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(this, fileText + " saved to file", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (btnload == v) {
            try {
                in = openFileInput("Remus");
                byte[] buffer = new byte[4096];
                try {
                    in.read(buffer);
                    fileText = new String(buffer);
                    in.close();
                    if (fileText != null)
                        tv.setText(fileText);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
