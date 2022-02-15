package com.farfromcampus.jeemocktestseries.daos

import com.farfromcampus.jeemocktestseries.models.Questions
import com.farfromcampus.jeemocktestseries.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Questiondao {
    private val db = FirebaseFirestore.getInstance()
    private val questionCollection = db.collection("questions")

    fun addQuestion(question: Questions?) {
        question?.let {
            GlobalScope.launch(Dispatchers.IO) {
                questionCollection.document(question.ques_id.toString()).set(it)
            }
        }
    }

    fun getUserById(uId: String): Task<DocumentSnapshot> {
        return questionCollection.document(uId).get()
    }
}