package com.github.hsingpeng.testextendbinder;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String MSG = "msg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editTextMessage = findViewById(R.id.editTextMessage);
        Button buttonActivity = findViewById(R.id.buttonActivity);
        Button buttonService = findViewById(R.id.buttonService);
        Button buttonBroadcast = findViewById(R.id.buttonBroadcast);
        Button buttonContentProvider = findViewById(R.id.buttonContentProvider);
        Button button2Activity = findViewById(R.id.button2Activity);
        Button button3Activity = findViewById(R.id.button3Activity);
        buttonActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.example.binder", "com.example.binder.mainactivity");
                intent.putExtra(MSG, editTextMessage.getText().toString());
                MainActivity.this.startActivity(intent);
            }
        });
        buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.example.binder", "com.example.binder.service1");
                intent.putExtra(MSG, editTextMessage.getText().toString());
                MainActivity.this.startService(intent);
            }
        });
        buttonBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.example.binder.broadcast1");
                intent.putExtra(MSG, editTextMessage.getText().toString());
                MainActivity.this.sendBroadcast(intent);
            }
        });
        buttonContentProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = getContentResolver().query(
                        Uri.parse("content://com.github.hsingpeng.extendbinder.proxycontentprovider/contentprovider1")
                        , null, null, null, null);
                cursor.moveToFirst();
                String s = cursor.getString(0);
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        button2Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main2Activity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        button3Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Main3Activity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}