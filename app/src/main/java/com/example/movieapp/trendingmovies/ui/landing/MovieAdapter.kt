package com.example.movieapp.trendingmovies.ui.landing

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.trendingmovies.glide.GlideApp
import com.example.movieapp.trendingmovies.model.Movie
import com.google.android.material.textview.MaterialTextView
import timber.log.Timber

class MovieAdapter: PagingDataAdapter<Movie,MovieAdapter.MovieViewHolder>(COMPARATOR){

    //private var movies : List<Movie>  = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

   /* override fun getItemCount(): Int {
     return movies.size
    }*/
   /* fun setMovies(movies: List<Movie>){
        this.movies = movies
        notifyDataSetChanged()

    }
*/

    class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_movie, parent, false)) {
        private var mTitle: MaterialTextView? = null
        private var mReleaseDate: MaterialTextView? = null
        private var mOverview: MaterialTextView? = null
        private var mIvPoster: ImageView? = null


        init {
            mTitle = itemView.findViewById(R.id.tvTitle)
            mReleaseDate = itemView.findViewById(R.id.tvReleaseDate)
            mOverview = itemView.findViewById(R.id.tvOverview)
            mIvPoster = itemView.findViewById(R.id.ivPoster)
        }

        fun bind(movie: Movie) {

            itemView.setOnClickListener {
                val directions = LandingFragmentDirections.actionLandingFragmentToMovieDetailsFragment(movie.id!!)
                it.findNavController().navigate(directions)
            }

            itemView.apply {
                mIvPoster?.let {
                    var img:String = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
                    Timber.i("image_path : ${img}")
                    GlideApp.with(it).load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                        .placeholder(R.drawable.ic_placeholder_img)
                        .error(R.drawable.ic_error_img)
                        .into(mIvPoster!!)
                }
                mTitle?.text = movie.title
                mReleaseDate?.text = movie.releaseDate
                mOverview?.text = movie.overview
            }

        }

    }

    companion object {
        private val COMPARATOR = object:DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }
}