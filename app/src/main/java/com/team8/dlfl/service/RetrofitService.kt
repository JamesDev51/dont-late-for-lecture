package com.team8.dlfl.service

import com.team8.dlfl.BuildConfig
import com.team8.dlfl.dto.StationDto

import com.team8.dlfl.model.Station
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("${BuildConfig.API_KEY}/json/SearchInfoBySubwayNameService/1/5/{keyword}/")
    fun getStationNameList(@Path("keyword") keyword: String): Call<StationDto>

}