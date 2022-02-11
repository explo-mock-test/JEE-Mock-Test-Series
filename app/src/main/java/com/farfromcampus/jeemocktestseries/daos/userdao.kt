package com.farfromcampus.jeemocktestseries.daos

import com.farfromcampus.jeemocktestseries.models.Users
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class userdao {
    val db = FirebaseFirestore.getInstance()
    val usercollection = db.collection("user")

    fun adduser(users: Users){
        users?.let{
            GlobalScope.launch(Dispatchers.IO) {
                usercollection.document(users.user_id).set(it)
            }
        }
    }
}