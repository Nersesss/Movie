package com.movies.nerses

import android.view.View

interface  OnItemClickListener<T> {
    fun onItemClick(view: View, position: Int, it: T)
}