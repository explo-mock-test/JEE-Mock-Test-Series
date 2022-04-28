package com.farfromcampus.jeemocktestseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.models.Mocktest
import com.farfromcampus.jeemocktestseries.models.Questions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (GoogleSignIn.getLastSignedInAccount(this) == null) {
            startActivity(Intent(this, getstartedActivity::class.java))
            finish()
        }

        val mock_id = "testingfirstmocktest"
        val intent = Intent(this, ockreviewActivity::class.java)
        findViewById<Button>(R.id.button).setOnClickListener {
            intent.putExtra("mock_id", mock_id)
            startActivity(intent)
        }




    }

    override fun onStart() {
        super.onStart()
        var mocks:ArrayList<Mocktest> = ArrayList()
        runBlocking{
            Log.d("TAGG",Mocktestdao().getAllMockTest().await().toString())
            Mocktestdao().getAllMockTest().addOnSuccessListener { document ->
                for (dat in document) {
                    Log.d("TAGG2",dat.toObject(Mocktest::class.java).toString())
                    mocks.add(dat.toObject(Mocktest::class.java))
                    Log.d("TAGG45",mocks.size.toString())
                }
            }.await()
        }
        Log.d("TAGG",mocks.size.toString())

        val recyclerview = findViewById<RecyclerView>(R.id.testsview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter1 = Tests_Adapter(mocks)
        recyclerview.adapter = adapter1
    }
}