package com.team8.dlfl.dto

import com.google.gson.annotations.SerializedName

data class Message(
    val code:String,
    val developerMessage:String,
    val message:String,
    val status: Int,
    val total: Int)
data class StationStatus (
    val rowNum: Long,
    val selectedCount: Long,
    val totalCount: Long,

    val updnLine: String,
    val trainLineNm: String,
    val subwayHeading: String,

    val statnNm: String,

    val bstatnNm: String,
    val recptnDt: String,
    val arvlMsg2: String,
    val arvlMsg3: String,
)
data class StationStatusRespDto(
                                @SerializedName("realtimeArrivalList") val realtimeStationArrival:ArrayList<StationStatus>,
                                @SerializedName("errorMessage") val message: Message)