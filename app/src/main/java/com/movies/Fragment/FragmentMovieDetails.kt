package com.movies.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.movies.R
import com.movies.databinding.FragmentMovieDetailsBinding
import com.movies.models.MovieDetailsResponse
import com.movies.models.ResponseStatus
import com.movies.utility.genericClassCast
import com.movies.viewModel.MovieViewModel

class FragmentMovieDetails: Fragment() {

    private var param1: String? = null
    private val movieId by lazy { param1.toString() }

    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewmodel by lazy { ViewModelProvider(this)[MovieViewModel::class.java] }

    override fun onCreateView(inflater: LayoutInflater,
      container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewmodel
        initViews()
    }


    private fun initViews(){

        arguments?.let {
            param1 = it.getString(MOVIE_ID)
        }

        with(binding){
            swipeRefresh.isEnabled = false //Avoid SwipeRefreshLayout from refreshing on pull to refresh
            toolbar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }

        getMovieDetails()

    }


    private fun getMovieDetails(){
        binding.swipeRefresh.isRefreshing = true
        viewmodel.getMovieDetails(movieId).observe(viewLifecycleOwner){
            binding.swipeRefresh.isRefreshing = false
            when(it){
                is ResponseStatus.Success -> {
                    val response = genericClassCast(it.data, MovieDetailsResponse::class.java)
                    viewmodel.setMovieDetails(response)
                    binding.movieUrl = response?.poster
                    binding.executePendingBindings()
                }
                is ResponseStatus.Failed -> {
                    Toast.makeText(requireContext(), it.error?.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    companion object {
        private const val MOVIE_ID = "movieId"

        fun newInstance(param1: String): FragmentMovieDetails {
            val fragment = FragmentMovieDetails()
            val args = Bundle()
            args.putString(MOVIE_ID, param1)
            fragment.arguments = args
            return fragment
        }
    }

}