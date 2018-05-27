package com.github.hsingpeng.xposedhook;

interface ICallBack {
    void resetActivityList();
    void addActivity(in String name);

    void resetService();
    void addService(in String name);

    void resetBroadcast();
    void addBroadcast(in String name);
}