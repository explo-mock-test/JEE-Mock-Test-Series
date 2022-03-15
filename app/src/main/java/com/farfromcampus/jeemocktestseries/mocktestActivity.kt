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
    private var marked: ArrayList<String> = ArrayList()
    var test = Test()
    var chkoption:Array<Int> = Array(100){-1}
    val mp = mapOf(0 to "a" ,1 to "b",2 to "c" ,3 to "d" ,4 to "e")

    private var i: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mocktest)
        test = intent.getSerializableExtra("gettest") as Test

        val countTime: TextView = findViewById(R.id.countTime)
        object : CountDownTimer(60000*180, 1000) {
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
        changeQuestion(0)
    }

    private fun changeQuestion(x: Int) {
        i = x
            findViewById<RadioGroup>(R.id.options).clearCheck()
            findViewById<TextView>(R.id.question_number).text ="Question  ${x+1}"
            findViewById<TextView>(R.id.question).text = test.Set[x].question

            if(test.AnswerSheet[x] != " "){
                findViewById<RadioGroup>(R.id.options).check(chkoption[x])
            }
            if (test.Set[x].image.isNotEmpty()) {
                Glide.with(this@mocktestActivity).load(test.Set[x].image).into(findViewById(R.id.Question_image))
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
        val x = findViewById<RadioGroup>(R.id.options).checkedRadioButtonId
        val checkedOption = findViewById<RadioButton>(x)
        var idd = ""
        chkoption[i]=x

        if(x != -1) { for (k in 0..4) { if (test.Set[i].option[k] == checkedOption.text) { idd = mp[k]!! } } }//???Alert

        if (x != -1 && test.AnswerSheet[i]!= idd) {
            test.AnswerSheet.set(i,idd)
        }
        if(x == test.Set.size-1){
            submit(view)
        }
        i++
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
        val intent1 = Intent(this, submission_Activity::class.java)
        intent1.putExtra("gettest",test)
        startActivity(intent1)
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