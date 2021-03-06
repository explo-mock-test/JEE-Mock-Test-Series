package com.farfromcampus.jeemocktestseries.daos

import com.farfromcampus.jeemocktestseries.models.AsptAnswer
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AsptAnswerdao {
    private val db = FirebaseFirestore.getInstance()
    private val AsptAnswerCollection = db.collection("aspirantAnswers")
    val auth = Firebase.auth

    fun addAsptAnswer(mock_id:String,Answer: ArrayList<String>) {
        val aspirantid = auth.currentUser!!.uid
        GlobalScope.launch(Dispatchers.IO) {
//            val aspirantdao = Aspirantdao()
//            val aspirant = aspirantdao.getAspirantById(aspirantid).await().toObject(Aspirant::class.java)
            val aspirantanswer = AsptAnswer(aspirantid+mock_id,mock_id,Answer)
            AsptAnswerCollection.document(aspirantid).set(aspirantanswer)
        }
    }

    fun getAsptAnswerById(uId: String): Task<DocumentSnapshot> {
        return AsptAnswerCollection.document(uId).get()
    }
}