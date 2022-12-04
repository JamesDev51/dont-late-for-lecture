package com.team8.dlfl.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team8.dlfl.dto.StationStatus
import com.team8.dlfl.model.MarkModel
import com.team8.dlfl.repository.StationStatusRepository
import kotlinx.coroutines.launch

private const val TAG = "StationStatusViewModel"

class StationStatusViewModel : ViewModel(){
    private val repository: StationStatusRepository = StationStatusRepository()


    private var _stationStatusList = MutableLiveData<ArrayList<StationStatus>>()
    val stationStatusList: LiveData<ArrayList<StationStatus>> = _stationStatusList

    lateinit var selectedStationName:String

    fun retrieveStationStatusList() {
        Log.d(TAG,"retrieveStationStatusList")
        Log.d(TAG,this.toString())
        viewModelScope.launch {
        Log.d(TAG,selectedStationName)
        _stationStatusList.value=repository.getStationStatus(selectedStationName)
        Log.d(TAG, _stationStatusList.value.toString())
        }
    }

}