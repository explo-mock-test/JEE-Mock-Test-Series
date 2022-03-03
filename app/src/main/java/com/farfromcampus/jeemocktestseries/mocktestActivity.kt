package com.farfromcampus.jeemocktestseries

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.farfromcampus.jeemocktestseries.models.Test


class mocktestActivity : AppCompatActivity() {
    val test = intent.getSerializableExtra("test") as Test

    var Answers: ArrayList<Char> = ArrayList()
    private var marked: ArrayList<String> = ArrayList()

    private var i: Int = 0

    private var ques_num = findViewById<TextView>(R.id.question_number)
    var ques = findViewById<TextView>(R.id.question)
    var ques_img = findViewById<ImageView>(R.id.Question_image)
    var Radiooptions = findViewById<RadioGroup>(R.id.options)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mocktest)

        val countTime: TextView = findViewById(R.id.countTime)
        object : CountDownTimer(3600*60*3000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60

                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli

                countTime.text = "${elapsedHours} hs ${elapsedMinutes} min ${elapsedSeconds} sec"
            }

            override fun onFinish() {
                submition()
            }
        }.start()
    }

    private fun changeQuestion(x: Int) {
        i = x
            Radiooptions.clearCheck()
            ques_num.text = x.toString()
            ques.text = test.Set[x].question
            if (test.Set[x].image.isNotEmpty()) {
                Glide.with(this@mocktestActivity).load(test.Set[x].image).into(ques_img)
            }

            findViewById<RadioButton>(R.id.a).text = test.Set[x].option[0]
            findViewById<RadioButton>(R.id.b).text = test.Set[x].option[1]
            findViewById<RadioButton>(R.id.c).text = test.Set[x].option[2]
            findViewById<RadioButton>(R.id.d).text = test.Set[x].option[3]

            if (test.Set[x].option[4].isNotEmpty()) {
                findViewById<RadioButton>(R.id.e).text = test.Set[x].option[4]
            }

        }

    fun saveAndNext(view: View) {
        val x = Radiooptions.checkedRadioButtonId
        if (x != -1) {
            Answers[i] = x.toChar()
            i++
        }
        if(x == test.Set.size-1){
            submit(view)
        }
        changeQuestion(i)
    }

    fun skip(view: View) {

        i++
        changeQuestion(i)
    }

    fun markForReview(view: View) {
        if (test.Set[i].ques_id in marked) else
            marked.add(test.Set[i].ques_id)
        Toast.makeText(this, "Marked For Review Successfully", Toast.LENGTH_SHORT).show()
    }

    fun chemistry(view: View) {
        i = test.subject[1]
        changeQuestion(i)
    }

    fun physics(view: View) {
        i = test.subject[0]
        changeQuestion(i)
    }

    fun maths(view: View) {
        i = test.subject[2]
        changeQuestion(i)
    }

    fun submition(){
        val intent = Intent(this, submission_Activity::class.java)
        intent.putExtra("Answers",Answers)
        intent.putExtra("test",test)

        startActivity(intent)
    }

    fun submit(view: View) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Submission")
            builder.setMessage("Are you sure to Submit Test?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Submit") { dialogInterface, which ->
                submition()
            }
            builder.setNegativeButton("No") { dialogInterface, which ->
                Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

    }
}