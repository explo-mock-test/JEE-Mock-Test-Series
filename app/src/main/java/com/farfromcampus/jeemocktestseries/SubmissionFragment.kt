package com.farfromcampus.jeemocktestseries

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.farfromcampus.jeemocktestseries.databinding.FragmentSubmissionBinding
import com.farfromcampus.jeemocktestseries.models.Mocktest

class SubmissionFragment : Fragment() {
    var mocks:ArrayList<Mocktest> = ArrayList()
    private lateinit var binding: FragmentSubmissionBinding
    private lateinit var args: SubmissionFragmentArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Message")
                    .setMessage("Do You Want To Exit Mocktest Result?")
                    .setPositiveButton(android.R.string.ok) { dialog, whichButton ->
                        requireView().findNavController().navigateUp()
                    }
                    .setNegativeButton(android.R.string.cancel) { dialog, whichButton ->

                    }.show()
                }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_submission, container, false)
        args = SubmissionFragmentArgs.fromBundle(requireArguments())

        val test = args.test
        var totalmarks = args.totalMarks
//        for (i in 0 until test.Set.size) {
//            if (test.Set[i].answer == test.AnswerSheet[i]) {
//                totalmarks = totalmarks + 4
//            }
//        }
        binding.marks.text = totalmarks.toString()

//        val asptAnswer = AsptAnswerdao()
//        val listss = test.AnswerSheet.toList()
//        asptAnswer.addAsptAnswer(test.mock_id, listss as ArrayList<String>)

        binding.viewsolution.setOnClickListener {
            it.findNavController().navigate(SubmissionFragmentDirections.actionSubmissionFragmentToSolutionFragment(test))
        }
        binding.homepage.setOnClickListener {
            it.findNavController().navigateUp()
        }

        return binding.root
    }
}