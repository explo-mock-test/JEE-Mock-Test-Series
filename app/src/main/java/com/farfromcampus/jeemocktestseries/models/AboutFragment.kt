package com.farfromcampus.jeemocktestseries.models

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.farfromcampus.jeemocktestseries.R
import com.farfromcampus.jeemocktestseries.databinding.FragmentAboutBinding
import com.farfromcampus.jeemocktestseries.databinding.FragmentHomeBinding

class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_about, container, false)
        return binding.root
    }
}