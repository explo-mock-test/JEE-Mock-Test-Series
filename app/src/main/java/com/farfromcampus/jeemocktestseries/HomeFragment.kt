package com.farfromcampus.jeemocktestseries

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.databinding.FragmentHomeBinding
import com.farfromcampus.jeemocktestseries.models.Mocktest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    var mocks:ArrayList<Mocktest> = ArrayList()
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)

        runBlocking{
            Log.d("TAGG", Mocktestdao().getAllMockTest().await().toString())
            Mocktestdao().getAllMockTest().addOnSuccessListener { document ->
                for (dat in document) {
                    Log.d("TAGG2",dat.toObject(Mocktest::class.java).toString())
                    mocks.add(dat.toObject(Mocktest::class.java))
                    Log.d("TAGG45",mocks.size.toString())
                }
            }.await()
        }
        Log.d("TAGG",mocks.size.toString())

        val recyclerview = binding.testsview
        recyclerview.layoutManager = LinearLayoutManager(activity)
        val adapter1 = Tests_Adapter(mocks)
        recyclerview.adapter = adapter1
        return binding.root
    }

//    override fun onStart() {
//        super.onStart()
//
//    }

    override fun onResume() {
        super.onResume()

        val recyclerview = binding.testsview
        recyclerview.layoutManager = LinearLayoutManager(activity)
        val adapter1 = Tests_Adapter(mocks)
        recyclerview.adapter = adapter1

    }
}