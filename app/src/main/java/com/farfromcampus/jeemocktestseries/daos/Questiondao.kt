package com.farfromcampus.jeemocktestseries.daos

import com.farfromcampus.jeemocktestseries.models.Questions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
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
    fun getAllQuestionsByIds(ques_ids:List<String>): Task<QuerySnapshot> {
        return questionCollection.whereIn("ques_id", ques_ids).get()
    }
}
