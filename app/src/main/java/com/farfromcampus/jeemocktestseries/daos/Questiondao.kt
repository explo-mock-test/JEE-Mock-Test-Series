package com.farfromcampus.jeemocktestseries.daos

import android.content.ContentValues.TAG
import android.util.Log
import com.farfromcampus.jeemocktestseries.models.Questions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Questiondao {
    private val db = FirebaseFirestore.getInstance()
    private val questionCollection = db.collection("Questions")

    fun addQuestion(question: Questions?) {
        question?.let {
            GlobalScope.launch(Dispatchers.IO) {
                questionCollection.document(question.ques_id).set(it)
            }
        }
    }

    fun getQuestionById(ques_id: String): Task<DocumentSnapshot> {      //Alert !!!
        return questionCollection.document(ques_id).get()
    }
}