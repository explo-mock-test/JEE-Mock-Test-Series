package com.farfromcampus.jeemocktestseries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.farfromcampus.jeemocktestseries.databinding.FragmentSolutionBinding

class SolutionFragment : Fragment() {
    private lateinit var binding: FragmentSolutionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_solution, container, false)

        val test = SolutionFragmentArgs.fromBundle(requireArguments()).test
        val recyclerView = binding.recylerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter1 = Sol_Adapter(test.Set)
        recyclerView.adapter = adapter1
        return binding.root
    }
}