package com.farfromcampus.jeemocktestseries

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farfromcampus.jeemocktestseries.models.Test

class Sol_Adapter(private val test:Test):RecyclerView.Adapter<SolutionViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolutionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sol_view,parent,false)
        val viewholder = SolutionViewHolder(view)
        viewholder.ques_text.setOnClickListener{
            viewholder.sol_text.isVisible = viewholder.sol_text.isVisible==false
            viewholder.sol_img.isVisible = viewholder.sol_text.isVisible==false
        }
        return viewholder
    }


    override fun onBindViewHolder(holder: SolutionViewHolder, position: Int) {

                holder.ques_num.text = "Question ${position}"
                holder.ques_text.text = test.Set[position].question
                holder.answer.text = test.Set[position].answer.toString()
                holder.sol_text.text = test.Set[position].solution
                if (test.Set[position].image.isNotEmpty()) {
                    Glide.with(holder.ques_img.context).load(test.Set[position].image).into(holder.ques_img)
                }
                if (test.Set[position].answer_img.isNotEmpty()) {
                    Glide.with(holder.sol_img.context).load(test.Set[position].image).into(holder.ques_img)
                }
        }


    override fun getItemCount(): Int {
        return test.Set.size
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
