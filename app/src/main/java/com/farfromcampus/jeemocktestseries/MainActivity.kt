package com.farfromcampus.jeemocktestseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.models.Mocktest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (GoogleSignIn.getLastSignedInAccount(this) == null) {
            startActivity(Intent(this, getstartedActivity::class.java))
            finish()
        }

////        var mock = Mocktest()
////        mock.name = "WarmUp JEE MockTest"
////        mock.testtype = "Full"
////        mock.time = 3
////        mock.test_number=2
////        mock.mock_id= "testingappmocktest1"
////        var quesids:ArrayList<String> = ArrayList()
////        for(i in 1..90){
////            quesids.add("Warmup_idx_$i")
////        }
////        mock.ques_ids=quesids
////        Mocktestdao().addMockTest(mock)
    }


    override fun onResume() {
        super.onResume()
        var mocks:ArrayList<Mocktest> = ArrayList()
        runBlocking {
            Mocktestdao().getAllMockTest().addOnSuccessListener { document ->
                for (dat in document) {
                    mocks.add(dat.toObject(Mocktest::class.java))
                }
            }.await()
        }
        val recyclerview = findViewById<RecyclerView>(R.id.testsview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter1 = Tests_Adapter(mocks)
        recyclerview.adapter = adapter1
    }
}