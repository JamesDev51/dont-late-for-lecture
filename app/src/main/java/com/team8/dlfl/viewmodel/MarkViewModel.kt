package com.team8.dlfl.viewmodel

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team8.dlfl.R
import com.team8.dlfl.view.mainview.MainActivity
import com.team8.dlfl.dto.CommonResponseDto
import com.team8.dlfl.model.MarkModel
import com.team8.dlfl.model.StationModel
import com.team8.dlfl.repository.MarkRepository
import kotlinx.coroutines.CoroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val TAG = "MarkViewModel"

class MarkViewModel(): ViewModel() {

    @SuppressLint("StaticFieldLeak")
    lateinit var mainActivity: MainActivity
    private val repository: MarkRepository = MarkRepository()
    private var _markList = MutableLiveData<ArrayList<MarkModel>>()
    val markList: LiveData<ArrayList<MarkModel>> = _markList

    lateinit var selectedMark: MarkModel


    fun deleteMark() {
        _markList.value?.forEach{
            if(it.deleteFlag){
                repository.removeMark(it.uid)
            }
        }
    }

    suspend fun uploadMark(departure:StationModel, arrival:StationModel):Boolean  = suspendCoroutine{
        var result: CommonResponseDto
        val newMark=MarkModel(departure,arrival,"",false)
        CoroutineScope(Dispatchers.IO).launch {
            result=repository.postMark(newMark)
            Log.d(TAG,"repository 결과: $result")

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                Toast.makeText(
                    mainActivity, result.message,
                    Toast.LENGTH_SHORT
                ).show()
            },0)
            it.resume(result.status)
        }
    }

    fun retrieveMarkList() {

        viewModelScope.launch {
            repository.readMarkList(_markList)
        }
    }
}