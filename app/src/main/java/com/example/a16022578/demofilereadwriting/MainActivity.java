package com.example.a16022578.demofilereadwriting;

import android.Manifest;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {
    Button btnRead;
    Button btnWrite;
    TextView tvText;
    String folderLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        tvText = (TextView)findViewById(R.id.tv);

        createFolder();

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Code for file writing
                File targetFile = new File(folderLocation, "data.txt");
                try {
                    FileWriter writer = new FileWriter(targetFile, true);
                    writer.write("HELLO MIC TEST" + "\n");
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to write", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File targetFile = new File(folderLocation, "data.txt");
                 //FILE READ
                if (targetFile.exists() == true){
                    String data ="";
                    try {
                        FileReader reader = new FileReader(targetFile);
                        BufferedReader br = new BufferedReader(reader);
                        String line = br.readLine();
                        while (line != null){
                            data += line + "\n";
                            line = br.readLine();
                        }
                        br.close();
                        reader.close();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Failed to read!",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    tvText.setText(data);
                }

            }
        });

    }
    //PERMISSION TO READ AND CREATE
    private void createFolder(){
        int permissionCheck_Storage = ContextCompat.checkSelfPermission(

                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck_Storage != PermissionChecker.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            finish();
        }
        folderLocation = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/MyFolder";
        File folder = new File(folderLocation);
        if (folder.exists() == false){
            boolean result = folder.mkdir();
            if (result == true){
                Log.d("File Read/Write", "Folder created");
            } else {
                Log.e("File Read/Write", "Folder creation failed");
            }
        }
    }

}
