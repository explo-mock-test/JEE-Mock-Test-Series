package com.farfromcampus.jeemocktestseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.farfromcampus.jeemocktestseries.daos.AsptAnswerdao
import com.farfromcampus.jeemocktestseries.models.Test

class submission_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)

        val test = intent.getSerializableExtra("gettest") as Test
        var totalmarks = intent.getIntExtra("TotalMarks",0)

        findViewById<TextView>(R.id.marks).text = totalmarks.toString()

        findViewById<Button>(R.id.viewsolution).setOnClickListener {
            val intent3 = Intent(this, solution_Activity::class.java)
            intent3.putExtra("test", test)
            startActivity(intent3)
        }
        findViewById<Button>(R.id.homepage).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
    override fun onBackPressed(){
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Message")
            .setMessage("Do You Want To Exit Mocktest Result?")
            .setPositiveButton(android.R.string.ok) { dialog, whichButton ->
                startActivity(Intent(this,MainActivity::class.java))
            }
            .setNegativeButton(android.R.string.cancel) { dialog, whichButton ->

            }.show()
    }
}