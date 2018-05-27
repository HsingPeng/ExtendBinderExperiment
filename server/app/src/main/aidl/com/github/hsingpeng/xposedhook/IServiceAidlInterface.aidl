package com.github.hsingpeng.xposedhook;

import com.github.hsingpeng.xposedhook.ICallBack;

interface IServiceAidlInterface {
    void startActivity(in Intent intent);
    void startService(in Intent intent);
    void broadcastIntent(in Intent intent);
    void registerCallBack(ICallBack callback);
}