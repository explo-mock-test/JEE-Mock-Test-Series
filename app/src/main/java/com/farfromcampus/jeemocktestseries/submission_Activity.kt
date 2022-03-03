package com.farfromcampus.jeemocktestseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.farfromcampus.jeemocktestseries.daos.AsptAnswerdao
import com.farfromcampus.jeemocktestseries.models.Test

class submission_Activity : AppCompatActivity() {
    val viewsolution = findViewById<Button>(R.id.viewsolution)
    val home = findViewById<Button>(R.id.homepage)
    val test = intent.getSerializableExtra("test") as Test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)

        val answers = intent.getCharArrayExtra("Answers")!!

        val test = intent.getSerializableExtra("test") as Test

        var totalmarks=0
        for( i in 0 until test.AnswerSheet.size){
            if(answers[i]==test.AnswerSheet[i]) {
                totalmarks=totalmarks+4
            }
        }
        val asptAnswer = AsptAnswerdao()
        asptAnswer.addAsptAnswer(test.mock_id,answers)

        viewsolution.setOnClickListener{
            val intent = Intent(this,solution_Activity::class.java)
            intent.putExtra("test",test)
            startActivity(intent)
        }
//        home.setOnClickListener {
//            val intent = Intent(this,Home_Activity::class.java)
//            startActivity(intent)
////        }
    }
}