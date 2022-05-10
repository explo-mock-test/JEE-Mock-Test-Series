package com.farfromcampus.jeemocktestseries

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.farfromcampus.jeemocktestseries.models.Mocktest

class Tests_Adapter(val mocktests:ArrayList<Mocktest>):RecyclerView.Adapter<TestsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tests_view,parent,false)
        val viewholder = TestsViewHolder(view)

        return viewholder
    }
    override fun getItemCount(): Int {
        return mocktests.size
    }
    override fun onBindViewHolder(holder: TestsViewHolder, position: Int) {
        holder.TestName.text = mocktests[position].name
        holder.TestNumber.text = "Test Number: " + mocktests[position].test_number.toString()
        if (mocktests[position].testtype.isEmpty()) {
            holder.TestType.text = "Type/Length: " + mocktests[position].testtype + " & " + mocktests[position].ques_ids.size + " Questions"
        } else {
            holder.TestType.text = "Type/Length: " + "All Subject" + " & " + mocktests[position].ques_ids.size + " Questions"
        }
        holder.TestTime.text = "Time :- ${mocktests[position].time.toString()} Hr"

        holder.start.setOnClickListener { v->
            v.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMockreviewFragment(mocktests[position].mock_id))
        }
    }
}
class TestsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val TestName = itemView.findViewById<TextView>(R.id.TestName)
    val TestNumber = itemView.findViewById<TextView>(R.id.TestNumber)
    val TestType = itemView.findViewById<TextView>(R.id.TestType)
    val TestTime = itemView.findViewById<TextView>(R.id.Time)
    val start = itemView.findViewById<Button>(R.id.StartTest)
}