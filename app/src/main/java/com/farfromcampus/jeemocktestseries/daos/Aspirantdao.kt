package com.farfromcampus.jeemocktestseries.daos

import com.farfromcampus.jeemocktestseries.models.Aspirant
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Aspirantdao {
    private val db = FirebaseFirestore.getInstance()
    private val aspirantCollection = db.collection("aspirant")

    fun addaspirant(student: Aspirant?) {
        student?.let {
            GlobalScope.launch(Dispatchers.IO) {
                aspirantCollection.document(student.std_id).set(it)
            }
        }
    }

    fun getAspirantById(uId: String): Task<DocumentSnapshot> {
        return aspirantCollection.document(uId).get()
    }
}