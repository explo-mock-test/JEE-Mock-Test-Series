package com.farfromcampus.jeemocktestseries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.farfromcampus.jeemocktestseries.models.Test


class solution_Activity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solution)

        val test = intent.getSerializableExtra("test") as Test

        val recyclerView = findViewById<RecyclerView>(R.id.recylerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter1 = Sol_Adapter(test.Set)
        recyclerView.adapter = adapter1

    }
}