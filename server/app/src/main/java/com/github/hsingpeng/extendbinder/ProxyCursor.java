package com.github.hsingpeng.extendbinder;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

import com.github.hsingpeng.extendbinder.proto.CursorMsg;

import java.util.UUID;

/**
 * Cursor代理类，用于原生应用请求扩展Cursor
 * Created by deep on 2018/2/12.
 */

public class ProxyCursor implements Cursor {
    private final UUID mUuid;
    private final AppInfo mAppInfo;

    public ProxyCursor(UUID uuid, AppInfo appInfo) {
        this.mUuid = uuid;
        this.mAppInfo = appInfo;
    }

    private Object methodProxy(CursorMsg.Request.Method method, String argName, Object arg, String retClass) {
        UUID uuid = mUuid;
        ControlService.ClientThread thread = mAppInfo.getThread();
        ControlService.getProxyMap().put(uuid, this);
        CursorMsg.Request.Builder builder = CursorMsg.Request.newBuilder();
        builder.setUuid(uuid.toString())
                .setMethod(method);

        if (argName != null) {
            switch (argName) {
                case "offset":
                    builder.setOffset((int) arg);
                    break;
                case "position":
                    builder.setPosition((int) arg);
                    break;
                case "columnName":
                    builder.setColumnName((String) arg);
                    break;
                case "columnIndex":
                    builder.setColumnIndex((int) arg);
                    break;
            }
        }
        boolean flag = thread.requestCursor(builder.build());
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
        CursorMsg.Response response = (CursorMsg.Response) ControlService.getResultMap().get(uuid);
        CursorMsg.Response.Status status = response.getStatus();
        if (status == CursorMsg.Response.Status.Ok && retClass != null) {
            switch (retClass) {
                case "int":
                case "short":
                    return response.getIntResult();
                case "long":
                    return response.getLongResult();
                case "double":
                    return response.getDoubleResult();
                case "String":
                    return response.getStringResult();
                case "boolean":
                    return response.getBoolResult();
                case "byte[]":
                    return response.getBytesResult().toByteArray();
                case "String[]":
                    return response.getStringArrayResultList().toArray(new String[0]);
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        Object ret = methodProxy(CursorMsg.Request.Method.getCount, null, null, "int");
        if (ret == null) {
            return -1;
        }
        return (int) ret;
    }

    @Override
    public int getPosition() {
        Object ret = methodProxy(CursorMsg.Request.Method.getPosition, null, null, "int");
        if (ret == null) {
            return -1;
        }
        return (int) ret;
    }

    @Override
    public boolean move(int offset) {
        Object ret = methodProxy(CursorMsg.Request.Method.move, "offset", offset, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public boolean moveToPosition(int position) {
        Object ret = methodProxy(CursorMsg.Request.Method.moveToPosition, "position", position, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public boolean moveToFirst() {
        Object ret = methodProxy(CursorMsg.Request.Method.moveToFirst, null, null, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public boolean moveToLast() {
        Object ret = methodProxy(CursorMsg.Request.Method.moveToLast, null, null, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public boolean moveToNext() {
        Object ret = methodProxy(CursorMsg.Request.Method.moveToNext, null, null, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public boolean moveToPrevious() {
        Object ret = methodProxy(CursorMsg.Request.Method.moveToPrevious, null, null, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public boolean isFirst() {
        Object ret = methodProxy(CursorMsg.Request.Method.isFirst, null, null, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public boolean isLast() {
        Object ret = methodProxy(CursorMsg.Request.Method.isLast, null, null, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public boolean isBeforeFirst() {
        Object ret = methodProxy(CursorMsg.Request.Method.isBeforeFirst, null, null, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public boolean isAfterLast() {
        Object ret = methodProxy(CursorMsg.Request.Method.isAfterLast, null, null, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public int getColumnIndex(String columnName) {
        Object ret = methodProxy(CursorMsg.Request.Method.getColumnIndex, "columnName", columnName, "int");
        if (ret == null) {
            return -1;
        }
        return (int) ret;
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        Object ret = methodProxy(CursorMsg.Request.Method.getColumnIndexOrThrow, "columnName", columnName, "int");
        if (ret == null) {
            return -1;
        }
        return (int) ret;
    }

    @Override
    public String getColumnName(int columnIndex) {
        Object ret = methodProxy(CursorMsg.Request.Method.getColumnName, "columnIndex", columnIndex, "String");
        return (String) ret;
    }

    @Override
    public String[] getColumnNames() {
        Object ret = methodProxy(CursorMsg.Request.Method.getColumnNames, null, null, "String[]");
        if (ret == null) {
            return new String[0];
        }
        return (String[]) ret;
    }

    @Override
    public int getColumnCount() {
        Object ret = methodProxy(CursorMsg.Request.Method.getColumnCount, null, null, "int");
        if (ret == null) {
            return -1;
        }
        return (int) ret;
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        Object ret = methodProxy(CursorMsg.Request.Method.getBlob, "columnIndex", columnIndex, "byte[]");
        if (ret == null) {
            return new byte[0];
        }
        return (byte[]) ret;
    }

    @Override
    public String getString(int columnIndex) {
        Object ret = methodProxy(CursorMsg.Request.Method.getString, "columnIndex", columnIndex, "String");
        if (ret == null) {
            return null;
        }
        return (String) ret;
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        // Unsupport temporarily
    }

    @Override
    public short getShort(int columnIndex) {
        Object ret = methodProxy(CursorMsg.Request.Method.getShort, "columnIndex", columnIndex, "short");
        if (ret == null) {
            return 0;
        }
        return (short) ret;
    }

    @Override
    public int getInt(int columnIndex) {
        Object ret = methodProxy(CursorMsg.Request.Method.getInt, "columnIndex", columnIndex, "int");
        if (ret == null) {
            return 0;
        }
        return (int) ret;
    }

    @Override
    public long getLong(int columnIndex) {
        Object ret = methodProxy(CursorMsg.Request.Method.getLong, "columnIndex", columnIndex, "long");
        if (ret == null) {
            return 0;
        }
        return (long) ret;
    }

    @Override
    public float getFloat(int columnIndex) {
        Object ret = methodProxy(CursorMsg.Request.Method.getFloat, "columnIndex", columnIndex, "float");
        if (ret == null) {
            return 0;
        }
        return (float) ret;
    }

    @Override
    public double getDouble(int columnIndex) {
        Object ret = methodProxy(CursorMsg.Request.Method.getDouble, "columnIndex", columnIndex, "double");
        if (ret == null) {
            return 0;
        }
        return (double) ret;
    }

    @Override
    public int getType(int columnIndex) {
        Object ret = methodProxy(CursorMsg.Request.Method.getType, "columnIndex", columnIndex, "int");
        if (ret == null) {
            return 0;
        }
        return (int) ret;
    }

    @Override
    public boolean isNull(int columnIndex) {
        Object ret = methodProxy(CursorMsg.Request.Method.isNull, "columnIndex", columnIndex, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public void deactivate() {
        methodProxy(CursorMsg.Request.Method.deactivate, null, null, null);
    }

    @Override
    public boolean requery() {
        Object ret = methodProxy(CursorMsg.Request.Method.requery, null, null, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public void close() {
        methodProxy(CursorMsg.Request.Method.close, null, null, null);
    }

    @Override
    public boolean isClosed() {
        Object ret = methodProxy(CursorMsg.Request.Method.isClosed, null, null, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {
        // Unsupport temporarily
    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {
        // Unsupport temporarily
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        // Unsupport temporarily
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        // Unsupport temporarily
    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {
        // Unsupport temporarily
    }

    @Override
    public Uri getNotificationUri() {
        // Unsupport temporarily
        return null;
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        Object ret = methodProxy(CursorMsg.Request.Method.getWantsAllOnMoveCalls, null, null, "boolean");
        if (ret == null) {
            return false;
        }
        return (boolean) ret;
    }

    @Override
    public void setExtras(Bundle extras) {
        // Unsupport temporarily
    }

    @Override
    public Bundle getExtras() {
        // Unsupport temporarily
        return null;
    }

    @Override
    public Bundle respond(Bundle extras) {
        // Unsupport temporarily
        return null;
    }
}
