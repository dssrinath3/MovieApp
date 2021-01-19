package com.example.movieapp.trendingmovies.ui.landing

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.example.movieapp.trendingmovies.model.Movie
import com.example.movieapp.trendingmovies.repository.MovieRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class LandingViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

    private val compositDisposable = CompositeDisposable()
    //with paging librray
    private val _trendingMovies = MutableLiveData<PagingData<Movie>>()
    val trendingMovies : LiveData<PagingData<Movie>>
        get() = _trendingMovies

    init {
        onGetTrendingMovie()
    }

    //without Paging Library
   /* private val _trendingMovies = MutableLiveData<Resource<List<Movie>>>()
    val trendingMovies : LiveData<Resource<List<Movie>>>
    get() = _trendingMovies
    init {
        compositDisposable.add(
            movieRepository.getTrendingMovie()
                .doOnSubscribe {
                    _trendingMovies.value = Resource.Loading(null)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                        movies -> _trendingMovies.value = Resource.Success(movies.results)
                },{ t ->
                    Timber.e(t)
                    _trendingMovies.value = Resource.Error(t.message!!,null)
                })
        )


    }*/

    override fun onCleared() {
        //to avoid memory leak
        compositDisposable.clear()
        super.onCleared()
    }

    fun onRefresh() {
        onGetTrendingMovie()
    }

    private fun onGetTrendingMovie(){
        compositDisposable.add(
            movieRepository.getTrendingMovie()
                .cachedIn(viewModelScope)
                .subscribe {
                    _trendingMovies.value = it
                }

        )
    }
}