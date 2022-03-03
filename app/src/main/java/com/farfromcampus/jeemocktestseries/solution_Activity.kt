package com.farfromcampus.jeemocktestseries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farfromcampus.jeemocktestseries.daos.Mocktestdao
import com.farfromcampus.jeemocktestseries.models.Mocktest
import com.farfromcampus.jeemocktestseries.models.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

abstract class solution_Activity : AppCompatActivity(){
    val test = intent.getSerializableExtra("test") as Test
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solution)
        val recyclerView = findViewById<RecyclerView>(R.id.recylerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = Sol_Adapter(test)      //Alert!!
        recyclerView.adapter = adapter

    }
}