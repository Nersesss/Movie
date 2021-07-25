package com.movies.nerses.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movies.nerses.R
import com.movies.nerses.model.Movie
import com.movies.nerses.view.PaginationScrollListener
import com.movies.nerses.view.adapter.MoviesAdapter
import com.movies.nerses.viewmodel.MoviesViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import com.movies.nerses.OnItemClickListener as OnItemClickListener1

class MoviesFragment : Fragment() {

    private val viewModel by sharedViewModel<MoviesViewModel>()

    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null

    private lateinit var adapter: MoviesAdapter

    private var isLoading = false
    private var isLastPage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        viewModel.movies.removeObservers(viewLifecycleOwner)
        viewModel.movies.observe(viewLifecycleOwner, {
            setMovies(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (!it) progressBar?.visibility = GONE
        })

        viewModel.currentPage.removeObservers(viewLifecycleOwner)
        viewModel.currentPage.observe(viewLifecycleOwner, {
            viewModel.getMovies(it)
        })
    }

    private fun initViews(view: View) {
        progressBar = view.findViewById(R.id.progressbar)

        recyclerView = view.findViewById(R.id.recyclerview)
        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = MoviesAdapter()
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.adapter = adapter

        adapter.setOnItemClickListener(object : OnItemClickListener1<Movie> {
            override fun onItemClick(view: View, position: Int, it: Movie) {
                viewModel.movie.value = it

                requireActivity().supportFragmentManager.commit {
                    replace<DetailsFragment>(R.id.fragment_container).addToBackStack(null)
                }
            }
        })


        recyclerView?.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {

            override fun loadMoreItems() {
                isLoading = true
                viewModel.currentPage.value = viewModel.currentPage.value?.plus(1)
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
    }

    private fun setMovies(list: MutableList<Movie>) {
        if (viewModel.currentPage.value == 1) {
            adapter.setData(list)
            adapter.addLoadingFooter()
            adapter.notifyDataSetChanged()
            if (viewModel.currentPage.value == viewModel.totalCount) {
                adapter.removeLoadingFooter()
                isLastPage = true
            }
        } else {
            if (list.isNotEmpty()) {
                adapter.removeLoadingFooter()
                isLoading = false
                adapter.addAll(list)
                adapter.addLoadingFooter()
            } else {
                adapter.removeLoadingFooter()
                isLoading = false
                isLastPage = true
            }
        }
    }
}

