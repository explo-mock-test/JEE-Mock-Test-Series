package com.farfromcampus.jeemocktestseries

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.daos.Questiondao
import com.farfromcampus.jeemocktestseries.databinding.FragmentMockReviewBinding
import com.farfromcampus.jeemocktestseries.models.Mocktest
import com.farfromcampus.jeemocktestseries.models.Questions
import com.farfromcampus.jeemocktestseries.models.Test
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class MockReviewFragment : Fragment() {
    private lateinit var binding: FragmentMockReviewBinding
    var isload =false
//    lateinit var testViewmodel : TestViewmodel
    var testnumber = 0
    var test = Test()
    var quesid = Mocktest()
    lateinit var args: MockReviewFragmentArgs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mock_review, container, false)
        args = MockReviewFragmentArgs.fromBundle(requireArguments())
        binding.startx.setOnClickListener { view: View ->
            view.findNavController().navigate(MockReviewFragmentDirections.actionMockreviewFragmentToMockTestFragment(test))
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val mockId = args.mockId
        test.mock_id = mockId
        GlobalScope.launch(Dispatchers.IO) {
            quesid = Mocktestdao().getMockTestById(mockId).await().toObject(Mocktest::class.java)!!
            testnumber = quesid.test_number
            getMockTest(quesid)

            GlobalScope.launch(Dispatchers.Main){
                    delay(2000)
                    execute()
            }
        }
//        execute()
    }

    private suspend fun getMockTest(quesId:Mocktest){
        val chunks = quesId.ques_ids.chunked(10) as ArrayList
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
        val isload=true
        test.Set.sortedWith(compareBy{ it.ques_id })
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

        var mockNum = binding.mocknumber
        var Ques = binding.questions
        var details = binding.details
        binding.progressBar2.visibility = View.GONE
        binding.textView3.visibility = View.GONE

        mockNum.visibility = View.VISIBLE
        Ques.visibility = View.VISIBLE
        details.visibility = View.VISIBLE
        binding.instructions.visibility = View.VISIBLE
        binding.startx.visibility= View.VISIBLE
        mockNum.text = "Mock Test $testnumber"

        val a = physics.size
        val b = chemistry.size
        val c = maths.size

        if(physics.isNotEmpty())test.subject[0] = physics.get(0)
        if(chemistry.isNotEmpty())test.subject[1] = chemistry.get(0)
        if(maths.isNotEmpty())test.subject[2] = maths.get(0)


        Ques.text = "${a+b+c} Questions"
        details.text = "$a Questions of Physics\n${c} Questions of Mathematics\n${b} Questions of Chemistry\n" + "Time- 3Hr"

            binding.instructions.text = " Instructions\n" +
                    "The quizzes consists of questions carefully designed to help you self-assess your comprehension of the information presented on the topics covered in the module. No data will be collected on the website regarding your responses or how many times you take the quiz.\n" +
                    "\n" +
                    "Each question in the quiz is of multiple-choice or \"true or false\" format. Read each question carefully, and click on the button next to your response that is based on the information covered on the topic in the module. Each correct or incorrect response will result in appropriate feedback immediately at the bottom of the screen.\n" +
                    "\n" +
                    "After responding to a question, click on the \"Next Question\" button at the bottom to go to the next questino. After responding to all Question, click on \"Submit\" on the top of the window to exit the quiz.\n" +
                    "\n" +
                    "There is Negative marking for Incorrect Option\n" +
                    "\n" +
                    "The total score for the quiz is based on your responses to all questions.your test score will reflect it appropriately."
    }
}