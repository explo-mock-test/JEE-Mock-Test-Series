package com.farfromcampus.jeemocktestseries

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.farfromcampus.jeemocktestseries.models.Test
import com.google.firebase.storage.FirebaseStorage
import com.qdot.mathrendererlib.MathRenderView


class mocktestActivity : AppCompatActivity() {

    var test = Test()
    var TotalMarks = 0
    var chkoption:Array<Int> = Array(100){-1}
    val mp = mapOf(0 to "a" ,1 to "b",2 to "c" ,3 to "d" ,4 to "e")
    private var i: Int = 0
    //Options
    private lateinit var radiogrp : RadioGroup
    private lateinit var OptionA:RadioButton
    private lateinit var OptionB:RadioButton
    private lateinit var OptionC:RadioButton
    private lateinit var OptionD:RadioButton
    private lateinit var OptionE:RadioButton
    private lateinit var OptionAView:MathRenderView
    private lateinit var OptionBView:MathRenderView
    private lateinit var OptionCView:MathRenderView
    private lateinit var OptionDView:MathRenderView
    private lateinit var OptionEView:MathRenderView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mocktest)
        init()
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
    fun init(){
        radiogrp = findViewById(R.id.options) as RadioGroup

        OptionA = findViewById(R.id.a) as RadioButton
        OptionB = findViewById(R.id.b) as RadioButton
        OptionC = findViewById(R.id.c) as RadioButton
        OptionD = findViewById(R.id.d) as RadioButton
        OptionE = findViewById(R.id.e) as RadioButton

        OptionAView = findViewById(R.id.optiona) as MathRenderView
        OptionBView = findViewById(R.id.optionb) as MathRenderView
        OptionCView = findViewById(R.id.optionc) as MathRenderView
        OptionDView = findViewById(R.id.optiond) as MathRenderView
        OptionEView = findViewById(R.id.optione) as MathRenderView
    }

    private fun changeQuestion(x: Int) {

        i = x
        if(x<0){
            Toast.makeText(this, "Index reach Negative value ${x}", Toast.LENGTH_SHORT).show()
            i = 0
        }else if(x > test.Set.size){
            Toast.makeText(this, "Index reach out of Scope ${x}", Toast.LENGTH_SHORT).show()
            i = test.Set.size
        }
        radiogrp.clearCheck()
        if(chkoption[i]!=-1){
            radiogrp.check(chkoption[x])
        }
            var mathview = findViewById<MathRenderView>(R.id.question)
            findViewById<TextView>(R.id.question_number).text ="Question  ${i+1}"
            mathview.text = test.Set[i].question
            if (test.Set[i].image.isNotEmpty()) {
                findViewById<ImageView>(R.id.Question_image).isVisible = true
                val storage = FirebaseStorage.getInstance()
                val gsReference = storage.getReferenceFromUrl(test.Set[i].image)
                Glide.with(this@mocktestActivity).load(gsReference).into(findViewById(R.id.Question_image))
            }
            OptionAView.text = test.Set[i].option[0]
            OptionBView.text = test.Set[i].option[1]
            OptionCView.text = test.Set[i].option[2]
            OptionDView.text = test.Set[i].option[3]
            if (test.Set[i].option[4].isNotEmpty()) {
                OptionEView.isVisible = true
                    OptionEView.text = test.Set[i].option[4]
            }else{
                OptionEView.isVisible = false
            }
        }

    fun saveAndNext(view: View) {
        val x = radiogrp.checkedRadioButtonId
        if (x != -1) {
            val checkedOption = findViewById<RadioButton>(x).resources.getResourceEntryName(x)
            chkoption[i]=x
            test.AnswerSheet.set(i,checkedOption)
        }
        if(test.Set[i].answer == test.AnswerSheet[i]){
            TotalMarks+=4
        }else{
            if(test.AnswerSheet[i]!=""){
                TotalMarks--
            }
        }
        i++
        if(i == test.Set.size){
            submit(view)
            i--
        }
        changeQuestion(i)
    }
    fun skip(view: View) {
        i--
        changeQuestion(i)
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
        intent1.putExtra("TotalMarks",TotalMarks)
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
    override fun onBackPressed(){
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Exit Alert")
            .setMessage("Do You Want To Exit Mock test?")
            .setPositiveButton(android.R.string.ok) { dialog, whichButton ->
                startActivity(Intent(this,MainActivity::class.java))
                super.onBackPressed()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, whichButton ->

            }.show()
    }
}