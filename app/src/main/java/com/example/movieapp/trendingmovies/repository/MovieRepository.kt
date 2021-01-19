package com.example.movieapp.trendingmovies.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.example.movieapp.trendingmovies.model.Movie
import com.example.movieapp.trendingmovies.network.MovieService
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    private val moviePagingSource: MoviePagingSource
){
    fun getTrendingMovie() : Flowable<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 1
            ),
            pagingSourceFactory = { moviePagingSource }
        ).flowable

        //without pagination
        /*return movieService.getTrendingMovie()
            .subscribeOn(Schedulers.io())*/
    }

    fun getMovieDetails(movieId :Long) :Single<Movie>{
        return movieService.getMovie(movieId)
            .subscribeOn(Schedulers.io())
    }
}