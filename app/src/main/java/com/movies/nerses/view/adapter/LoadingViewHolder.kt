package com.movies.nerses.view.adapter

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.movies.nerses.R

class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

   private val progressBar = view.findViewById<ProgressBar>(R.id.progressbar)
    fun initData(visible: Int) {
        progressBar.visibility = visible
    }

}
