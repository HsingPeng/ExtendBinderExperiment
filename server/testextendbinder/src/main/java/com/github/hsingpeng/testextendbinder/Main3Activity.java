package com.github.hsingpeng.testextendbinder;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = "Main3Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        final EditText editTextTimes = findViewById(R.id.editTextTimes);
        Button buttonSendToLinux = findViewById(R.id.buttonSendToLinux);
        Button buttonSendToAndroid = findViewById(R.id.buttonSendToAndroid);
        Button buttonSendLinuxContentProvider = findViewById(R.id.buttonSendLinuxContentProvider);
        Button buttonSendAndroidContentProvider = findViewById(R.id.buttonSendAndroidContentProvider);
        buttonSendToLinux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timesStr = editTextTimes.getText().toString();
                if (timesStr.equals("")) {
                    Toast.makeText(Main3Activity.this, "times is Empty.", Toast.LENGTH_SHORT).show();
                } else {
                    int time = Integer.parseInt(timesStr);
                    Log.d(TAG, "start send linux");
                    for (int i=0; i<time; i++) {
                        Intent intent = new Intent();
                        intent.setClassName("com.example.binder", "com.example.binder.mainactivity");
                        Main3Activity.this.startActivity(intent);
                    }
                }

            }
        });
        buttonSendToAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timesStr = editTextTimes.getText().toString();
                if (timesStr.equals("")) {
                    Toast.makeText(Main3Activity.this, "times is Empty.", Toast.LENGTH_SHORT).show();
                } else {
                    int time = Integer.parseInt(timesStr);
                    Log.d(TAG, "start send android");
                    for (int i=0; i<time; i++) {
                        Intent intent = new Intent();
                        intent.setClassName("com.github.hsingpeng.extendbinder", "com.github.hsingpeng.extendbinder.MainActivity");
                        Main3Activity.this.startActivity(intent);
                    }
                }
            }
        });
        buttonSendLinuxContentProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timesStr = editTextTimes.getText().toString();
                if (timesStr.equals("")) {
                    Toast.makeText(Main3Activity.this, "times is Empty.", Toast.LENGTH_SHORT).show();
                } else {
                    int time = Integer.parseInt(timesStr);
                    Log.d(TAG, "start send linux");
                    Log.d(TAG, "PERFORMACE_TEST:linux:start");
                    for (int i=0; i<time; i++) {
                        String s = getContentResolver().getType(
                                Uri.parse("content://com.github.hsingpeng.extendbinder.proxycontentprovider/contentprovider1"));
                    }
                    Log.d(TAG, "PERFORMACE_TEST:linux:finish");
                }
            }
        });
        buttonSendAndroidContentProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timesStr = editTextTimes.getText().toString();
                if (timesStr.equals("")) {
                    Toast.makeText(Main3Activity.this, "times is Empty.", Toast.LENGTH_SHORT).show();
                } else {
                    int time = Integer.parseInt(timesStr);
                    Log.d(TAG, "PERFORMACE_TEST:android:start");
                    for (int i=0; i<time; i++) {
                        String s = getContentResolver().getType(Uri.parse("content://com.example.testcontentprovider1/test"));
                    }
                    Log.d(TAG, "PERFORMACE_TEST:android:finish");
                }
            }
        });
    }
}
