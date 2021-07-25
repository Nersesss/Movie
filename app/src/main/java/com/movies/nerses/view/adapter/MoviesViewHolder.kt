package com.movies.nerses.view.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.movies.nerses.R
import com.movies.nerses.model.Movie

class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val textName = view.findViewById<TextView>(R.id.text_name)
    private val textDate = view.findViewById<TextView>(R.id.text_date)

   fun initData(movie: Movie){
       textName.text = movie.name
       textDate.text = movie.date
   }

}
