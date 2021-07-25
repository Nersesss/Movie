package com.movies.nerses.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movies.nerses.OnItemClickListener
import com.movies.nerses.R
import com.movies.nerses.model.Movie


class MoviesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<Movie> = ArrayList()
    private val LOADING = 0
    private val ITEM = 1
    private var isLoadingAdded = false
    private var onItemClickListener: OnItemClickListener<Movie>? = null

    fun setData(list: MutableList<Movie>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM -> {
                val viewItem: View = inflater.inflate(R.layout.movie_item, parent, false)
                viewHolder = MoviesViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading: View = inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie: Movie = list[position]
        when (getItemViewType(position)) {
            ITEM -> {
                val movieViewHolder: MoviesViewHolder = holder as MoviesViewHolder
                movieViewHolder.initData(movie)

                movieViewHolder.itemView.setOnClickListener {
                    onItemClickListener?.onItemClick(it, position, movie)
                }
            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.initData(VISIBLE)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Movie())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        if (list.size > 0) {
            val position: Int = list.size - 1
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun add(movie: Movie) {
        list.add(movie)
        notifyItemInserted(list.size - 1)
    }

    fun addAll(moveResults: List<Movie>) {
        for (result in moveResults) {
            add(result)
        }
    }

    fun getData(): MutableList<Movie> {
        return  list
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<Movie>){
        this.onItemClickListener = onItemClickListener
    }
}