package com.team8.dlfl.service

import com.team8.dlfl.dto.StationStatusRespDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("/api/subway/(인증키)/xml/realtimeStationArrival/0/5/{stationName}")
    fun getStationStatus(@Path("stationName") stationName: String): Call<StationStatusRespDto>

}