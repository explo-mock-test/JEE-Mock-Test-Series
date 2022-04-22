package com.farfromcampus.jeemocktestseries.daos

import com.farfromcampus.jeemocktestseries.models.Mocktest
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Mocktestdao {
    private val db = FirebaseFirestore.getInstance()
    private val mocktestCollection = db.collection("mocktest")

    fun addMockTest(mock: Mocktest) {
        mock?.let {
            GlobalScope.launch(Dispatchers.IO) {
                mocktestCollection.document(mock.mock_id).set(it)
            }
        }
    }

    fun getMockTestById(uId: String): Task<DocumentSnapshot> {
        return mocktestCollection.document(uId).get()
    }
//    fun getAllMockTest():Task<QuerySnapshot>{
//        return mocktestCollection
//    }
}