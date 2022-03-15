package com.farfromcampus.jeemocktestseries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.daos.Questiondao
import com.farfromcampus.jeemocktestseries.models.Mocktest
import com.farfromcampus.jeemocktestseries.models.Questions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(GoogleSignIn.getLastSignedInAccount(this)==null){
            startActivity(Intent(this, getstartedActivity::class.java))
            finish()
        }

//        var mock = Mocktest()
//        var ids:ArrayList<String> = ArrayList(30)
//        mock.mock_id = "testingfirstmocktest"
//        for( i in 0..29){
//            ids.add("ques_idx_" + (i+1).toString())
//        }
//        mock.ques_ids = ids
//        mock.test_number = 1
//        Mocktestdao().addMockTest(mock)

//        var ques = Questions()

//        // 24
//        ques.ques_id = "ques_idx_24"
//        ques.question = ""
//        ques.answer = ""
//        ques.image = ""
//        ques.solution = ""
//        ques.subject_id = 2
//        ques.answer_img = ""
//
//        ques.option.add("")
//        ques.option.add("")
//        ques.option.add("")
//        ques.option.add("")
//        ques.option.add("")


        val mock_id = "testingfirstmocktest"
        val intent =Intent(this,ockreviewActivity::class.java)
        findViewById<Button>(R.id.button).setOnClickListener {
            intent.putExtra("mock_id",mock_id)
            startActivity(intent)
        }


    }

//    override fun onStart() {
//        super.onStart()
//        val mock_id = "testingfirstmocktest"
//        GlobalScope.launch(Dispatchers.Main) {
//            val quesid = Mocktestdao().getMockTestById(mock_id).await().toObject(Mocktest::class.java)!!
//            findViewById<TextView>(R.id.TestingGet).text = quesid.ques_ids.size.toString()
//
//        }
//    }
}