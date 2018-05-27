import extend.binder as binder
import extend.android_componment as android
import logging

class Broadcast1(android.BroadcastReceiver):
    def __init__(self, looper):
        android.BroadcastReceiver.__init__(self, 'com.example.binder.broadcast1', looper)

    def onReceive(self, intent):
        logging.debug('onReceiver:' + str(intent))
