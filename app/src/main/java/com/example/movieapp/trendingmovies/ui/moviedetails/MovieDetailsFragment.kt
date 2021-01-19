package com.example.movieapp.trendingmovies.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.movieapp.R
import com.example.movieapp.trendingmovies.glide.GlideApp
import com.example.movieapp.trendingmovies.model.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.layout_loading.*

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private  val viewModel: MovieDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    showLoading(false)
                    val movie = it.data
                    GlideApp.with(ivBackDrop)
                        .load("https://image.tmdb.org/t/p/original${movie?.backdropPath}")
                        .placeholder(R.drawable.ic_placeholder_img)
                        .error(R.drawable.ic_error_img)
                        .into(ivBackDrop)
                    GlideApp.with(ivPoster)
                        .load("https://image.tmdb.org/t/p/w500${movie?.posterPath}")
                        .placeholder(R.drawable.ic_placeholder_img)
                        .error(R.drawable.ic_error_img)
                        .into(ivPoster)

                    tvTitle.text = movie?.title

                    if (movie?.genres !=null && movie.genres .isNotEmpty()){
                        val geners = movie.genres.joinToString(
                            separator = " | ",transform = { genre -> genre.name!! })
                        tvGeners.text = geners
                    }else{
                        tvGeners.visibility = View.GONE
                    }

                    if (movie?.runtime != null)
                    {
                        tvRuntime.text = getString(R.string.format_runtime, movie?.runtime)
                    }else{
                        tvRuntime.visibility = View.GONE
                    }

                    if (movie?.releaseDate != null && movie?.releaseDate.isNotBlank())
                    {
                        tvReleaseDate.text = movie.releaseDate
                    }else{
                        tvReleaseDate.visibility = View.GONE
                    }

                    movie?.voteAverage.let { rating ->
                        ivRating.rating = (rating?.div(2))?.toFloat()!!
                    }
                    tvVoteCount.text = movie?.voteCount.toString()
                    tvOverview.text = movie?.overview
                }
                Status.ERROR -> {
                    showLoading(false)
                    Snackbar.make(requireView(), it.message!!,Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> showLoading(true)
            }
        })
    }

    private fun showLoading(isShow :Boolean){
        loadingContainer.visibility = if (isShow) View.VISIBLE else View.GONE
    }

}