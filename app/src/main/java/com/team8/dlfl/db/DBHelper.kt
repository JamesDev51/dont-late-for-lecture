package com.team8.dlfl.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private const val TAG = "DataBaseHelper"  //Logcat에 출력할 태그이름

class DBHelper(val context: Context?) : SQLiteOpenHelper(context, "dlfl", null, 1) {

    private val DB_NAME="dlfl.db"
    private val DB_PATH = "/data/data/"+context?.packageName+"/databases/"


    lateinit var mDataBase: SQLiteDatabase

    fun createDataBase() {

        val mDataBaseExist:Boolean = checkDataBase ()
        if (!mDataBaseExist) {
            super.getReadableDatabase()
            super.close()
            try {
                Log.d(TAG, "createDatabase database 시도")
                copyDataBase()
                Log.d(TAG, "createDatabase database created")
            } catch (e: IOException) {
                throw IOException("ErrorCopyingDatabase")
            }
        }

    }

    private fun copyDataBase() {

        try {
            val inputStream = context?.assets?.open(DB_NAME)
            val outFileName = DB_PATH + DB_NAME
            val outputStream = FileOutputStream(outFileName)
            val mBuffer = ByteArray(1024)
            var mLength = inputStream?.read(mBuffer)
            while ((mLength ?: 0) > 0) {
                outputStream.write(mBuffer,0,mLength?:0)
                mLength = inputStream?.read(mBuffer)
                }
            outputStream.flush()
            outputStream.close()
            inputStream?.close()
            Log.d(TAG,"dbcopy 완료")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d(TAG,"dbcopy중에 IOException 발생")
        }

    }

    fun openDataBase(): Boolean {
        val mPath = DB_PATH+DB_NAME

        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY)

        return true
    }

    private fun checkDataBase(): Boolean {
        val dbFile= File(DB_PATH+DB_NAME)
        Log.d(TAG, "존재 ? : " + dbFile.exists())

        return dbFile.exists()
    }

    override fun onCreate(p0: SQLiteDatabase?) {
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.disableWriteAheadLogging()
    }
}