package com.github.hsingpeng.extendbinder;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.github.hsingpeng.extendbinder.proto.ContentProviderMsg;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class ProxyContentProvider extends ContentProvider {
    private static final String TAG = "ProxyContentProvider";

    public ProxyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        ContentProviderMsg.Request.Builder builder = ContentProviderMsg.Request.newBuilder();
        builder.setMethod(ContentProviderMsg.Request.Method.delete)
                .setUri(uri.getPath());
        if (selection != null) {
            builder.setSelection(selection);
        }
        if (selectionArgs != null) {
            builder.addAllSelectionArgs(Arrays.asList(selectionArgs));
        }
        Object ret = proxyMethod(uri, builder);
        if (ret == null) {
            return 0;
        }
        return (int) ret;
    }

    @Override
    public String getType(Uri uri) {
        ContentProviderMsg.Request.Builder builder = ContentProviderMsg.Request.newBuilder();
        builder.setMethod(ContentProviderMsg.Request.Method.getType)
                .setUri(uri.getPath());
        return (String) proxyMethod(uri, builder);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        ContentProviderMsg.Request.Builder builder = ContentProviderMsg.Request.newBuilder();
        builder.setMethod(ContentProviderMsg.Request.Method.insert)
                .setUri(uri.getPath());
        for (Map.Entry<String, Object> entry : values.valueSet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                builder.putValues(key, "");
            } else {
                builder.putValues(key, value.toString());
            }
        }
        Object ret = proxyMethod(uri, builder);
        if (ret == null) {
            return null;
        }
        return Uri.parse(ret.toString());
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        ContentProviderMsg.Request.Builder builder = ContentProviderMsg.Request.newBuilder();
        builder.setMethod(ContentProviderMsg.Request.Method.query)
                .setUri(uri.getPath());
        if (projection != null) {
            builder.addAllProjection(Arrays.asList(projection));
        }
        if (selection != null) {
            builder.setSelection(selection);
        }
        if (selectionArgs != null) {
            builder.addAllSelectionArgs(Arrays.asList(selectionArgs));
        }
        if (sortOrder != null) {
            builder.setSortOrder(sortOrder);
        }
        return (Cursor) proxyMethod(uri, builder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        ContentProviderMsg.Request.Builder builder = ContentProviderMsg.Request.newBuilder();
        builder.setMethod(ContentProviderMsg.Request.Method.update)
                .setUri(uri.getPath());
        for (Map.Entry<String, Object> entry : values.valueSet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                builder.putValues(key, "");
            } else {
                builder.putValues(key, value.toString());
            }
        }
        if (selection != null) {
            builder.setSelection(selection);
        }
        if (selectionArgs != null) {
            builder.addAllSelectionArgs(Arrays.asList(selectionArgs));
        }
        Object ret = proxyMethod(uri, builder);
        if (ret == null) {
            return 0;
        }
        return (int)ret;
    }

    private Object proxyMethod(Uri uri, ContentProviderMsg.Request.Builder builder) {
        AppInfo appInfo = ControlService.getContentProviderMap().get(uri.getPath());
        if (appInfo == null) {
            Log.e(TAG, "No such ContentProvider: uri=" + uri.toString());
            return null;
        }

        if (appInfo.getStatus() == AppStatus.STOP) {
            Bundle bundle = new Bundle();
            bundle.putInt(ControlService.WHAT, ControlService.LAUNCH_APP);
            bundle.putString(ControlService.APP_NAME, appInfo.getName());

            Intent intent = new Intent(this.getContext(), ControlService.class);
            intent.putExtra(ControlService.DATA, bundle);
            this.getContext().startService(intent);

            synchronized (appInfo) {
                try {
                    appInfo.wait(ControlService.RPC_WAIT_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (appInfo.getStatus() != AppStatus.STARTED) {
            Log.d(TAG, "App status error:" + appInfo.getName());
            return null;
        }

        ControlService.ClientThread clientThread = appInfo.getThread();

        UUID uuid = UUID.randomUUID();
        ControlService.getProxyMap().put(uuid, this);
        builder.setUuid(uuid.toString());
        boolean flag = clientThread.requestContentProvider(builder.build());
        if (flag) {
            synchronized (this) {
                try {
                    this.wait(ControlService.RPC_WAIT_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ControlService.getProxyMap().remove(uuid);
        return ControlService.getResultMap().get(uuid);
    }
}
