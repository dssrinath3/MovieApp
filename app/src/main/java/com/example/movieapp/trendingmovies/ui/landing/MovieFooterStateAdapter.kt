package com.example.movieapp.trendingmovies.ui.landing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import kotlinx.android.synthetic.main.item_movie_footer_state.view.*

class MovieFooterStateAdapter(private  val retry: () -> Unit) :
    LoadStateAdapter<MovieFooterStateAdapter.MovieFooterStateViewHoder>() {


    override fun onBindViewHolder(holder: MovieFooterStateViewHoder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieFooterStateViewHoder {
       return MovieFooterStateViewHoder.create(parent,retry)
    }

    class MovieFooterStateViewHoder private constructor(
        itemView: View,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState){
            itemView.apply {
                if(loadState is LoadState.Error){
                    tvErrorMesssage.text = loadState.error.message
                }
                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                tvErrorMesssage.isVisible = loadState !is LoadState.Loading
            }
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit) : MovieFooterStateViewHoder{
               val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_footer_state,parent,false)
                return MovieFooterStateViewHoder(view,retry)
            }
        }
    }
}