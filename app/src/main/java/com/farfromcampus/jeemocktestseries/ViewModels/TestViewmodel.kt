//package com.farfromcampus.jeemocktestseries.ViewModels
//
//import android.view.View
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.farfromcampus.jeemocktestseries.daos.Questiondao
//import com.farfromcampus.jeemocktestseries.models.Mocktest
//import com.farfromcampus.jeemocktestseries.models.Questions
//import com.farfromcampus.jeemocktestseries.models.Test
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//
//class TestViewmodel(val mock:Mocktest): ViewModel() {
//    var test = Test()
//    val ques_id = mock.ques_ids
//
//    var i = 0
//    var question = Questions()
//
//    fun Download() {
//        GlobalScope.launch {
//            for (i in 0..ques_id.size) {
//                val Ques = Questiondao().getQuestionById(ques_id[i]).await()
//                    .toObject(Questions::class.java)!!
//
//                test.Set.add(Ques)
//                test.subject[0] = 0                                             //Physics
//                if (test.subject[1] == -1 && Ques.subject_id == 1) test.subject[1] = i  //Chemistry
//                if (test.subject[2] == -1 && Ques.subject_id == 2) test.subject[2] = i  //Mathematics
//            }
//        }
//    }
//
//    private fun changeQuestion(x: Int) {
//        question = test.Set[x]
//    }
//    fun saveAndNext() {
//        i++
//        changeQuestion(i)
//    }
//    fun chemistry() {
//        i = test.subject[1]
//        changeQuestion(i)
//    }
//    fun physics() {
//        i = test.subject[0]
//        changeQuestion(i)
//    }
//    fun maths() {
//        i = test.subject[2]
//        changeQuestion(i)
//    }
//
//
//
//
//}