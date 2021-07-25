package com.movies.nerses.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.movies.nerses.R
import com.movies.nerses.viewmodel.MoviesViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class DetailsFragment : Fragment() {
    private val viewModel by sharedViewModel<MoviesViewModel>()

    private lateinit var textName: TextView
    private lateinit var textDetails: TextView
    private lateinit var textDate: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        viewModel.movie.observe(viewLifecycleOwner, {
           textName.text = it.name
           textDate.text = it.date
           textDetails.text = it.description
        })

        viewModel.getDetails()
    }

    private fun initViews(view: View) {
        textName = view.findViewById(R.id.text_name)
        textDate = view.findViewById(R.id.text_date)
        textDetails = view.findViewById(R.id.text_details)
    }

}