import abc

class Activity():
    __metaclass__ = abc.ABCMeta

    def __init__(self, className, looper):
        self.className = className
        self.looper = looper

    @abc.abstractmethod
    def onCreate(self):
        pass

    def onStart(self):
        pass

    def onResume(self):
        pass

    def onPause(self):
        pass

    def onStop(self):
        pass

    def onDestroy(self):
        pass

    @abc.abstractmethod
    def onNewIntent(self, intent):
        pass

class Service():
    __metaclass__ = abc.ABCMeta

    def __init__(self, className, looper):
        self.className = className
        self.looper = looper

    @abc.abstractmethod
    def onCreate(self):
        pass

    @abc.abstractmethod
    def onStartCommand(self, intent):
        pass

    def onDestroy(self):
        pass

class BroadcastReceiver():
    __metaclass__ = abc.ABCMeta

    def __init__(self, action, looper):
        self.action = action
        self.looper = looper

    @abc.abstractmethod
    def onReceive(self, intent):
        pass

class ContentProvider():
    __metaclass__ = abc.ABCMeta

    def __init__(self, path, looper):
        self.path = path
        self.looper = looper

    @abc.abstractmethod
    def onCreate(self):
        pass

    @abc.abstractmethod
    def delete(self, uri, selection, selectionArgs):
        pass

    @abc.abstractmethod
    def getType(self, uri):
        pass

    @abc.abstractmethod
    def insert(self, uri, values):
        pass

    @abc.abstractmethod
    def query(self, uri, projection, selection, selectionArgs, sortOrder):
        pass

    @abc.abstractmethod
    def update(self, uri, values, selection, selectionArgs):
        pass

class Cursor():
    __metaclass__ = abc.ABCMeta

    def __init__(self, looper):
        self.looper = looper

    @abc.abstractmethod
    def getCount(self):
        pass

    @abc.abstractmethod
    def getPosition(self):
        pass

    @abc.abstractmethod
    def move(self, offset):
        pass

    @abc.abstractmethod
    def moveToPosition(self, position):
        pass

    @abc.abstractmethod
    def moveToFirst(self):
        pass

    @abc.abstractmethod
    def moveToLast(self):
        pass

    @abc.abstractmethod
    def moveToNext(self):
        pass

    @abc.abstractmethod
    def moveToPrevious(self):
        pass

    @abc.abstractmethod
    def isFirst(self):
        pass

    @abc.abstractmethod
    def isLast(self):
        pass

    @abc.abstractmethod
    def isBeforeFirst(self):
        pass

    @abc.abstractmethod
    def isAfterLast(self):
        pass

    @abc.abstractmethod
    def getColumnIndex(self, columnName):
        pass

    @abc.abstractmethod
    def getColumnIndexOrThrow(self, columnName):
        pass

    @abc.abstractmethod
    def getColumnName(self, columnIndex):
        pass

    @abc.abstractmethod
    def getColumnNames(self):
        pass

    @abc.abstractmethod
    def getColumnCount(self):
        pass

    @abc.abstractmethod
    def getBlob(self, columnIndex):
        pass

    @abc.abstractmethod
    def getString(self, columnIndex):
        pass

    @abc.abstractmethod
    def getShort(self, columnIndex):
        pass

    @abc.abstractmethod
    def getInt(self, columnIndex):
        pass

    @abc.abstractmethod
    def getLong(self, columnIndex):
        pass

    @abc.abstractmethod
    def getFloat(self, columnIndex):
        pass

    @abc.abstractmethod
    def getDouble(self, columnIndex):
        pass

    @abc.abstractmethod
    def getType(self, columnIndex):
        pass

    @abc.abstractmethod
    def isNull(self, columnIndex):
        pass

    @abc.abstractmethod
    def deactivate(self):
        pass

    @abc.abstractmethod
    def requery(self):
        pass

    @abc.abstractmethod
    def close(self):
        pass

    @abc.abstractmethod
    def isClosed(self):
        pass

    @abc.abstractmethod
    def getWantsAllOnMoveCalls(self):
        pass
