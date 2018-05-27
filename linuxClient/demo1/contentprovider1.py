import logging
import extend.binder as binder
import extend.android_componment as android

class ContentProvider1(android.ContentProvider):
    def __init__(self, looper):
        android.ContentProvider.__init__(self, '/contentprovider1', looper)

    def onCreate(self):
        pass

    def delete(self, uri, selection, selectionArgs):
        return 0

    def getType(self, uri):
        logging.debug('getType')
        return ''

    def insert(self, uri, values):
        return None

    def query(self, uri, projection, selection, selectionArgs, sortOrder):
        logging.debug('query')
        return Cursor1(self.looper)

    def update(self, uri, values, selection, selectionArgs):
        return 0

class Cursor1(android.Cursor):
    def __init__(self, looper):
        android.Cursor.__init__(self, looper)

    def getCount(self):
        return 1

    def getPosition(self):
        return 0

    def move(self, offset):
        return True

    def moveToPosition(self, position):
        return True

    def moveToFirst(self):
        return True

    def moveToLast(self):
        return True

    def moveToNext(self):
        return False

    def moveToPrevious(self):
        return True

    def isFirst(self):
        return True

    def isLast(self):
        return True

    def isBeforeFirst(self):
        return True

    def isAfterLast(self):
        return True

    def getColumnIndex(self, columnName):
        pass

    def getColumnIndexOrThrow(self, columnName):
        pass

    def getColumnName(self, columnIndex):
        return 'name'

    def getColumnNames(self):
        pass

    def getColumnCount(self):
        return 1

    def getBlob(self, columnIndex):
        pass

    def getString(self, columnIndex):
        logging.debug('getString')
        return "TestTestTest"

    def getShort(self, columnIndex):
        pass

    def getInt(self, columnIndex):
        pass

    def getLong(self, columnIndex):
        pass

    def getFloat(self, columnIndex):
        pass

    def getDouble(self, columnIndex):
        pass

    def getType(self, columnIndex):
        return 3

    def isNull(self, columnIndex):
        return False

    def deactivate(self):
        pass

    def requery(self):
        return False

    def close(self):
        pass

    def isClosed(self):
        return False

    def getWantsAllOnMoveCalls(self):
        return False
