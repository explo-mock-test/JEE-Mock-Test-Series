package com.farfromcampus.jeemocktestseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class getstartedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getstarted)
    }

    fun getstarted(view: View) {
        val intent = Intent(this,signinActivity::class.java)
        startActivity(intent)
    }
}