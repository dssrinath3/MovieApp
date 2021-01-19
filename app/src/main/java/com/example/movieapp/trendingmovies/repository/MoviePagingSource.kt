package com.example.movieapp.trendingmovies.repository

import androidx.paging.rxjava3.RxPagingSource
import com.example.movieapp.trendingmovies.model.Movie
import com.example.movieapp.trendingmovies.network.MovieService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviePagingSource @Inject constructor(
    private val movieService: MovieService
) : RxPagingSource<Int, Movie>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
            val page = params.key ?:1
        return  movieService.getTrendingMovie(page)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it.results,
                    prevKey = if (page == 1) null else page -1,
                    nextKey = if (page == it.totalPage.toInt()) null else it.page.toInt()+1
                ) as LoadResult<Int,Movie>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}