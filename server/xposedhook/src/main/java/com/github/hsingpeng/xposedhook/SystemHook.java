package com.github.hsingpeng.xposedhook;

import android.app.AndroidAppHelper;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * System Hook
 * Created by bboxh on 2017/11/16.
 */

public class SystemHook implements IXposedHookLoadPackage {
    private static final String TAG = "SystemHook";
    private static final Object ACTIVITY_START_SUCCESS = 0;     //ActivityManager.START_SUCCESS
    private IServiceAidlInterface iService;
    Set<String> activitySet = Collections.synchronizedSet(new HashSet<String>());
    Set<String> serviceSet = Collections.synchronizedSet(new HashSet<String>());
    Set<String> broadcastSet = Collections.synchronizedSet(new HashSet<String>());
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iService = IServiceAidlInterface.Stub.asInterface(service);
            try {
                iService.registerCallBack(new ICallBack.Stub() {
                    @Override
                    public void resetActivityList() throws RemoteException {
                        activitySet.clear();
                    }

                    @Override
                    public void addActivity(String name) throws RemoteException {
                        activitySet.add(name);
                    }

                    @Override
                    public void resetService() throws RemoteException {
                        serviceSet.clear();
                    }

                    @Override
                    public void addService(String name) throws RemoteException {
                        serviceSet.add(name);
                    }

                    @Override
                    public void resetBroadcast() throws RemoteException {
                        broadcastSet.clear();
                    }

                    @Override
                    public void addBroadcast(String name) throws RemoteException {
                        broadcastSet.add(name);
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
                iService = null;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iService = null;
        }
    };

    private void bind() {
        Log.d(TAG, "bind-extendbinder");
        Context context = AndroidAppHelper.currentApplication().getApplicationContext();
        Intent intent = new Intent();
        intent.setPackage("com.github.hsingpeng.extendbinder");
        intent.setComponent(new ComponentName("com.github.hsingpeng.extendbinder", "com.github.hsingpeng.extendbinder.ControlService"));
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedBridge.log("Loaded app: " + lpparam.packageName);
        if (!lpparam.packageName.equals("android")) {
            return;
        }

        final Class<?> IApplicationThreadClazz = XposedHelpers.findClass(
                "android.app.IApplicationThread",
                lpparam.classLoader);
        final Class<?> IntentClazz = XposedHelpers.findClass("android.content.Intent",
                lpparam.classLoader);
        final Class<?> IBinderClazz = XposedHelpers.findClass("android.os.IBinder",
                lpparam.classLoader);
        final Class<?> ProfilerInfoClazz = XposedHelpers.findClass("android.app.ProfilerInfo",
                lpparam.classLoader);
        final Class<?> BundleClazz = XposedHelpers.findClass("android.os.Bundle",
                lpparam.classLoader);
        final Class<?> ProcessRecordClazz = XposedHelpers.findClass("com.android.server.am.ProcessRecord",
                lpparam.classLoader);
        final Class<?> IIntentReceiverClazz = XposedHelpers.findClass("android.content.IIntentReceiver",
                lpparam.classLoader);

        XposedHelpers.findAndHookMethod("com.android.server.am.ActivityManagerService",
                lpparam.classLoader,
                "startActivity",
                IApplicationThreadClazz,
                String.class,
                IntentClazz,
                String.class,
                IBinderClazz,
                String.class,
                int.class,
                int.class,
                ProfilerInfoClazz,
                BundleClazz,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Intent intent = (Intent) param.args[2];
                        Log.d(TAG, "startActivity: " + " intent=" + intent.toString());

                        if (intent.getComponent() != null && activitySet.contains(intent.getComponent().getClassName())) {
                            param.setResult(ACTIVITY_START_SUCCESS);
                            if (iService != null) {
                                iService.startActivity(intent);
                            }
                        }
                        if (iService == null) {
                            bind();
                        }
                    }
                });

        XposedHelpers.findAndHookMethod("com.android.server.am.ActivityManagerService",
                lpparam.classLoader,
                "startService",
                IApplicationThreadClazz,
                IntentClazz,
                String.class,
                String.class,
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Intent intent = (Intent) param.args[1];
                        Log.d(TAG, "startService: " + " intent=" + intent.toString());

                        if (intent.getComponent() != null && serviceSet.contains(intent.getComponent().getClassName())) {
                            if (iService != null) {
                                iService.startService(intent);
                            }
                        }
                        if (iService == null) {
                            bind();
                        }
                    }
                });

        XposedHelpers.findAndHookMethod("com.android.server.am.ActivityManagerService",
                lpparam.classLoader,
                "broadcastIntent",
                IApplicationThreadClazz,
                IntentClazz,
                String.class,
                IIntentReceiverClazz,
                int.class,
                String.class,
                BundleClazz,
                String[].class,
                int.class,
                BundleClazz,
                boolean.class,
                boolean.class,
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Intent intent = (Intent) param.args[1];
                        Log.d(TAG, "broadcastIntent: " + " intent=" + intent.toString());
                        Log.d(TAG, "broadcastSet: " + broadcastSet.toString());
                        if (broadcastSet.contains(intent.getAction())) {
                            if (iService != null) {
                                iService.broadcastIntent(intent);
                            }
                        }
                        if (iService == null) {
                            bind();
                        }
                    }
                });
    }
}
