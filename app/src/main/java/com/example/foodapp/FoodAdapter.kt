package com.example.foodapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationfood.R
import com.squareup.picasso.Picasso

class FoodAdapter(private val data: ArrayList<Food>, private val foodEvent: FoodEvent) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        val imgMain = itemView.findViewById<ImageView>(R.id.imageView)
        val txtSubject = itemView.findViewById<TextView>(R.id.textView_nameoffood)
        val txtCity = itemView.findViewById<TextView>(R.id.textView_city)
        val txtPrice = itemView.findViewById<TextView>(R.id.textView_price)
        val txtDistance = itemView.findViewById<TextView>(R.id.textView_distance)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar2)
        val txtRating = itemView.findViewById<TextView>(R.id.textView_count_of_rating)


        fun bindData(position: Int) {

            txtSubject.text = data[position].txtSubject
            txtCity.text = data[position].txtCity
            txtPrice.text = "$" + data[position].txtPrice + " vip"
            txtDistance.text = data[position].txtDistance + " miles from you"
            ratingBar.rating = data[position].rating
            txtRating.text = "(" + data[position].numOfRating.toString() + " Ratings)"


            Picasso
                .get()
                .load(data[position].urlImage)
                .into(imgMain)
            itemView.setOnClickListener{
                foodEvent.onfoodclicked(data[position],adapterPosition)
            }
            itemView.setOnLongClickListener{
                foodEvent.onfoodlongcliked(data[adapterPosition],adapterPosition)
                true
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindData(position)

    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun addfood(newfood:Food){
        data.add(0,newfood)
        notifyItemInserted(0)
    }
    fun removefood(oldfood:Food,oldposition:Int){
        data.remove((oldfood))
        notifyItemRemoved(oldposition)
    }
    fun updatefood(newfood: Food, position: Int){
        data.set(position,newfood)
        notifyItemChanged(position)

    }
    fun setdata(newlist:ArrayList<Food>){
        data.clear()
        data.addAll(newlist)
        notifyDataSetChanged()
    }
    interface FoodEvent{
         fun onfoodclicked(food:Food,position: Int)
        fun onfoodlongcliked(food:Food,pos:Int )
    }


}