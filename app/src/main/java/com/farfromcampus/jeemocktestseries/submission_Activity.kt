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

        var totalmarks = 0
        for (i in 0 until test.Set.size) {
            if (test.Set[i].answer == test.AnswerSheet[i]) {
                totalmarks = totalmarks + 4
            }
        }
        findViewById<TextView>(R.id.marks).text = totalmarks.toString()

//        val asptAnswer = AsptAnswerdao()
//        val listss = test.AnswerSheet.toList()
//        asptAnswer.addAsptAnswer(test.mock_id, listss as ArrayList<String>)

        findViewById<Button>(R.id.viewsolution).setOnClickListener {
            val intent3 = Intent(this, solution_Activity::class.java)
            intent3.putExtra("test", test)
            startActivity(intent3)
        }
        findViewById<Button>(R.id.homepage).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
//        }
        }
    }
}