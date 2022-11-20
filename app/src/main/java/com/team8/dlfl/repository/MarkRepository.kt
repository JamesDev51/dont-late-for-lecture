package com.team8.dlfl.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.team8.dlfl.model.MarkModel

private const val  TAG = "MarkRepository"

class MarkRepository {

    val auth = Firebase.auth
    val database = Firebase.database
    val reference = database.reference
    val marks = emptyList<MarkModel>()

    val userUid = auth.uid

    fun postMark(mark: MarkModel) {
        Log.d(TAG, "userUid: $userUid")
        userUid?.let {
            reference.child(it)
                .child("marks")
                .push()
                .setValue(mark) }
    }
    fun observeMarks() {

    }

}