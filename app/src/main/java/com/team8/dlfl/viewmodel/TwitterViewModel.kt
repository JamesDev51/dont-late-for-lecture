package com.team8.dlfl.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team8.dlfl.repository.TwitterRepository
import kotlinx.coroutines.launch



data class Notice(val title: String, val name: String, val people: String)

class TwitterViewModel: ViewModel() {
    private val repository = TwitterRepository()

    private val _notices = MutableLiveData<ArrayList<Notice>>()
    val notices : LiveData<ArrayList<Notice>> = _notices

    fun retrieveNotices() {
        viewModelScope.launch {
            repository.readNotices()?.let { jsonNotices ->
                val nList = ArrayList<Notice>()
                for (idx in 0 until 20) {
                    jsonNotices.getJSONObject(idx)?.let { obj ->
                        nList += Notice(obj.getString("title"), obj.getString("id") + ".시위 정보", obj.getString("completed"))
                    }
                }
                _notices.value = nList
            }
        }
    }
}