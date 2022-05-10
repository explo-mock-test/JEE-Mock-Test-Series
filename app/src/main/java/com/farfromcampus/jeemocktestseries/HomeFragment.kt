package com.farfromcampus.jeemocktestseries

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.databinding.FragmentHomeBinding
import com.farfromcampus.jeemocktestseries.models.Mocktest
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class HomeFragment : Fragment() {
    var mocks:ArrayList<Mocktest> = ArrayList()
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch(Dispatchers.Main) {
            mocks.clear()
//            Log.d("TAGG", Mocktestdao().getAllMockTest().await().toString())
            Mocktestdao().getAllMockTest().addOnSuccessListener { document ->
                for (dat in document) {
                    Log.d("TAGG2",dat.toObject(Mocktest::class.java).toString())
                    mocks.add(dat.toObject(Mocktest::class.java))
                    Log.d("TAGG45",mocks.size.toString())
                }
            }.await()

            execute()
        }
    }

    fun execute() {
        val recyclerview = binding.testsview
        recyclerview.layoutManager = LinearLayoutManager(activity)
        val adapter1 = Tests_Adapter(mocks)
        recyclerview.adapter = adapter1
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater?.inflate(R.menu.overflow_menu, menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return NavigationUI.onNavDestinationSelected(item!!,
//            requireView().findNavController())
//                || super.onOptionsItemSelected(item)
//    }
}