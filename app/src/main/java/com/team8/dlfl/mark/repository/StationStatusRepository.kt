package com.team8.dlfl.mark.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
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


//https://blog.gangnamunni.com/post/android-coroutine/
class StationStatusRepository {
    private val stationStatusUrl = "http://swopenAPI.seoul.go.kr"

    suspend fun getStationStatus(stationName: String, _stationStatusList: MutableLiveData<ArrayList<StationStatus>>) {


        val retrofit = Retrofit.Builder().baseUrl(stationStatusUrl)
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service = retrofit.create(RetrofitService::class.java);
        return withContext(Dispatchers.IO) {

                service.getStationStatus(stationName).enqueue(object: Callback<StationStatusRespDto>{
                    override fun onResponse(call: Call<StationStatusRespDto>, response: Response<StationStatusRespDto>) {
                        val resp = response.body();
                        _stationStatusList.value= resp?.realtimeStationArrival
                        Log.d(TAG,"응답 옴")

                    }
                    override fun onFailure(call: Call<StationStatusRespDto>, t: Throwable) {
                        Log.d(TAG, "onFailure 에러: " + t.message.toString());
                    }
                })
        }
    }
}