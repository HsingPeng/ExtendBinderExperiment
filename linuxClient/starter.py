import socket
import os
import time
import struct
import logging

def send(client, msg):
    length = struct.pack('>i', len(msg))
    client.sendall(length)
    client.sendall(msg)

client = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
client.connect("\0extend_binder")
print("Ready.")
app_name = 'com.github.hsingpeng.extendbinder.linux.launcher'
send(client, app_name)

while True:
    _length = client.recv(4)
    length, = struct.unpack('>i', _length)
    app_path = client.recv(length)
    print(app_path)
    cmd = 'python ' + app_path + ' &'
    os.system(cmd)
