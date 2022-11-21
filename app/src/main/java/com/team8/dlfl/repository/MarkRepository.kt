package com.team8.dlfl.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.team8.dlfl.dto.CommonResponseDto
import com.team8.dlfl.model.MarkModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val  TAG = "MarkRepository"

class MarkRepository {

    private val auth = Firebase.auth
    private val database = Firebase.database
    private val reference = database.getReference("mark")
    private val uid=auth.currentUser?.uid
    val marks = emptyList<MarkModel>()

    suspend fun postMark(mark: MarkModel) = suspendCoroutine{

        var result: CommonResponseDto
        CoroutineScope(Dispatchers.IO).launch {
            runBlocking {

                try {
                    Log.d(TAG, "userUid: $uid")
                    uid?.let {u->
                        reference.child(u)
                            .push()
                            .setValue(mark).addOnCompleteListener {task ->
                                if(task.isSuccessful){
                                    Log.d(TAG,"파이어베이스에 등록 성공")
                                    result=CommonResponseDto(true,"즐겨찾기가 등록되었습니다.")
                                    it.resume(result)
                                }
                            }
                    }?.await()
                } catch (e: FirebaseException) {
                    Log.d(TAG, "에러 메세지 :${e.message}")
                    result= CommonResponseDto(false, "${e.message}")

                    it.resume(result)
                }


            }
        }

    }

    fun observeMarkList(markList: MutableLiveData<List<MarkModel>>) {

        uid?.let {
            reference.child(it).addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot){

                    val dbMarkList = emptyList<MarkModel>().toMutableList()
                    for (child in snapshot.children) {
                        val dbMark = child.getValue(MarkModel::class.java)
                        Log.d(TAG,dbMark.toString())

                        dbMark?.let { mark -> dbMarkList.add(mark) }
                    }


                    markList.postValue(dbMarkList)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

}
