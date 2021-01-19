package com.example.movieapp.trendingmovies

import android.app.Application
import com.example.movieapp.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TrendingMovieApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG)
        {
            Timber.plant(Timber.DebugTree())
        }
    }

}