package com.farfromcampus.jeemocktestseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn



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
}