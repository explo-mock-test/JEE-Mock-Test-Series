package com.farfromcampus.jeemocktestseries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farfromcampus.jeemocktestseries.models.Questions

class Sol_Adapter(val test:ArrayList<Questions>):RecyclerView.Adapter<SolutionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolutionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sol_view,parent,false)
        val viewholder = SolutionViewHolder(view)
        viewholder.ques_text.setOnClickListener{
            viewholder.sol_text.isVisible = viewholder.sol_text.isVisible==false
            viewholder.sol_img.isVisible = viewholder.sol_text.isVisible==false
        }
        return viewholder
    }

    override fun getItemCount(): Int {
        return test.size
    }

    override fun onBindViewHolder(holder: SolutionViewHolder, position: Int) {
                holder.ques_num.text = "Question ${position+1}"
                holder.ques_text.text = test[position].question
                holder.answer.text = "Answer:- " +  test[position].answer.toString()
                holder.sol_text.text = test[position].solution
                if (test[position].image.isNotEmpty()) {
                    Glide.with(holder.ques_img.context).load(test[position].image).into(holder.ques_img)
                }
                if (test[position].answer_img.isNotEmpty()) {
                    Glide.with(holder.sol_img.context).load(test[position].image).into(holder.ques_img)
                }
        }
}
class SolutionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val ques_num = itemView.findViewById<TextView>(R.id.question_num)
    val ques_text = itemView.findViewById<TextView>(R.id.question_text)
    val answer = itemView.findViewById<TextView>(R.id.answer)
    val ques_img =itemView.findViewById<ImageView>(R.id.question_img)
    val sol_text = itemView.findViewById<TextView>(R.id.sol_text)
    val sol_img = itemView.findViewById<ImageView>(R.id.sol_img)
}
