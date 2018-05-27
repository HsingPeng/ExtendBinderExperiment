package com.github.hsingpeng.extendbinder;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.hsingpeng.extendbinder.proto.AppInfoMsg;
import com.github.hsingpeng.extendbinder.proto.IntentMsg;
import com.github.hsingpeng.xposedhook.ICallBack;
import com.github.hsingpeng.xposedhook.IServiceAidlInterface;
import com.github.hsingpeng.extendbinder.proto.ContentProviderMsg;
import com.github.hsingpeng.extendbinder.proto.CursorMsg;
import com.google.protobuf.ByteString;
import com.google.protobuf.ProtocolStringList;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ControlService extends Service {
    public static final String LAUNCH_APP_NAME = "com.github.hsingpeng.extendbinder.linux.launcher";
    public static final String TAG = "ControlService";

    public static final String WHAT = "what";
    public static final String DATA = "data";
    public static final String APP_NAME = "app_name";

    public static final int LAUNCH_APP = 1;
    public static final int START_ACTIVITY = 2;
    public static final int START_SERVICE = 3;
    public static final int SEND_BROADCASRT = 4;

    public static final long RPC_WAIT_TIME = 10000L;

    private Handler mHandler = new ServiceHandler();
    private static Map<UUID, Object> mProxyMap = new ConcurrentHashMap<>();
    private static Map<UUID, Object> mResultMap = new ConcurrentHashMap<>();

    private static Map<String, AppInfo> mAppMap = new ConcurrentHashMap<>();
    private static Map<String, AppInfo> mActivityMap = new ConcurrentHashMap<>();
    private static Map<String, AppInfo> mServiceMap = new ConcurrentHashMap<>();
    private static Map<String, AppInfo> mBroadcastMap = new ConcurrentHashMap<>();
    private static Map<String, AppInfo> mContentProviderMap = new ConcurrentHashMap<>();

    private static ExecutorService mThreadPool = Executors.newCachedThreadPool();
    private static SocketServerThread mSocketServerThread;
    private static LaunchThread mLaunchThread = null;
    private ICallBack intentCallback = null;

    public ControlService() {
    }

    public Handler getHandler() {
        return mHandler;
    }

    public static Map<String, AppInfo> getAppMap() {
        return mAppMap;
    }

    public static Map<String, AppInfo> getContentProviderMap() {
        return mContentProviderMap;
    }

    public static Map<UUID, Object> getProxyMap() {
        return mProxyMap;
    }

    public static Map<UUID, Object> getResultMap() {
        return mResultMap;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
        mSocketServerThread = new SocketServerThread();
        mThreadPool.execute(mSocketServerThread);
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
        builder.setContentTitle("Extend Binder");
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
        startForeground(1, builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocketServerThread.stopIt();
        mThreadPool.shutdown();
        try {
            mThreadPool.awaitTermination(5L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Message msg = mHandler.obtainMessage();
            Bundle bundle = intent.getBundleExtra(DATA);
            if (bundle != null) {
                msg.setData(bundle);
                msg.what = bundle.getInt(ControlService.WHAT);
                mHandler.sendMessage(msg);
            }
        }
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceAidlInterface();
    }

    private class ServiceAidlInterface extends IServiceAidlInterface.Stub {

        @Override
        public void startActivity(Intent intent) throws RemoteException {
            Message msg = mHandler.obtainMessage();
            msg.what = ControlService.START_ACTIVITY;
            Bundle bundle = new Bundle();
            bundle.putParcelable(ControlService.DATA, intent);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }

        @Override
        public void startService(Intent intent) throws RemoteException {
            Message msg = mHandler.obtainMessage();
            msg.what = ControlService.START_SERVICE;
            Bundle bundle = new Bundle();
            bundle.putParcelable(ControlService.DATA, intent);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }

        @Override
        public void broadcastIntent(Intent intent) throws RemoteException {
            Message msg = mHandler.obtainMessage();
            msg.what = ControlService.SEND_BROADCASRT;
            Bundle bundle = new Bundle();
            bundle.putParcelable(ControlService.DATA, intent);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        }

        @Override
        public void registerCallBack(ICallBack callback) throws RemoteException {
            ControlService.this.intentCallback = callback;
            Log.d(TAG, "registerCallBack");
        }
    }

    private final class ServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LAUNCH_APP:
                    launchApp(msg);
                    break;
                case START_ACTIVITY:
                    hookIntent(msg, START_ACTIVITY);
                    break;
                case START_SERVICE:
                    hookIntent(msg, START_SERVICE);
                    break;
                case SEND_BROADCASRT:
                    hookIntent(msg, SEND_BROADCASRT);
                    break;
                default:
                    break;
            }
        }

        private void hookIntent(Message msg, int method) {
            Bundle bundle = msg.getData();
            Intent intent = bundle.getParcelable(ControlService.DATA);

            IntentMsg.Request.Builder builder = IntentMsg.Request.newBuilder();
            String action = intent.getAction();
            if (action != null) {
                builder.setAction(action);
            }
            ComponentName component = intent.getComponent();
            if (component != null) {
                String className = component.getClassName();
                builder.setClassName(className);
                String packageName = component.getPackageName();
                builder.setPackageName(packageName);
            }
            String type = intent.getType();
            if (type != null) {
                builder.setType(type);
            }
            Set<String> categories = intent.getCategories();
            if (categories != null) {
                for (String s : categories) {
                    builder.addCategories(s);
                }
            }
            Uri uri = intent.getData();
            if (uri != null) {
                builder.setUri(uri.toString());
            }
            Bundle extras = intent.getExtras();
            if (extras != null) {
                for (String key: extras.keySet()) {
                    Object value = extras.get(key);
                    builder.putExtras(key, value.toString());
                }
            }
            Log.d(TAG, "PERFORMACE_TEST:hookIntent:" + intent);
            AppInfo appInfo = null;
            switch (method) {
                case START_ACTIVITY:
                    builder.setMethod(IntentMsg.Request.Method.startActivity);
                    appInfo = mActivityMap.get(intent.getComponent().getClassName());
                    break;
                case START_SERVICE:
                    builder.setMethod(IntentMsg.Request.Method.startService);
                    appInfo = mServiceMap.get(intent.getComponent().getClassName());
                    break;
                case SEND_BROADCASRT:
                    builder.setMethod(IntentMsg.Request.Method.sendBroadcast);
                    appInfo = mBroadcastMap.get(intent.getAction());
                    break;
                default:
                    break;
            }
            if (appInfo == null) {
                return;
            }

            if (appInfo.getStatus() != AppStatus.STARTED) {
                Bundle _bundle = new Bundle();
                _bundle.putInt(ControlService.WHAT, ControlService.LAUNCH_APP);
                _bundle.putString(ControlService.APP_NAME, appInfo.getName());
                Message _msg = mHandler.obtainMessage();
                _msg.setData(_bundle);
                _msg.what = _bundle.getInt(ControlService.WHAT);
                mHandler.sendMessage(_msg);
                Log.d(TAG, "Intent AppStatus=STOP:" + appInfo.getName());
            } else {
                appInfo.getThread().requestIntent(builder.build());
            }
        }

        private void launchApp(Message msg) {
            Bundle bundle = msg.getData();
            String appName = bundle.getString(ControlService.APP_NAME);
            AppInfo appInfo = mAppMap.get(appName);
            boolean flag;
            if (mLaunchThread == null) {
                flag = false;
            } else {
                flag = mLaunchThread.launchApp(appInfo);
            }
            if (!flag) {
                synchronized (appInfo) {
                    appInfo.notifyAll();
                }
            }
        }
    }

    private class LaunchThread implements Runnable {
        private static final String TAG = "LaunchThread";

        private LocalSocket launchAppSocket;
        private DataInputStream inputStream;
        private DataOutputStream outputStream;

        private LaunchThread(LocalSocket launchAppSocket, DataInputStream inputStream, DataOutputStream outputStream) {
            this.launchAppSocket = launchAppSocket;
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    int length = inputStream.readInt();
                    byte[] resultByte = new byte[length];
                    inputStream.readFully(resultByte);
                    String result = new String(resultByte, "UTF-8");
                    Log.d(TAG, "Result:" + result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mLaunchThread = null;
        }

        private boolean launchApp(AppInfo appInfo) {
            String appPath = appInfo.getPath();
            byte[] appPathBytes = appPath.getBytes();
            try {
                outputStream.writeInt(appPathBytes.length);
                outputStream.write(appPathBytes);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    private class SocketServerThread implements Runnable {
        private static final String TAG = "SocketServerThread";
        private String SOCKET_ADDRESS = "extend_binder";
        private LocalServerSocket serverSocket;

        @Override
        public void run() {

            try {
                serverSocket = new LocalServerSocket(SOCKET_ADDRESS);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "LocalServerSocket created error.");
            }
            while (serverSocket != null) {
                try {
                    LocalSocket clientSocket = serverSocket.accept();
                    DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

                    int length = inputStream.readInt();
                    byte[] appNameByte = new byte[length];
                    inputStream.readFully(appNameByte);
                    String appName = new String(appNameByte, "UTF-8");
                    if (appName.equals(ControlService.LAUNCH_APP_NAME)) {
                        if (mLaunchThread == null) {
                            mLaunchThread = new LaunchThread(clientSocket, inputStream, outputStream);
                            mThreadPool.execute(mLaunchThread);
                        }
                    } else {
                        AppInfo appInfo = mAppMap.get(appName);
                        if (appInfo != null && appInfo.getThread() == null) {
                            mThreadPool.execute(new ClientThread(appInfo, clientSocket, inputStream, outputStream));
                        } else {
                            Log.d(TAG, "Ignore appName:" + appName);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        /**
         * Stop it.
         */
        public void stopIt() {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                    serverSocket = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class ClientThread implements Runnable {
        private static final int CONTENT_PROVIDER_REQUEST = 1;
        private static final int CONTENT_PROVIDER_RESPONSE = 2;
        private static final int CURSOR_REQUEST = 3;
        private static final int CURSOR_RESPONSE = 4;
        private static final int INTENT_REQUEST = 5;

        private static final String TAG = "ClientThread";
        private final LocalSocket mClientSocket;
        private final AppInfo mAppInfo;
        private DataInputStream inputStream;
        private DataOutputStream outputStream;

        private ClientThread(AppInfo appInfo, LocalSocket clientSocket, DataInputStream inputStream, DataOutputStream outputStream) {
            this.mAppInfo = appInfo;
            this.mClientSocket = clientSocket;
            this.inputStream = inputStream;
            this.outputStream = outputStream;
            mAppInfo.setThread(this);
        }

        /**
         * 发送请求包请求ContentProvider
         *
         * @param request
         */
        public boolean requestContentProvider(ContentProviderMsg.Request request) {
            try {
                outputStream.writeInt(CONTENT_PROVIDER_REQUEST);
                byte[] array = request.toByteArray();
                outputStream.writeInt(array.length);
                outputStream.write(array);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        public boolean requestCursor(CursorMsg.Request request) {
            try {
                outputStream.writeInt(CURSOR_REQUEST);
                byte[] array = request.toByteArray();
                outputStream.writeInt(array.length);
                outputStream.write(array);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        public boolean requestIntent(IntentMsg.Request request) {
            try {
                outputStream.writeInt(INTENT_REQUEST);
                byte[] array = request.toByteArray();
                outputStream.writeInt(array.length);
                outputStream.write(array);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public void run() {
            try {
                Log.d(TAG, "ClientThread:" + mAppInfo.getName());
                int _length = inputStream.readInt();
                byte[] data = new byte[_length];
                inputStream.readFully(data);
                AppInfoMsg.Info info = AppInfoMsg.Info.parseFrom(data);
                mAppInfo.resetActivity();
                mAppInfo.resetService();
                mAppInfo.resetBroadcast();
                mAppInfo.resetContentProvider();
                try {
                    for(String s : info.getActivityListList()) {
                        mAppInfo.addActivity(s);
                        if (intentCallback != null) {
                            intentCallback.addActivity(s);
                            Log.d(TAG, "addActivity=" + s);
                        }
                        mActivityMap.put(s, mAppInfo);
                    }
                    for(String s : info.getServiceListList()) {
                        mAppInfo.addService(s);
                        if (intentCallback != null) {
                            intentCallback.addService(s);
                        }
                        mServiceMap.put(s, mAppInfo);
                    }
                    for(String s : info.getBroadcastListList()) {
                        mAppInfo.addBroadcast(s);
                        if (intentCallback != null) {
                            intentCallback.addBroadcast(s);
                        }
                        mBroadcastMap.put(s, mAppInfo);
                    }
                    for(String s : info.getContentProviderListList()) {
                        mAppInfo.addContentProvider(s);
                        mContentProviderMap.put(s, mAppInfo);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mAppInfo.setStatus(AppStatus.STARTED);
                Log.d(TAG, "AppStatus STARTED:" + mAppInfo.getName());
                synchronized (mAppInfo) {
                    mAppInfo.notifyAll();
                }

                while (true) {
                    int type = inputStream.readInt();
                    switch (type) {
                        // 扩展请求原生 发送包
                        case CONTENT_PROVIDER_REQUEST:
                            handleContentProviderRequest();
                            break;
                        //原生请求扩展 返回包
                        case CONTENT_PROVIDER_RESPONSE:
                            handleContentProviderResponse();
                            break;
                        // 扩展请求原生 发送包
                        case CURSOR_REQUEST:
                            handleCursorRequest();
                            break;
                        // 原生请求扩展 返回包
                        case CURSOR_RESPONSE:
                            handleCursorResponse();
                            break;
                        case INTENT_REQUEST:
                            handleIntentRequest();
                        default:
                            Log.e(TAG, "Unkown RPC type.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mAppInfo.setStatus(AppStatus.STOP);
            mAppInfo.setThread(null);
            Log.d(TAG, "AppStatus STOP:" + mAppInfo.getName());
        }

        private void handleIntentRequest() throws IOException {
            int _length = inputStream.readInt();
            byte[] data = new byte[_length];
            inputStream.readFully(data);
            IntentMsg.Request request = IntentMsg.Request.parseFrom(data);
            String action = request.getAction();
            String className = request.getClassName();
            String packageName = request.getPackageName();
            ProtocolStringList categoriesList = request.getCategoriesList();
            String type = request.getType();
            Map<String, String> extrasMap = request.getExtrasMap();
            IntentMsg.Request.Method method = request.getMethod();
            String uri = request.getUri();
            Intent intent = new Intent();
            intent.setAction(action);
            intent.setClassName(packageName, className);
            intent.setType(type);
            intent.setData(Uri.parse(uri));
            for (String s : categoriesList) {
                intent.addCategory(s);
            }
            for (String key : extrasMap.keySet()) {
                intent.putExtra(key, extrasMap.get(key));
            }
            switch(method) {
                case startActivity:
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ControlService.this.startActivity(intent);
                    break;
                case startService:
                    ControlService.this.startService(intent);
                    break;
                case sendBroadcast:
                    ControlService.this.sendBroadcast(intent);
                    break;
                case UNRECOGNIZED:
                    break;
            }

        }

        private void handleCursorRequest() throws IOException {
            int _length = inputStream.readInt();
            byte[] data = new byte[_length];
            inputStream.readFully(data);
            CursorMsg.Request request = CursorMsg.Request.parseFrom(data);
            UUID uuid = UUID.fromString(request.getUuid());
            CursorMsg.Request.Method method = request.getMethod();
            int offset = request.getOffset();
            int position = request.getPosition();
            String columnName = request.getColumnName();
            int columnIndex = request.getColumnIndex();
            Cursor cursor = (Cursor) mProxyMap.get(uuid);
            CursorMsg.Response.Builder builder = CursorMsg.Response.newBuilder();
            builder.setUuid(uuid.toString());
            builder.setStatus(CursorMsg.Response.Status.Ok);
            switch (method) {
                case getCount:
                    builder.setIntResult(cursor.getCount());
                    break;
                case getPosition:
                    builder.setIntResult(cursor.getPosition());
                    break;
                case move:
                    builder.setBoolResult(cursor.move(offset));
                    break;
                case moveToPosition:
                    builder.setBoolResult(cursor.moveToPosition(position));
                    break;
                case moveToFirst:
                    builder.setBoolResult(cursor.moveToFirst());
                    break;
                case moveToLast:
                    builder.setBoolResult(cursor.moveToLast());
                    break;
                case moveToNext:
                    builder.setBoolResult(cursor.moveToNext());
                    break;
                case moveToPrevious:
                    builder.setBoolResult(cursor.moveToPrevious());
                    break;
                case isFirst:
                    builder.setBoolResult(cursor.isFirst());
                    break;
                case isLast:
                    builder.setBoolResult(cursor.isLast());
                    break;
                case isBeforeFirst:
                    builder.setBoolResult(cursor.isBeforeFirst());
                    break;
                case isAfterLast:
                    builder.setBoolResult(cursor.isAfterLast());
                    break;
                case getColumnIndex:
                    builder.setIntResult(cursor.getColumnIndex(columnName));
                    break;
                case getColumnIndexOrThrow:
                    builder.setIntResult(cursor.getColumnIndexOrThrow(columnName));
                    break;
                case getColumnName:
                    builder.setStringResult(cursor.getColumnName(columnIndex));
                    break;
                case getColumnNames:
                    builder.addAllStringArrayResult(Arrays.asList(cursor.getColumnNames()));
                    break;
                case getColumnCount:
                    builder.setIntResult(cursor.getColumnIndex(columnName));
                    break;
                case getBlob:
                    builder.setBytesResult(ByteString.copyFrom(cursor.getBlob(columnIndex)));
                    break;
                case getString:
                    builder.setStringResult(cursor.getString(columnIndex));
                    break;
                case copyStringToBuffer:
                    builder.setStatus(CursorMsg.Response.Status.MethodNotSupport);
                    break;
                case getShort:
                    builder.setIntResult(cursor.getShort(columnIndex));
                    break;
                case getInt:
                    builder.setIntResult(cursor.getInt(columnIndex));
                    break;
                case getLong:
                    builder.setLongResult(cursor.getLong(columnIndex));
                    break;
                case getFloat:
                    builder.setFloatResult(cursor.getFloat(columnIndex));
                    break;
                case getDouble:
                    builder.setDoubleResult(cursor.getDouble(columnIndex));
                    break;
                case getType:
                    builder.setIntResult(cursor.getType(columnIndex));
                    break;
                case isNull:
                    builder.setBoolResult(cursor.isNull(columnIndex));
                    break;
                case deactivate:
                    cursor.deactivate();
                    break;
                case requery:
                    builder.setBoolResult(cursor.requery());
                    break;
                //关闭 Cursor 之后，服务端删除实例，扩展客户端维持isClosed()查询。
                case close:
                    cursor.close();
                    mProxyMap.remove(uuid);
                    break;
                case isClosed:
                    builder.setBoolResult(cursor.isClosed());
                    break;
                case registerContentObserver:
                    builder.setStatus(CursorMsg.Response.Status.MethodNotSupport);
                    break;
                case unregisterContentObserver:
                    builder.setStatus(CursorMsg.Response.Status.MethodNotSupport);
                    break;
                case registerDataSetObserver:
                    builder.setStatus(CursorMsg.Response.Status.MethodNotSupport);
                    break;
                case unregisterDataSetObserver:
                    builder.setStatus(CursorMsg.Response.Status.MethodNotSupport);
                    break;
                case setNotificationUri:
                    builder.setStatus(CursorMsg.Response.Status.MethodNotSupport);
                    break;
                case getNotificationUri:
                    builder.setStatus(CursorMsg.Response.Status.MethodNotSupport);
                    break;
                case getWantsAllOnMoveCalls:
                    builder.setBoolResult(cursor.getWantsAllOnMoveCalls());
                    break;
                case setExtras:
                    builder.setStatus(CursorMsg.Response.Status.MethodNotSupport);
                    break;
                case getExtras:
                    builder.setStatus(CursorMsg.Response.Status.MethodNotSupport);
                    break;
                case respond:
                    builder.setStatus(CursorMsg.Response.Status.MethodNotSupport);
                    break;
                default:
                    builder.setStatus(CursorMsg.Response.Status.MethodNotFound);
                    break;
            }
            outputStream.writeInt(CURSOR_RESPONSE);
            byte[] array = builder.build().toByteArray();
            outputStream.writeInt(array.length);
            outputStream.write(array);
        }

        private void handleCursorResponse() throws IOException {
            int _length = inputStream.readInt();
            byte[] data = new byte[_length];
            inputStream.readFully(data);
            CursorMsg.Response response = CursorMsg.Response.parseFrom(data);
            UUID uuid = UUID.fromString(response.getUuid());
            Object proxy = mProxyMap.remove(uuid);
            if (proxy != null) {
                mResultMap.put(uuid, response);
                synchronized (proxy) {
                    proxy.notify();
                }
            }
        }

        private void handleContentProviderResponse() throws IOException {
            int _length = inputStream.readInt();
            byte[] data = new byte[_length];
            inputStream.readFully(data);
            ContentProviderMsg.Response response = ContentProviderMsg.Response.parseFrom(data);
            UUID uuid = UUID.fromString(response.getUuid());
            ProxyCursor cursor = null;
            ContentProviderMsg.Response.Status status = response.getStatus();
            if (status == ContentProviderMsg.Response.Status.Ok) {
                cursor = new ProxyCursor(uuid, mAppInfo);
            }
            Object proxy = mProxyMap.remove(uuid);
            if (proxy != null) {
                mResultMap.put(uuid, cursor);
                synchronized (proxy) {
                    proxy.notify();
                }
            }
        }

        private void handleContentProviderRequest() throws IOException {
            int _length = inputStream.readInt();
            byte[] data = new byte[_length];
            inputStream.readFully(data);
            ContentProviderMsg.Request request = ContentProviderMsg.Request.parseFrom(data);
            String uuid = request.getUuid();
            String uri = request.getUri();
            String selection = request.getSelection();
            String[] selectionArgs = request.getSelectionArgsList().toArray(new String[0]);
            ContentValues values = new ContentValues();
            for (Map.Entry<String, String> entry : request.getValuesMap().entrySet()) {
                values.put(entry.getKey(), entry.getValue());
            }
            String[] projection = request.getProjectionList().toArray(new String[0]);
            String sortOrder = request.getSortOrder();
            ContentProviderMsg.Response.Builder responseBuilder = ContentProviderMsg.Response.newBuilder();
            responseBuilder.setUuid(uuid);
            responseBuilder.setStatus(ContentProviderMsg.Response.Status.Ok);
            switch (request.getMethod()) {
                case delete:
                    int retDelete = getContentResolver().delete(Uri.parse(uri), selection, selectionArgs);
                    responseBuilder.setIntResult(retDelete);
                    break;
                case getType:
                    String retGetType = getContentResolver().getType(Uri.parse(uri));
                    responseBuilder.setStringResult(retGetType);
                    break;
                case insert:
                    Uri retInsert = getContentResolver().insert(Uri.parse(uri), values);
                    responseBuilder.setStringResult(retInsert == null ? null : retInsert.toString());
                    break;
                case query:
                    Cursor cursor = getContentResolver().query(Uri.parse(uri), projection, selection, selectionArgs, sortOrder);
                    UUID retQuery = UUID.randomUUID();
                    mProxyMap.put(retQuery, cursor);
                    responseBuilder.setStringResult(retQuery.toString());
                    break;
                case update:
                    int retUpdate = getContentResolver().update(Uri.parse(uri), values, selection, selectionArgs);
                    responseBuilder.setIntResult(retUpdate);
                    break;
                default:
                    responseBuilder.setStatus(ContentProviderMsg.Response.Status.MethodNotFound);
                    break;
            }
            outputStream.writeInt(CONTENT_PROVIDER_RESPONSE);
            byte[] array = responseBuilder.build().toByteArray();
            outputStream.writeInt(array.length);
            outputStream.write(array);

        }
    }
}
