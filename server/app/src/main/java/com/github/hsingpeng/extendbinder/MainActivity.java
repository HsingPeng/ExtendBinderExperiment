package com.github.hsingpeng.extendbinder;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.hsingpeng.xposedhook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AppInfoHelper helper;
    private LinearLayout layout;

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "Get Intent");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new AppInfoHelper(this);
        helper.setDataBase();
        setContentView(R.layout.activity_main);
        Button buttonAdd = findViewById(R.id.button_add);
        layout = findViewById(R.id.LinearLayout);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.add_app);
                View layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_dialog
                        , null);
                final EditText editTextAppName = layout.findViewById(R.id.editTextAppName);
                final EditText editTextAppPath = layout.findViewById(R.id.editTextAppPath);
                builder.setView(layout);
                builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String appName = editTextAppName.getText().toString();
                        String appPath = editTextAppPath.getText().toString();
                        if (appName.equals("")) {
                            Toast.makeText(MainActivity.this, "App Name is empty."
                                    , Toast.LENGTH_LONG).show();
                        } else if (appPath.equals("")) {
                            Toast.makeText(MainActivity.this, "App Path is empty."
                                    , Toast.LENGTH_LONG).show();
                        } else {
                            helper.insert(appName, appPath);
                            showAppInfo();
                        }
                    }
                });
                builder.setNeutralButton("取消", null);
                builder.show();
            }
        });
        this.showAppInfo();

        Intent intent = new Intent(MainActivity.this, ControlService.class);
        this.startService(intent);
    }

    private void showAppInfo() {
        layout.removeAllViews();
        List<String[]> list = helper.queryAll();
        Map<String, AppInfo> appMap = ControlService.getAppMap();
        for (String[] s: list) {
            String appName = s[0];
            String appPath = s[1];
            addView(appName, appPath);
            AppInfo appInfo = appMap.get(appName);
            if (appInfo == null) {
                appMap.put(appName, new AppInfo(appName, appPath));
            } else {
                appInfo.setPath(appPath);
            }
        }
    }

    private void addView(final String appName, final String appPath) {
        LinearLayout parent = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 0, 8, 0);
        parent.setLayoutParams(params);
        TextView viewName = new TextView(this);
        viewName.setLayoutParams(params);
        TextView viewPath = new TextView(this);
        viewPath.setLayoutParams(params);
        Button buttonDelete = new Button(this);
        buttonDelete.setLayoutParams(params);
        buttonDelete.setText("删除");
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.delete(appName);
                showAppInfo();
            }
        });
        viewName.setText(this.getResources().getText(R.string.appName) + ":" + appName);
        viewPath.setText(this.getResources().getText(R.string.appPath) + ":" + appPath);
        parent.addView(viewName);
        parent.addView(viewPath);
        parent.addView(buttonDelete);
        layout.addView(parent);
    }
}

class AppInfoHelper extends SQLiteOpenHelper {
    public static final String table = "appinfo";
    public static final String appName = "appname";
    public static final String appPath = "apppath";
    private SQLiteDatabase db = null;

    public AppInfoHelper(Context context) {
        super(context, table, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + table + "(id integer primary key autoincrement,"
                + appName + " varchar(500)," + appPath + " varchar(500))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void setDataBase() {
        this.db = this.getWritableDatabase();
    }

    public void insert(String name, String path) {
        if (db == null) {
            return;
        }
        ContentValues value = new ContentValues();
        value.put(appName, name);
        value.put(appPath, path);
        db.insert(table, null, value);
    }

    public void delete(String name) {
        if (db == null) {
            return;
        }
        String whereClause = appName + "=?";
        String[] whereArgs = {name};
        db.delete(table, whereClause, whereArgs);
    }

    public List<String[]> queryAll() {
        List<String[]> list = new ArrayList<>();
        if (db == null) {
            return list;
        }
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getCount();
            for (int i=0 ; i<count; i++) {
                String name = cursor.getString(1);
                String path = cursor.getString(2);
                list.add(new String[]{name, path});
                cursor.moveToNext();
            }
        }
        return list;
    }
}
