package com.team8.dlfl.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.team8.dlfl.dto.CommonResponseDto
import com.team8.dlfl.model.MarkModel
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val  TAG = "MarkRepository"

class MarkRepository {

    private val auth = Firebase.auth
    private val database = Firebase.database
    private val reference = database.getReference("mark")
    private val uid=auth.currentUser?.uid

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
                                    Log.d(TAG,"파이어베이스에 등록 성공: ${task.result}")
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

    suspend fun readMarkList(markList: MutableLiveData<ArrayList<MarkModel>>) {

        withContext(Dispatchers.IO) {

            runBlocking {
                uid?.let {
                    reference.child(it).addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val dbMarkList = ArrayList<MarkModel>()
                            for (child in snapshot.children) {
                                val dbMark = child.getValue(MarkModel::class.java)
                                dbMark?.uid= child.key.toString()
                                dbMark?.let { mark ->
                                    Log.d(TAG, mark.toString())
                                    dbMarkList.add(mark) }
                            }
                            markList.value=dbMarkList
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }
        }
    }

    fun removeMark(markUid: String) {
        uid?.let{u->
            reference.child(u).child(markUid).removeValue()
        }
    }

}
