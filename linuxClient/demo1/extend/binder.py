import socket
import pdb
import time
import struct
import threading
import uuid
import logging
import app_info_pb2
import intent_pb2
import cursor_pb2
import content_provider_pb2

class Looper(threading.Thread):
    CONTENT_PROVIDER_REQUEST = 1
    CONTENT_PROVIDER_RESPONSE = 2
    CURSOR_REQUEST = 3
    CURSOR_RESPONSE = 4
    INTENT_REQUEST = 5

    def __init__(self):
        threading.Thread.__init__(self)
        self.activity_set = {}
        self.service_set = {}
        self.broadcast_set = {}
        self.contentprovider_set = {}
        self.proxy_set = {}
        self.result_set = {}

    def set_app_name(self, app_name):
        self.app_name = app_name

    def set_app_path(self, app_path):
        self.app_path = app_path

    def put_activity(self, class_name, activity):
        self.activity_set[class_name] = activity
        activity.onCreate()
        activity.onStart()
        activity.onResume()

    def get_activity(self, class_name):
        return self.activity_set.get(class_name)

    def put_service(self, class_name, service):
        self.service_set[class_name] = service
        service.onCreate()

    def get_service(self, class_name):
        return self.service_set.get(class_name)

    def put_broadcast(self, class_name, broadcast):
        self.broadcast_set[class_name] = broadcast

    def get_broadcast(self, class_name):
        return self.broadcast_set.get(class_name)

    def put_contentprovider(self, path, contentprovider):
        self.contentprovider_set[path] = contentprovider
        contentprovider.onCreate()

    def get_contentprovider(self, path):
        return self.contentprovider_set.get(path)

    def send(self, client, msg):
        length = struct.pack('>i', len(msg))
        client.sendall(length)
        client.sendall(msg)

    def send_int(self, client, value):
        _data = struct.pack('>i', value)
        client.sendall(_data)

    def __send_intent(self, intent, method):
        action = intent.get('action')
        className = intent.get('className')
        package = intent.get('packageName')
        _type = intent.get('type')
        categories = intent.get('categories')
        uri = intent.get('uri')
        extras = intent.get('extras')
        request = intent_pb2.Request()
        request.method = method
        if action != None:
            request.action = action
        if className != None:
            request.className = className
        if package != None:
            request.packageName = package
        if _type != None:
            request.type = _type
        if categories != None:
            request.categories.extend(categories)
        if uri != None:
            request.uri = uri
        if extras != None:
            request.extras.update(extras)
        _data = request.SerializeToString()
        self.send_int(self.client, self.INTENT_REQUEST)
        self.send(self.client, _data)

    def startActivity(self, intent):
        self.__send_intent(intent, intent_pb2.Request.startActivity)

    def startService(self, intent):
        self.__send_intent(intent, intent_pb2.Request.startService)

    def startBroadcast(self, intent):
        self.__send_intent(intent, intent_pb2.Request.startBroadcast)

    def getContentResolver_delete(self, uri, selection, selectionArgs):
        request = content_provider_pb2.Request()
        uuid = str(uuid.uuid1())
        request.uuid = uuid
        request.method = content_provider_pb2.Request.delete
        request.uri = uri
        request.selection = selection
        request.selectionArgs = selectionArgs
        _data = request.SerializeToString()
        cond = threading.Condition()
        self.proxy_set[uuid] = cond
        cond.acquire()
        self.send_int(self.client, self.CONTENT_PROVIDER_RESPONSE)
        self.send(self.client, _data)
        cond.wait()
        cond.release()
        self.proxy_set.pop(uuid)
        response = self.result_set.pop(uuid)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.intResult

    def getContentResolver_getType(self, uri):
        request = content_provider_pb2.Request()
        uuid = str(uuid.uuid1())
        request.uuid = uuid
        request.method = content_provider_pb2.Request.getType
        request.uri = uri
        _data = request.SerializeToString()
        cond = threading.Condition()
        self.proxy_set[uuid] = cond
        cond.acquire()
        self.send_int(self.client, self.CONTENT_PROVIDER_RESPONSE)
        self.send(self.client, _data)
        cond.wait()
        cond.release()
        self.proxy_set.pop(uuid)
        response = self.result_set.pop(uuid)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return None
        return response.stringResult
        
    def getContentResolver_insert(self, uri, values):
        request = content_provider_pb2.Request()
        uuid = str(uuid.uuid1())
        request.uuid = uuid
        request.method = content_provider_pb2.Request.insert
        request.uri = uri
        request.values.update(values)
        _data = request.SerializeToString()
        cond = threading.Condition()
        self.proxy_set[uuid] = cond
        cond.acquire()
        self.send_int(self.client, self.CONTENT_PROVIDER_RESPONSE)
        self.send(self.client, _data)
        cond.wait()
        cond.release()
        self.proxy_set.pop(uuid)
        response = self.result_set.pop(uuid)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return None
        return response.stringResult

    def getContentResolver_query(self, uri, projection, selection, selectionArgs, sortOrder):
        request = content_provider_pb2.Request()
        uuid = str(uuid.uuid1())
        request.uuid = uuid
        request.method = content_provider_pb2.Request.query
        request.uri = uri
        request.projection = projection
        request.selectionArgs.append(selectionArgs)
        request.sortOrder = sortOrder
        _data = request.SerializeToString()
        cond = threading.Condition()
        self.proxy_set[uuid] = cond
        cond.acquire()
        self.send_int(self.client, self.CONTENT_PROVIDER_RESPONSE)
        self.send(self.client, _data)
        cond.wait()
        cond.release()
        self.proxy_set.pop(uuid)
        response = self.result_set.pop(uuid)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return None
        cursor = Cursor(uuid, self)
        return cursor

    def getContentResolver_update(self, values, selection, selectionArgs):
        request = content_provider_pb2.Request()
        uuid = str(uuid.uuid1())
        request.uuid = uuid
        request.method = content_provider_pb2.Request.update
        request.uri = uri
        request.values.update(values)
        request.selection = selection
        request.selectionArgs.append(selectionArgs)
        _data = request.SerializeToString()
        cond = threading.Condition()
        self.proxy_set[uuid] = cond
        cond.acquire()
        self.send_int(self.client, self.CONTENT_PROVIDER_RESPONSE)
        self.send(self.client, _data)
        cond.wait()
        cond.release()
        self.proxy_set.pop(uuid)
        response = self.result_set.pop(uuid)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.intResult

    def stop(self):
        self.client.shutdown(2)
        self.client.close()

    def run(self):
        logging.debug("Binder looper started.")
        # connect to extend binder server
        client = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
        client.connect("\0extend_binder")
        self.send(client, self.app_name)
        # register componments.
        app_info = app_info_pb2.Info()
        self.client = client
        for name in self.activity_set:
            app_info.activityList.append(name)
        for name in self.service_set:
            app_info.serviceList.append(name)
        for name in self.broadcast_set:
            app_info.broadcastList.append(name)
        for name in self.contentprovider_set:
            app_info.contentProviderList.append(name)
        _data = app_info.SerializeToString()
        self.send(client, _data)
        # monitor request
        while True:
            _method = client.recv(4)
            method, = struct.unpack('>i', _method)
            _length = client.recv(4)
            length, = struct.unpack('>i', _length)
            pb = client.recv(length)
            if method == self.CONTENT_PROVIDER_REQUEST:
                self.handle_content_provider_request(pb)
            elif method == self.CONTENT_PROVIDER_RESPONSE:
                self.handle_content_provider_response(pb)
            elif method == self.CURSOR_REQUEST:
                self.handle_cursor_request(pb)
            elif method == self.CURSOR_RESPONSE:
                self.handle_cursor_response(pb)
            elif method == self.INTENT_REQUEST:
                self.handle_intent(pb)
            else:
                logging.error("Unknown method:" + method)

    def handle_content_provider_request(self, pb):
        #pdb.set_trace()
        request = content_provider_pb2.Request()
        request.ParseFromString(pb)
        uuid = request.uuid
        contentprovider = self.contentprovider_set.get(request.uri)
        response = content_provider_pb2.Response()
        response.uuid = uuid
        response.status = content_provider_pb2.Response.Ok
        if contentprovider == None:
            response.status = content_provider_pb2.Response.UriNotFound
            _data = response.SerializeToString()
            self.send_int(self.client, self.CONTENT_PROVIDER_RESPONSE)
            self.send(self.client, _data)
            return
        _method = request.method
        uri = request.uri
        selection = request.selection
        selectionArgs = request.selectionArgs
        values = request.values
        projection = request.projection
        sortOrder = request.sortOrder
        if _method == content_provider_pb2.Request.delete:
            result = contentprovider.delete(uri, selection, selectionArgs)
            response.intResult = result
        elif _method == content_provider_pb2.Request.getType:
            result = contentprovider.getType(uri)
            response.stringResult = result
        elif _method == content_provider_pb2.Request.insert:
            result = contentprovider.insert(uri, values)
            response.stringResult = result
        elif _method == content_provider_pb2.Request.query:
            cursor = contentprovider.query(uri, projection, selection
                                            , selectionArgs, sortOrder)
            if cursor == None:
                response.status = content_provider_pb2.Response.MethodNotFound
            else:
                self.proxy_set[uuid] = cursor
        elif _method == content_provider_pb2.Request.update:
            result = contentprovider.update(uri, values, selection, selectionArgs)
            response.intResult = result
        else:
            response.status = content_provider_pb2.Response.MethodNotFound
        _data = response.SerializeToString()
        self.send_int(self.client, self.CONTENT_PROVIDER_RESPONSE)
        self.send(self.client, _data)

    def handle_content_provider_response(self, pb):
        response = content_provider_pb2.Response()
        response.ParseFromString(pb)
        uuid = response.uuid
        cond = self.proxy_set.get(uuid)
        if lock == None:
            return
        cond.acquire()
        cond.notifyAll()
        cond.release()
        self.result_set[uuid] = response

    def handle_cursor_request(self, pb):
        request = cursor_pb2.Request()
        request.ParseFromString(pb)
        uuid = request.uuid
        cursor = self.proxy_set.get(uuid)
        response = cursor_pb2.Response()
        response.uuid = uuid
        response.status = cursor_pb2.Response.Ok
        if cursor == None:
            response.status = cursor_pb2.Response.UriNotFound
            _data = response.SerializeToString()
            self.send_int(self.client, self.CURSOR_RESPONSE)
            self.send(self.client, _data)
            return
        _method = request.method
        offset = request.offset
        position = request.position
        columnName = request.columnName
        columnIndex = request.columnIndex
        observer = request.observer
        extras = request.extras
        if _method == cursor_pb2.Request.getCount:
            result = cursor.getCount()
            response.intResult = result
        elif _method == cursor_pb2.Request.getPosition:
            result = cursor.getPosition()
            response.intResult = result
        elif _method == cursor_pb2.Request.move:
            result = cursor.move(offset)
            response.boolResult = result
        elif _method == cursor_pb2.Request.moveToPosition:
            result = cursor.moveToPosition(position)
            response.boolResult = result
        elif _method == cursor_pb2.Request.moveToFirst:
            result = cursor.moveToFirst()
            response.boolResult = result
        elif _method == cursor_pb2.Request.moveToLast:
            result = cursor.moveToLast()
            response.boolResult = result
        elif _method == cursor_pb2.Request.moveToNext:
            result = cursor.moveToNext()
            response.boolResult = result
        elif _method == cursor_pb2.Request.moveToPrevious:
            result = cursor.moveToPrevious()
            response.boolResult = result
        elif _method == cursor_pb2.Request.isFirst:
            result = cursor.isFirst()
            response.boolResult = result
        elif _method == cursor_pb2.Request.isLast:
            result = cursor.isLast()
            response.boolResult = result
        elif _method == cursor_pb2.Request.isBeforeFirst:
            result = cursor.isBeforeFirst()
            response.boolResult = result
        elif _method == cursor_pb2.Request.isAfterLast:
            result = cursor.isAfterLast()
            response.boolResult = result
        elif _method == cursor_pb2.Request.getColumnIndex:
            result = cursor.getColumnIndex(columnName)
            response.intResult = result
        elif _method == cursor_pb2.Request.getColumnIndexOrThrow:
            result = cursor.getColumnIndexOrThrow(columnName)
            response.intResult = result
        elif _method == cursor_pb2.Request.getColumnName:
            result = cursor.getColumnName(columnIndex)
            response.stringResult = result
        elif _method == cursor_pb2.Request.getColumnNames:
            result = cursor.getColumnNames()
            response.stringArrayResult.extend(result)
        elif _method == cursor_pb2.Request.getColumnCount:
            result = cursor.getColumnCount()
            response.intResult = result
        elif _method == cursor_pb2.Request.getBlob:
            result = cursor.getBlob(columnIndex)
            response.bytesResult = result
        elif _method == cursor_pb2.Request.copyStringToBuffer:
            #result = cursor.copyStringToBuffer(columnIndex, None)
            response.status = cursor_pb2.Response.MethodNotSupport
        elif _method == cursor_pb2.Request.getShort:
            result = cursor.getShort(columnIndex)
            response.intResult = result
        elif _method == cursor_pb2.Request.getInt:
            result = cursor.getInt(columnIndex)
            response.intResult = result
        elif _method == cursor_pb2.Request.getString:
            result = cursor.getString(columnIndex)
            response.stringResult = result
        elif _method == cursor_pb2.Request.getLong:
            result = cursor.getLong(columnIndex)
            response.longResult = result
        elif _method == cursor_pb2.Request.getFloat:
            result = cursor.getFloat(columnIndex)
            response.floatResult = result
        elif _method == cursor_pb2.Request.getDouble:
            result = cursor.getDouble(columnIndex)
            response.doubleResult = result
        elif _method == cursor_pb2.Request.getInt:
            result = cursor.getInt(columnIndex)
            response.intResult = result
        elif _method == cursor_pb2.Request.getType:
            result = cursor.getType(columnIndex)
            response.intResult = result
        elif _method == cursor_pb2.Request.isNull:
            result = cursor.isNull(columnIndex)
            response.boolResult = result
        elif _method == cursor_pb2.Request.deactivate:
            cursor.deactivate()
        elif _method == cursor_pb2.Request.requery:
            result = cursor.requery()
            response.boolResult = result
        elif _method == cursor_pb2.Request.close:
            cursor.close()
            del self.proxy_set[uuid]
        elif _method == cursor_pb2.Request.isClosed:
            result = cursor.isClosed()
            response.boolResult = result
        elif _method == cursor_pb2.Request.getWantsAllOnMoveCalls:
            result = cursor.getWantsAllOnMoveCalls()
            response.boolResult = result
        else:
            response.status = cursor_pb2.Response.MethodNotFound
        _data = response.SerializeToString()
        self.send_int(self.client, self.CURSOR_RESPONSE)
        self.send(self.client, _data)

    def handle_cursor_response(self, pb):
        response = cursor_pb2.Response()
        response.ParseFromString(pb)
        uuid = response.uuid
        cond = self.proxy_set.get(uuid)
        if lock == None:
            return
        cond.acquire()
        cond.notifyAll()
        cond.release()
        self.result_set[uuid] = response

    def handle_intent(self, pb):
        request = intent_pb2.Request()
        request.ParseFromString(pb)
        _method = request.method
        action = request.action
        className = request.className
        packageName = request.packageName
        _type = request.type
        categories = request.categories
        uri = request.uri
        extras = request.extras
        if _method == intent_pb2.Request.startActivity:
            activity = self.activity_set.get(className)
            if activity!= None:
                activity.onNewIntent(request)
        elif _method == intent_pb2.Request.startService:
            service = self.service_set.get(className)
            if service != None:
                service.onStartCommand(request)
        elif _method == intent_pb2.Request.sendBroadcast:
            broadcast = self.broadcast_set.get(action)
            if broadcast != None:
                broadcast.onReceive(request)
        else:
            return

