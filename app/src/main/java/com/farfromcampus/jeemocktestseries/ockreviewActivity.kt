package com.farfromcampus.jeemocktestseries

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
//import com.farfromcampus.jeemocktestseries.ViewModels.TestViewmodel
//import com.farfromcampus.jeemocktestseries.ViewModels.TestViewmodelFactory
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.daos.Questiondao
import com.farfromcampus.jeemocktestseries.models.Mocktest
import com.farfromcampus.jeemocktestseries.models.Questions
import com.farfromcampus.jeemocktestseries.models.Test
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class ockreviewActivity : AppCompatActivity() {

//    lateinit var testViewmodel : TestViewmodel

    var testnumber = 0
    var test = Test()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ockreview)

        val intent2 = Intent(this,mocktestActivity::class.java)
        findViewById<Button>(R.id.startx).setOnClickListener { view: View ->
            intent2.putExtra("gettest", test)
            startActivity(intent2)
        }

    }

    override fun onStart() {
        super.onStart()
        val mock_id = intent.getStringExtra("mock_id")!!
        test.mock_id = mock_id
        GlobalScope.launch(Dispatchers.Main) {
            val quesid =
                Mocktestdao().getMockTestById(mock_id).await().toObject(Mocktest::class.java)!!

//            testViewmodel = ViewModelProvider(this@ockreviewActivity,TestViewmodelFactory(quesid)).get(TestViewmodel::class.java)

            testnumber = quesid.test_number
            for (i in 0..20) {
                val Ques = Questiondao().getQuestionById(quesid.ques_ids[i]).await()
                    .toObject(Questions::class.java)!!

                test.Set.add(Ques)
                delay(5)
                test.subject[0] = 0                                 //Physics
                if(test.subject[1]==-1 && Ques.subject_id==1)test.subject[1]=i  //Chemistry
                if(test.subject[2]==-1 && Ques.subject_id==2)test.subject[2]=i  //Mathematics


                findViewById<TextView>(R.id.status).text = test.Set.size.toString()
                if (i == 20) {
                    execute()
                }
            }
        }

    }


    @SuppressLint("SetTextI18n")
    fun execute() {
        var mockNum =findViewById<TextView>(R.id.mocknumber)
        var Ques = findViewById<TextView>(R.id.questions)
        var details = findViewById<TextView>(R.id.details)
        findViewById<ProgressBar>(R.id.progressBar2).isVisible = false
        findViewById<TextView>(R.id.textView3).isVisible = false

        mockNum.isVisible = true
        Ques.isVisible = true
        details.isVisible =true

        findViewById<TextView>(R.id.instructions).isVisible = true
        findViewById<Button>(R.id.startx).isVisible = true

        mockNum.text = "Mock Test $testnumber"

        val a = test.subject[1]
        val b = test.subject[2]
        val c = test.Set.size

        Ques.text = "$c Questions"

        details.text = "$a Questions of Physics\n${c-b} Questions of Mathematics\n${b-a} Questions of Chemistry\n" + "Time- 3Hr"


    }

    //    override fun onBackPressed(){
//        AlertDialog.Builder(this)
//            .setTitle("Exit Alert")
//            .setMessage("Do You Want To Exit Mock test?")
//            .setPositiveButton(android.R.string.ok) { dialog, whichButton ->
//                super.onBackPressed()
//            }
//            .setNegativeButton(android.R.string.cancel) { dialog, whichButton ->
//
//            }.show()
//    }
}