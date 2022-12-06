package com.team8.dlfl.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL


class TwitterRepository {
    private val topUrlStr = "https://jsonplaceholder.typicode.com/"

    suspend fun readNotices() : JSONArray? {
        val reqUrl = URL(topUrlStr + "todos") // url커넥션을 만듬
        return withContext(Dispatchers.IO) {
            (reqUrl.openConnection() as? HttpURLConnection)?.run {
                requestMethod = "GET"
                connect()
                val reader = inputStream.bufferedReader()
                val strBuilder = StringBuilder()
                while (true) {
                    strBuilder.append(reader.readLine() ?: break)
                }
                JSONArray(strBuilder.toString())
            }
        }
    }



}