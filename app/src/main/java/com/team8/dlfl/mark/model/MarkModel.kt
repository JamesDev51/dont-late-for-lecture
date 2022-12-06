package com.team8.dlfl.mark.model

data class MarkModel(val departure: StationModel, val arrival: StationModel, var uid: String, var deleteFlag: Boolean) {
    constructor(): this(StationModel(), StationModel(),"",false)

    override fun toString(): String {
        return "$departure $arrival $uid $deleteFlag"
    }
}