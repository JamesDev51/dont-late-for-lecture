package com.team8.dlfl.model

data class MarkModel(val departure: StationModel, val arrival: StationModel) {
    lateinit var uid:String
    constructor(): this(StationModel(),StationModel())
}