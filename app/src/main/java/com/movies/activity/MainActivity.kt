package com.movies.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.movies.Fragment.FragmentMovieDetails
import com.movies.R
import com.movies.adapters.MovieAdapter
import com.movies.databinding.ActivityMainBinding
import com.movies.models.ResponseStatus
import com.movies.models.SearchMovieResponse
import com.movies.utility.debounce
import com.movies.utility.genericClassCast
import com.movies.viewModel.MovieViewModel


class MainActivity : AppCompatActivity() {

    private val viewmodel by lazy { ViewModelProvider(this)[MovieViewModel::class.java] }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            adapter = MovieAdapter(this@MainActivity){ it, _ ->
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.frag_container, FragmentMovieDetails.newInstance(it.imdbID))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit()
                }
            }
            binding.rvMovies.adapter = adapter

            searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    debounce {
                        newText?.let {
                            searchMovies(it)
                        }
                    }
                    return true
                }
            })

            // END OF BINDING CODE
        }


    }


    private fun searchMovies(query: String){
        binding.progressIndicator.show()
        viewmodel.searchMovies(query = query).observe(this){
            binding.progressIndicator.hide()
            binding.tvEmpty.visibility = View.GONE
            when (it){
                is ResponseStatus.Success -> {
                    val response = genericClassCast(it.data, SearchMovieResponse::class.java)
                    adapter.setMovies(response?.search?:mutableListOf())
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

}