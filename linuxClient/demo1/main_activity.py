import Tkinter
import time
import threading
import logging
import extend.binder as binder
import extend.android_componment as android

class MainActivity(android.Activity):

    def __init__(self, looper, top):
        android.Activity.__init__(self, 'com.example.binder.mainactivity', looper)
        self.top = top

    def onCreate(self):
        msg = 'Test Activity.'
        tk = Tkinter.Toplevel(self.top)
        tk.geometry('300x200')
        tk.title('Test Activity')
        self.labelvar = Tkinter.StringVar()
        self.labelvar.set(msg)
        label = Tkinter.Label(tk, textvariable=self.labelvar)
        #label.place(relx=0.5,rely=0.5,anchor='center')
        label.pack(expand='yes', fill='both')

    def onNewIntent(self, intent):
        logging.debug('onNewIntent:' + str(intent))
        extras = intent.extras
        msg = extras.get('msg')
        if msg != None:
            self.labelvar.set(msg)

if __name__ == '__main__':
    def update(activity):
        activity.extras = {'msg': '1 gei wo li giao giao!'}
        activity.onNewIntent(activity)
        
    top = Tkinter.Tk()
    activity = MainActivity(None, top)
    activity.onCreate()
    t = threading.Timer(5, update, [activity])
    t.start()
    activity.top.mainloop()
