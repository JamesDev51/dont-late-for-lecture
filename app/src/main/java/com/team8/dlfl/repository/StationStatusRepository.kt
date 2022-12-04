package com.team8.dlfl.repository

import android.util.Log
import com.team8.dlfl.dto.StationStatus
import com.team8.dlfl.dto.StationStatusRespDto
import com.team8.dlfl.service.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "StationStatusRepository"


class StationStatusRepository {
    private val stationStatusUrl = "http://swopenAPI.seoul.go.kr"

    suspend fun getStationStatus(stationName:String): ArrayList<StationStatus>? {

        var stationStatusList: ArrayList<StationStatus>? = arrayListOf()
        stationStatusList= arrayListOf(
            StationStatus(rowNum = 1, selectedCount = 5, totalCount = 5, updnLine = "상행", trainLineNm = "수색행-강매", arvlMsg2 = "5전역", arvlMsg3 = "10전역", bstatnNm = "", recptnDt = "", subwayHeading = "문산행", statnNm = ""),
            StationStatus(rowNum = 1, selectedCount = 5, totalCount = 5, updnLine = "상행", trainLineNm = "수색행-강매", arvlMsg2 = "5전역", arvlMsg3 = "10전역", bstatnNm = "", recptnDt = "", subwayHeading = "문산행", statnNm = ""),
            StationStatus(rowNum = 1, selectedCount = 5, totalCount = 5, updnLine = "상행", trainLineNm = "수색행-강매", arvlMsg2 = "5전역", arvlMsg3 = "10전역", bstatnNm = "", recptnDt = "", subwayHeading = "문산행", statnNm = ""),
            StationStatus(rowNum = 1, selectedCount = 5, totalCount = 5, updnLine = "상행", trainLineNm = "수색행-강매", arvlMsg2 = "5전역", arvlMsg3 = "10전역", bstatnNm = "", recptnDt = "", subwayHeading = "문산행", statnNm = "")
        )
        val retrofit = Retrofit.Builder().baseUrl(stationStatusUrl)
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service = retrofit.create(RetrofitService::class.java);
        return withContext(Dispatchers.IO) {
            service.getStationStatus(stationName).enqueue(object: Callback<StationStatusRespDto>{
                override fun onResponse(call: Call<StationStatusRespDto>, response: Response<StationStatusRespDto>) {
                    val resp = response.body();
    //                stationStatusList= resp?.realtimeStationArrival?.stationStatusList
                }
                override fun onFailure(call: Call<StationStatusRespDto>, t: Throwable) {
                    Log.d(TAG, "onFailure 에러: " + t.message.toString());
                }
            })

            Log.d(TAG,stationStatusList.toString())
            stationStatusList
        }
    }
}