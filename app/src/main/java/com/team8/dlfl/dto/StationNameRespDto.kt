package com.team8.dlfl

import com.team8.dlfl.model.Station

data class StationNameRespDto(
    val SearchInfoBySubwayNameService: SearchInfoBySubwayNameService
)

data class SearchInfoBySubwayNameService(
    val RESULT: RESULT,
    val list_total_count: Int,
    val row: List<Station>
)

data class RESULT(
    val CODE: String,
    val MESSAGE: String
)

