package com.team8.dlfl.service

import com.team8.dlfl.dto.StationStatusRespDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {


    @GET("/api/subway/6d504d7745616c73373979586f7a4c/json/realtimeStationArrival/0/20/{stationName}")
    fun getStationStatus(@Path("stationName") stationName: String): Call<StationStatusRespDto>

}