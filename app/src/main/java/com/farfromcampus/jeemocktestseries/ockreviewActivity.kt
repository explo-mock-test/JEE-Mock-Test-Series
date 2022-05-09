package com.farfromcampus.jeemocktestseries

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.daos.Questiondao
import com.farfromcampus.jeemocktestseries.models.Mocktest
import com.farfromcampus.jeemocktestseries.models.Questions
import com.farfromcampus.jeemocktestseries.models.Test
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.lang.Thread.sleep

class ockreviewActivity : AppCompatActivity() {

    var isload =false
    var testnumber = 0
    var test = Test()
    var quesid = Mocktest()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ockreview)

        val intent2 = Intent(this,mocktestActivity::class.java)
        findViewById<Button>(R.id.startx).setOnClickListener { view: View ->
            intent2.putExtra("gettest", test)
            intent2.putExtra("mock",quesid)
            startActivity(intent2)
        }

    }

    override fun onStart() {
        super.onStart()
        val mock_id = intent.getStringExtra("mock_id")!!
        test.mock_id = mock_id
        GlobalScope.launch(Dispatchers.IO) {
            quesid = Mocktestdao().getMockTestById(mock_id).await().toObject(Mocktest::class.java)!!
            testnumber = quesid.test_number
            getMocktest(quesid)

            GlobalScope.launch(Dispatchers.Main){
                    delay(2000)
                    execute()
            }
        }
//        execute()
    }

    suspend fun getMocktest(quesid:Mocktest){
        val chunks = quesid.ques_ids.chunked(10) as ArrayList
        var questionTasks: ArrayList<Task<QuerySnapshot>> = ArrayList()
        for (chunk in chunks) {
//            Log.d("TAGG!",Questiondao().getAllQuestionsByIds(chunk).await().toString())
            val task = Questiondao().getAllQuestionsByIds(chunk)
                .addOnSuccessListener { document ->
                    for (dat in document) {
                        test.Set.add(dat.toObject(Questions::class.java))
                    }
                }
                .addOnFailureListener {
                    Log.d("TAG", "Failed")
                }
            questionTasks.add(task)
        }
        for (task in questionTasks) {
            task.await()
        }
    }


        @SuppressLint("SetTextI18n")
    fun execute() {
            isload=true
        test.Set.sortedWith(compareBy({ it.ques_id }))
        var maths : ArrayList<Int> = ArrayList()
        var chemistry : ArrayList<Int> = ArrayList()
        var physics : ArrayList<Int> = ArrayList()
        if(test.Set.size > 0) {
           for (i in 0..test.Set.size-1) {
                if (test.Set[i].subject_id == 0) physics.add(i)
                else if (test.Set[i].subject_id == 1) chemistry.add(i)
                else if (test.Set[i].subject_id == 2) maths.add(i)
            }
        }

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

        val a = physics.size
        val b = chemistry.size
        val c = maths.size
        if(physics.isNotEmpty())test.subject[0] = physics.get(0)
            if(chemistry.isNotEmpty())test.subject[1] = chemistry.get(0)
            if(maths.isNotEmpty())test.subject[2] = maths.get(0)


        Ques.text = "${a+b+c} Questions"
        details.text = "$a Questions of Physics\n${c} Questions of Mathematics\n${b} Questions of Chemistry\n" + "Time- 3Hr"
    }


}