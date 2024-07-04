package com.movies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.movies.R
import com.movies.models.MovieItem
import com.movies.models.SearchMovieResponse

class MovieAdapter(
    private val context: Context,
    private val clickListener: (MovieItem, Int) -> Unit
): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies: MutableList<MovieItem>? = null

    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val titleTextView = itemView.findViewById<MaterialTextView>(R.id.tv_movie_title)
        private val yearTextView = itemView.findViewById<MaterialTextView>(R.id.tv_movie_year)
        private val posterImageView = itemView.findViewById<ShapeableImageView>(R.id.img_movie_poster)

        fun bind(movie: MovieItem,
                 context: Context,
                 position: Int,
                 clickListener: (MovieItem, Int) -> Unit){
            titleTextView.text = movie.title
            yearTextView.text = movie.year
            Glide.with(context).load(movie.poster).into(posterImageView)
            itemView.setOnClickListener { clickListener(movie, position) }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
        return MovieViewHolder(view)
    }

    fun setMovies(movies: MutableList<MovieItem>){
        this.movies?.clear()
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies!![position], context, position, clickListener)
    }



}