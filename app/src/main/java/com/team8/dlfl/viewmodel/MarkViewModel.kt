package com.team8.dlfl.viewmodel

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team8.dlfl.activity.MainActivity
import com.team8.dlfl.dto.CommonResponseDto
import com.team8.dlfl.model.MarkModel
import com.team8.dlfl.model.StationModel
import com.team8.dlfl.repository.MarkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val TAG = "AddViewModel"

class MarkViewModel(): ViewModel() {

    @SuppressLint("StaticFieldLeak")
    lateinit var mainActivity: MainActivity
    val repository: MarkRepository = MarkRepository()
    private var _markList = MutableLiveData<List<MarkModel>>(emptyList())
    val markList: LiveData<List<MarkModel>> get() = _markList

    suspend fun init(): Boolean {
        return repository.observeMarkList(_markList)
    }

    suspend fun uploadMark(departure:StationModel, arrival:StationModel):Boolean  = suspendCoroutine{
        var result=CommonResponseDto(false,"")
        val newMark=MarkModel(departure,arrival)
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
}