package com.farfromcampus.jeemocktestseries.daos

import android.content.ContentValues.TAG
import android.util.Log
import com.farfromcampus.jeemocktestseries.models.Questions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.w3c.dom.Document

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


//    fun getAllQuestionsByIds(ques_ids:ArrayList<String>): ArrayList<Questions> {
////        var quests:ArrayList<Questions> = ArrayList()
//        val valuess =  questionCollection.whereIn("ques_id", ques_ids).get()
////        for(i in documents){
////            quests.add(i.toObject(Questions::class.java))
////        }
////        return documents
//
//    }
    fun getAllQuestionsByIds(ques_ids:List<String>): Task<QuerySnapshot> {
        return questionCollection.whereIn("ques_id", ques_ids).get()
//            .get()
//            .addOnSuccessListener { document->
//                for(dat in document){
//                    quesList.add(dat.toObject(Questions::class.java))
//                }
//            }
//            .addOnFailureListener {
//                Log.d("TAG","Failed")
//            }
    }

}
