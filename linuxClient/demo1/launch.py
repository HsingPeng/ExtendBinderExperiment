import logging
import signal
import sys
import os
import Tkinter
import main_activity
import service1
import broadcast1
import contentprovider1
import extend.binder as binder

if __name__ == '__main__':
    def signal_handle(signal, frame):
        sys.exit(0)

    def send_activity():
        intent = {}
        intent['className'] = 'com.github.hsingpeng.testextendbinder.Main2Activity'
        intent['packageName'] = 'com.github.hsingpeng.testextendbinder'
        looper.startActivity(intent)

    #signal.signal(signal.SIGINT, signal_handle)
    logging.basicConfig(level=logging.DEBUG,
        format='[%(asctime)s] %(filename)s[line:%(lineno)d] '
         + '%(name)s:%(levelname)s: %(message)s')
    top = Tkinter.Tk()
    top.geometry('300x200')
    top.title('Test Extend Binder')
    button = Tkinter.Button(top, text='SEND ACTIVITY', command=send_activity)
    button.pack(pady=30)
    looper = binder.Looper()
    looper.set_app_name('com.example.binder')
    looper.set_app_path('home/deep/binder/launch.py')
    activity = main_activity.MainActivity(looper, top)
    service = service1.Service1(looper)
    broadcast = broadcast1.Broadcast1(looper)
    contentprovider = contentprovider1.ContentProvider1(looper)
    looper.put_activity(activity.className, activity)
    looper.put_service(service.className, service)
    looper.put_broadcast(broadcast.action, broadcast)
    looper.put_contentprovider(contentprovider.path, contentprovider)
    looper.start()
    activity.top.mainloop()
    looper.stop()
    looper.join()
