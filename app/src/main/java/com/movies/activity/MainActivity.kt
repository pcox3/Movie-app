package com.movies.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.movies.Fragment.FragmentMovieDetails
import com.movies.R
import com.movies.adapters.MovieAdapter
import com.movies.databinding.ActivityMainBinding
import com.movies.models.ResponseStatus
import com.movies.models.SearchMovieResponse
import com.movies.utility.genericClassCast
import com.movies.viewModel.MovieViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewmodel by lazy { ViewModelProvider(this)[MovieViewModel::class.java] }
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            adapter = MovieAdapter(this@MainActivity){ it, position ->
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.frag_container, FragmentMovieDetails(movieId = it.imdbID?:""))
                        .addToBackStack(null)
                        .commit()
                }
            }
            binding.rvMovies.adapter = adapter

            searchBar.setOnQueryTextListener(this@MainActivity)
            swipeRefresh.isEnabled = false //Avoid SwipeRefreshLayout from refreshing on pull to refresh

        }


    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }
    override fun onQueryTextChange(newText: String?): Boolean {
        debounceRunnable?.let { debounceHandler.removeCallbacks(it) }
        debounceRunnable = Runnable {
            newText?.let {
                searchMovies(it)
            }
        }
        debounceRunnable?.let { debounceHandler.postDelayed(it, DEBOUNCE_DELAY) }

        return true
    }


    private fun searchMovies(query: String){
        binding.swipeRefresh.isRefreshing = true
        viewmodel.searchMovies(query = query).observe(this){
            binding.swipeRefresh.isRefreshing = false
            binding.tvEmpty.visibility = View.GONE
            when (it){
                is ResponseStatus.Success -> {
                    val response = genericClassCast(it.data, SearchMovieResponse::class.java)
                    adapter.setMovies(response?.search?:ArrayList())
                    if (adapter.itemCount == 0){
                        binding.tvEmpty.visibility = View.VISIBLE
                    }
                }
                is ResponseStatus.Failed -> {
                    Toast.makeText(this, it.error?.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private var debounceHandler: Handler = Handler(Looper.getMainLooper())
    private var debounceRunnable: Runnable? = null

    /**
     * Call search API every x milliseconds as user types
     * This has a flaw of too many API calls. Advisable to
     * implement the onSearch option in the search bar
     */
    private val DEBOUNCE_DELAY: Long = 500

}