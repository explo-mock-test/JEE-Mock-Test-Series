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
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
//import com.farfromcampus.jeemocktestseries.ViewModels.TestViewmodel
//import com.farfromcampus.jeemocktestseries.ViewModels.TestViewmodelFactory
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.daos.Questiondao
import com.farfromcampus.jeemocktestseries.models.Mocktest
import com.farfromcampus.jeemocktestseries.models.Questions
import com.farfromcampus.jeemocktestseries.models.Test
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class ockreviewActivity : AppCompatActivity() {

    var k = 0
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

        GlobalScope.launch(Dispatchers.Main) {
            quesid = Mocktestdao().getMockTestById(mock_id).await().toObject(Mocktest::class.java)!!
            testnumber = quesid.test_number


//          QUERY BASED
            val chunks = quesid.ques_ids.chunked(10) as ArrayList
            val questionTasks: ArrayList<Task<QuerySnapshot>> = ArrayList()
            for(chunk in chunks) {
                Log.d("TAG", chunk.toString())
                val task =  Questiondao().getAllQuestionsByIds(chunk).addOnSuccessListener { document->
                    for(dat in document){
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

            execute()
        }
    }


    @SuppressLint("SetTextI18n")
    fun execute() {
        test.Set.sortedWith(compareBy({ it.ques_id }))
        for(i in 0..test.Set.size-1){
            if(test.Set[i].subject_id == 0 && test.subject[0]==-1)test.subject[0]=i
            else if(test.Set[i].subject_id == 1 && test.subject[0]==-1)test.subject[1]=i
            else if(test.Set[i].subject_id == 2 && test.subject[0]==-1)test.subject[2]=i
        }

        var mockNum =findViewById<TextView>(R.id.mocknumber)
        var Ques = findViewById<TextView>(R.id.questions)
        var details = findViewById<TextView>(R.id.details)

        findViewById<ProgressBar>(R.id.progressBar2).isVisible = false
        findViewById<TextView>(R.id.textView3).isVisible = false
        findViewById<TextView>(R.id.status).isVisible = false
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

        override fun onBackPressed(){
        AlertDialog.Builder(this)
            .setTitle("Exit Alert")
            .setMessage("Do You Want To Exit Mock test?")
            .setPositiveButton(android.R.string.ok) { dialog, whichButton ->
                super.onBackPressed()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, whichButton ->

            }.show()
    }
}