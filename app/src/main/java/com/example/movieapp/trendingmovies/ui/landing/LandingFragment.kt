package com.example.movieapp.trendingmovies.ui.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_landing.*
import kotlinx.android.synthetic.main.layout_error.*


@AndroidEntryPoint
class LandingFragment : Fragment(R.layout.fragment_landing) {
    private lateinit var movieAdapter: MovieAdapter

    private val landingViewModel by viewModels<LandingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter()
        rvMovie.layoutManager = LinearLayoutManager(requireContext())
        rvMovie.adapter = movieAdapter.withLoadStateFooter(
            MovieFooterStateAdapter{
                movieAdapter.retry()
            }
        )
        movieAdapter.addLoadStateListener { loadStates ->
            srl.isRefreshing = loadStates.source.refresh is LoadState.Loading
            llErrorContainer.isVisible = loadStates.source.refresh is LoadState.Error
            rvMovie.isVisible = !llErrorContainer.isVisible

            if (loadStates.source.refresh is LoadState.Error){
                btnRetry.setOnClickListener {
                    movieAdapter.retry()
                }

                llErrorContainer.isVisible = loadStates.source.refresh is LoadState.Error
                val errorMessage = (loadStates.source.refresh as LoadState.Error).error.message
                tvErrorMesssage.text = errorMessage
            }
        }

        srl.setOnRefreshListener {
            landingViewModel.onRefresh()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        landingViewModel.trendingMovies.observe(viewLifecycleOwner, Observer {
            //wit pagging librray
            movieAdapter.submitData(lifecycle, it)


            // witout pagging librrary
           /* when (it.status) {
                Status.SUCCESS -> {
                    showLoading(false)
                    movieAdapter.setMovies(it.data!!)
                }
                Status.ERROR -> {
                   showLoading(false)
                    Snackbar.make(requireView(), it.message!!,Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                  showLoading(true)
                }
            }*/

        })
    }

  /* private fun showLoading(isShow : Boolean){
        if(isShow){
            loadingContainer.visibility = View.VISIBLE
        }else{
            loadingContainer.visibility = View.GONE
        }
    }*/

}