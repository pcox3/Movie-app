package com.movies.utility

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.movies.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("movieUrl")
    fun setImageUrl(imageView: ShapeableImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(imageView)
    }

}