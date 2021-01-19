package com.example.movieapp.trendingmovies.ui.moviedetails

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.movieapp.trendingmovies.model.Movie
import com.example.movieapp.trendingmovies.model.Resource
import com.example.movieapp.trendingmovies.repository.MovieRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MovieDetailsViewModel @ViewModelInject constructor(
    private val movieRepository: MovieRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _movie = MutableLiveData<Resource<Movie>>()
    private val compositDisposable = CompositeDisposable()

    val movie: LiveData<Resource<Movie>>
    get() = _movie

    init {
        if (savedStateHandle.contains("movieId")) {
            val movieId = savedStateHandle.get<Long>("movieId")

            compositDisposable.addAll(
                movieRepository.getMovieDetails(movieId!!)
                    .doOnSubscribe {
                        _movie.value = Resource.Loading()
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { movie -> _movie.value = Resource.Success(movie) },
                        { error -> _movie.value = Resource.Error(error.message!!) }
                    )
                //  Timber.i("MovieId: $movieId")
            )

        }else{
            _movie.value = Resource.Error("OMG Unable to get the Arguments")
        }
    }

    override fun onCleared() {
        compositDisposable.clear()
        super.onCleared()
    }
}