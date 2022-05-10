package com.farfromcampus.jeemocktestseries

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.farfromcampus.jeemocktestseries.databinding.FragmentMockTestBinding
import com.farfromcampus.jeemocktestseries.models.Test
import com.qdot.mathrendererlib.MathRenderView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class MockTestFragment : Fragment() {
    private lateinit var binding: FragmentMockTestBinding

    var test = Test()
    var TotalMarks = 0
    var chkoption:Array<Int> = Array(100){-1}
    val mp = mapOf(0 to "a" ,1 to "b",2 to "c" ,3 to "d" ,4 to "e")
    private var i: Int = 0
    //Options
    private lateinit var radiogrp : RadioGroup
    private lateinit var OptionA: RadioButton
    private lateinit var OptionB: RadioButton
    private lateinit var OptionC: RadioButton
    private lateinit var OptionD: RadioButton
    private lateinit var OptionE: RadioButton
    private lateinit var OptionAView: MathRenderView
    private lateinit var OptionBView: MathRenderView
    private lateinit var OptionCView: MathRenderView
    private lateinit var OptionDView: MathRenderView
    private lateinit var OptionEView: MathRenderView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Exit Alert")
                    .setMessage("Do You Want To Exit Mock test?")
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        requireView().findNavController().navigateUp()

                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ ->

                    }.show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_mock_test, container, false)
        init()
        test = MockTestFragmentArgs.fromBundle(requireArguments()).test

        val countTime = binding.countTime

        binding.skip.setOnClickListener { skip(it) }
        binding.saveandnext.setOnClickListener { saveAndNext(it)}
        binding.chemistry.setOnClickListener { chemistry(it) }
        binding.physics.setOnClickListener { physics(it) }
        binding.maths.setOnClickListener { maths(it) }
        binding.submit.setOnClickListener { submit(it) }

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

                countTime.text = "$elapsedHours hs $elapsedMinutes min $elapsedSeconds sec"
            }

            override fun onFinish() {
                submition()
            }
        }.start()
        changeQuestion(0)

        return binding.root
    }

    fun init(){
        radiogrp = binding.options

        OptionA = binding.a
        OptionB = binding.b
        OptionC = binding.c
        OptionD = binding.d
        OptionE = binding.e

        OptionAView = binding.optiona
        OptionBView = binding.optionb
        OptionCView = binding.optionc
        OptionDView = binding.optiond
        OptionEView = binding.optione
    }


    private fun changeQuestion(x: Int) {

        i = x
        if(x<0){
            Toast.makeText(context, "Index reach Negative value $x", Toast.LENGTH_SHORT).show()
            i = 0
        }else if(x > test.Set.size){
            Toast.makeText(context, "Index reach out of Scope $x", Toast.LENGTH_SHORT).show()
            i = test.Set.size
        }
        radiogrp.clearCheck()
        if(chkoption[i]!=-1){
            radiogrp.check(chkoption[x])
        }
        var mathview = binding.question
        binding.questionNumber.text ="Question  ${i+1}"
        mathview.text = test.Set[i].question
        if (test.Set[i].image.isNotEmpty()) {
            binding.QuestionImage.isVisible = true
            Glide.with(requireActivity()).load(test.Set[i].image).into(binding.QuestionImage)
        }else{
            binding.QuestionImage.isVisible = false
        }

        OptionAView.text =  test.Set[i].option[0]
        OptionBView.text =  test.Set[i].option[1]
        OptionCView.text =  test.Set[i].option[2]
        OptionDView.text =  test.Set[i].option[3]
        //Added for height correcption
//            OptionA.height = OptionAView.height
//            OptionB.height = OptionBView.height
//            OptionC.height = OptionCView.height
//            OptionD.height = OptionDView.height

        if (test.Set[i].option.size == 5 && test.Set[i].option[4].isNotEmpty()) {
            OptionEView.isVisible = true
            OptionEView.text = test.Set[i].option[4]
        }else{
            OptionEView.isVisible = false
        }
    }

    fun saveAndNext(view: View) {
        val x = radiogrp.checkedRadioButtonId
        if (x != -1) {
            val checkedOption = requireView().findViewById<RadioButton>(x).resources.getResourceEntryName(x)
            chkoption[i]=x
            test.marks[5]++
            test.AnswerSheet.set(i,checkedOption)
        }
        if(test.Set[i].answer == test.AnswerSheet[i]){
            TotalMarks+=4
            test.marks[0]+=4
            if(test.Set[i].subject_id==0){
                test.marks[1]=test.marks[1]+1
            }else if(test.Set[i].subject_id==1){
                test.marks[2]=test.marks[2]+1
            }else if(test.Set[i].subject_id==2){
                test.marks[3]=test.marks[3]+1
            }
        }else{
            if(test.AnswerSheet[i]!=""){
                TotalMarks--
                test.marks[0]--
                test.marks[4]++
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
        Log.d("submition", "Need to add code to submit test to backend")
        requireView().findNavController().navigate(MockTestFragmentDirections.actionMockTestFragmentToSubmissionFragment(TotalMarks, test))
    }
    fun submit(view: View) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Submission")
        builder.setMessage("Are you sure to Submit Test?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Submit") { dialogInterface, which ->
            submition()
        }
        builder.setNegativeButton("No") { dialogInterface, which ->
            Toast.makeText(context, "clicked No", Toast.LENGTH_LONG).show()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

//    override fun onBackPressed(){
//        androidx.appcompat.app.AlertDialog.Builder(this)
//            .setTitle("Exit Alert")
//            .setMessage("Do You Want To Exit Mock test?")
//            .setPositiveButton(android.R.string.ok) { dialog, whichButton ->
//                startActivity(Intent(this,MainActivity::class.java))
//                super.onBackPressed()
//            }
//            .setNegativeButton(android.R.string.cancel) { dialog, whichButton ->
//
//            }.show()
//    }
}