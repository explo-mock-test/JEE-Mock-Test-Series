package com.farfromcampus.jeemocktestseries

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.daos.Questiondao
import com.farfromcampus.jeemocktestseries.models.Mocktest
import com.farfromcampus.jeemocktestseries.models.Questions
import com.farfromcampus.jeemocktestseries.models.Test
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.io.Serializable
import kotlin.math.abs

class ockreviewActivity : AppCompatActivity() {

    var testnumber:Int ?= 0
    var test = Test()
    var job = Job()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ockreview)

        val mock_id = intent.getStringExtra("mock_id")!!
        test.mock_id = mock_id

        val intent = Intent(this,mocktestActivity::class.java)
        for(i in 0..2){
            test.subject[i]=-1
        }
        fun execute() {
            findViewById<TextView>(R.id.mocknumber).text = "Mock Test $testnumber"
            findViewById<TextView>(R.id.questions).text = "${test.AnswerSheet.size} Questions"
            findViewById<TextView>(R.id.details).text =
                "${abs(test.subject[2] - test.subject[1])} Questions of Physics\n" +
                        "${abs(test.AnswerSheet.size - test.subject[2])} Questions of Mathematics\n" +
                        "${abs(test.subject[1] - test.subject[0])} Questions of Chemistry\n" + "Time- 3Hr"

            intent.putExtra("test", test as Serializable)

            findViewById<Button>(R.id.Start).setOnClickListener { view: View ->
                startActivity(intent)
            }
        }

        job = GlobalScope.launch(Dispatchers.IO) {
            val mock = Mocktestdao()
            val mtest = mock.getMockTestById(mock_id).await().toObject(Mocktest::class.java)!!  //Alert
            val Question_id = mtest.ques_ids
            val question_dao = Questiondao()
            testnumber = mtest.test_number

            for( x in 0 until Question_id.size){
                val Ques = question_dao.getQuestionById(Question_id[x]).await()
                    .toObject(Questions::class.java)!!
                test.Set[x] = Ques
                test.AnswerSheet[x]= Ques.answer
                if(Ques.subject_id==0 && test.subject[0]!=-1){
                    test.subject[0]=x
                }else if(Ques.subject_id==1 && test.subject[0]!=-1){
                    test.subject[1]=x
                }else if(Ques.subject_id==2 && test.subject[0]!=-1){
                    test.subject[2]=x
                }
            }
            execute()
        } as CompletableJob
    }
    override fun onBackPressed(){
        AlertDialog.Builder(this)
            .setTitle("Exit Alert")
            .setMessage("Do You Want To Exit Mock test?")
            .setPositiveButton(android.R.string.ok) { dialog, whichButton ->
                job.cancel()
                super.onBackPressed()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, whichButton ->

            }.show()
    }
}