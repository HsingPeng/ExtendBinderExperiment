import time
import logging
import extend.binder as binder
import extend.android_componment as android

class Service1(android.Service):
    def __init__(self, looper):
        android.Service.__init__(self, 'com.example.binder.service1', looper)

    def onCreate(self):
        pass

    def onStartCommand(self, intent):
        logging.debug("recv intent:" + str(intent))
