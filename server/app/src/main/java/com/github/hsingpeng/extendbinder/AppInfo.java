package com.github.hsingpeng.extendbinder;

import android.net.Uri;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 扩展程序信息
 * Created by deep on 2018/2/25.
 */

class AppInfo {
    private String name;
    private String path;
    private AppStatus status;
    private Set<String> activitySet = new HashSet<>();
    private Set<String> serviceSet = new HashSet<>();
    private Set<String> broadcastSet = new HashSet<>();
    private Set<String> contentProviderSet = new HashSet<>();
    private ControlService.ClientThread thread;

    public AppInfo(String name, String path) {
        this.name = name;
        this.path = path;
        status = AppStatus.STOP;
    }

    public void addActivity(String className) {
        activitySet.add(className);
    }

    public void resetActivity() {
        contentProviderSet.clear();
    }

    public void addService(String className) {
        serviceSet.add(className);
    }

    public void resetService() {
        serviceSet.clear();
    }

    public void addBroadcast(String className) {
        broadcastSet.add(className);
    }

    public void resetBroadcast() {
        broadcastSet.clear();
    }

    public void addContentProvider(String path) {
        contentProviderSet.add(path);
    }

    public void resetContentProvider() {
        contentProviderSet.clear();
    }

    public Set<String> getContentProvider() {
        Set<String> set= new HashSet<>();
        set.addAll(contentProviderSet);
        return set;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public AppStatus getStatus() {
        return status;
    }

    public void setStatus(AppStatus status) {
        this.status = status;
    }

    public ControlService.ClientThread getThread() {
        return thread;
    }

    public void setThread(ControlService.ClientThread thread) {
        this.thread = thread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppInfo appInfo = (AppInfo) o;

        if (name != null ? !name.equals(appInfo.name) : appInfo.name != null) return false;
        if (path != null ? !path.equals(appInfo.path) : appInfo.path != null) return false;
        return status == appInfo.status;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}

enum AppStatus {
    STOP, STARTED, UNAVAILABLE
}