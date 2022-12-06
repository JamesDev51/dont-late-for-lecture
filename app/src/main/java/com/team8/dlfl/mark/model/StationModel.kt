package com.team8.dlfl.mark.model

import com.google.gson.annotations.SerializedName

data class StationModel(
    @SerializedName("station_name") val stationName: String,
    @SerializedName("station_name_eng") val stationNameEng: String,
    @SerializedName("line_number") val lineNumber: String,
    @SerializedName("ex_code") val externalCode: String,
){
    constructor(): this("","","","")
    override fun toString(): String {
        return "$lineNumber  $stationName  ($externalCode)"
    }
}