class Cursor():
    CURSOR_RESPONSE = 4
    def __init__(self, uuid, looper):
        self.uuid = uuid

    def __send(self, request):
        uuid = str(uuid.uuid1())
        request.uuid = uuid
        _data = request.SerializeToString()
        cond = threading.Condition()
        self.proxy_set[uuid] = cond
        cond.acquire()
        self.send_int(self.client, self.CURSOR_RESPONSE)
        self.send(self.client, _data)
        cond.wait()
        cond.release()
        self.proxy_set.pop(uuid)
        response = self.result_set.pop(uuid)
        return response

    def getCount(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getCount
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.intResult

    def getPosition(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getPosition
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.intResult

    def move(self, offset):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.move
        request.offset = offset
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return False
        return response.boolResult

    def moveToPosition(self, position):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.moveToPosition
        request.position = position
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return False
        return response.boolResult

    def moveToFirst(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.moveToFirst
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return False
        return response.boolResult

    def moveToLast(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.moveToLast
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return False
        return response.boolResult

    def moveToNext(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.moveToNext
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return False
        return response.boolResult

    def moveToPrevious(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.moveToPrevious
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return False
        return response.boolResult

    def isFirst(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.isFirst
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return False
        return response.boolResult

    def isLast(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.isLast
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return False
        return response.boolResult

    def isBeforeFirst(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.isBeforeFirst
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return False
        return response.boolResult

    def isAfterLast(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.isAfterLast
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return False
        return response.boolResult

    def getColumnIndex(self, columnName):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getColumnIndex
        request.columnName = columnName
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.intResult

    def getColumnIndexOrThrow(self, columnName):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getColumnIndexOrThrow
        request.columnName = columnName
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.intResult

    def getColumnName(self, columnIndex):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getColumnName
        request.columnIndex = columnIndex
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return None
        return response.stringResult

    def getColumnNames(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getColumnNames
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return None
        return response.stringArrayResult

    def getColumnCount(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getColumnCount
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.intResult

    def getBlob(self, columnIndex):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getBlob
        request.columnIndex = columnIndex
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return None
        return response.stringResult

    def getString(self, columnIndex):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getString
        request.columnIndex = columnIndex
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return None
        return response.stringResult

    def copyStringToBuffer(self, columnIndex, _buffer):
        ''' Unsupport temporarily '''

    def getShort(self, columnIndex):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getShort
        request.columnIndex = columnIndex
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.intResult

    def getInt(self, columnIndex):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getInt
        request.columnIndex = columnIndex
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.intResult

    def getLong(self, columnIndex):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getLong
        request.columnIndex = columnIndex
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.longResult

    def getFloat(self, columnIndex):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getFloat
        request.columnIndex = columnIndex
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.floatResult

    def getDouble(self, columnIndex):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getDouble
        request.columnIndex = columnIndex
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.doubleResult

    def getType(self, columnIndex):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getType
        request.columnIndex = columnIndex
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return 0
        return response.intResult

    def isNull(self, columnIndex):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.isNull
        request.columnIndex = columnIndex
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return True
        return response.boolResult

    def deactivate(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.deactivate
        self.__send(request)

    def requery(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.requery
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return False
        return response.boolResult

    def close(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.close
        self.__send(request)

    def isClosed(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.isClosed
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return True
        return response.boolResult

    def getWantsAllOnMoveCalls(self):
        request = cursor_pb2.Request()
        request.method = intent_pb2.Request.getWantsAllOnMoveCalls
        self.__send(request)
        status = response.status
        if status != content_provider_pb2.Response.Ok:
            return False
        return response.boolResult
