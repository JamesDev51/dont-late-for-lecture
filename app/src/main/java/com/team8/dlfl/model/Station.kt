package com.team8.dlfl.model

import com.google.gson.annotations.SerializedName

data class Station(
    @SerializedName("STATION_CD") val stationCode: String,
    @SerializedName("STATION_NM") val stationName: String,
    @SerializedName("LINE_NUM") val lineNumber: String,
    @SerializedName("FR_CODE") val externalCode: String
){
    override fun toString(): String {
        return "$lineNumber - $stationName"
    }
}