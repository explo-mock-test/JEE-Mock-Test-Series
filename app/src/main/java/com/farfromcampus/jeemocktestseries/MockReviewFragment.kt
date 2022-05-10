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
//import com.farfromcampus.jeemocktestseries.ViewModels.TestViewmodel
//import com.farfromcampus.jeemocktestseries.ViewModels.TestViewmodelFactory
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
        if(physics.isNotEmpty()) {
            test.subject[0] = physics[0]
            test.subject[1] = chemistry[0]
            test.subject[2] = maths[0]
        }

        Ques.text = "${a+b+c} Questions"
        details.text = "$a Questions of Physics\n${c} Questions of Mathematics\n${b} Questions of Chemistry\n" + "Time- 3Hr"
    }
}