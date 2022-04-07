package com.farfromcampus.jeemocktestseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.daos.Questiondao
import com.farfromcampus.jeemocktestseries.models.Mocktest
import com.farfromcampus.jeemocktestseries.models.Questions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(GoogleSignIn.getLastSignedInAccount(this)==null){
            startActivity(Intent(this, getstartedActivity::class.java))
            finish()
        }

        val mock_id = "testingfirstmocktest"
        val intent =Intent(this,ockreviewActivity::class.java)
        findViewById<Button>(R.id.button).setOnClickListener {
            intent.putExtra("mock_id",mock_id)
            startActivity(intent)
        }
    }
}