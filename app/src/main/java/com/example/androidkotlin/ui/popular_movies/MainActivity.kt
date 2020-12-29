package com.example.androidkotlin.ui.popular_movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidkotlin.R
import com.example.androidkotlin.data.api.MovieDBClient
import com.example.androidkotlin.data.api.MovieDBInterface
import com.example.androidkotlin.data.local.model.User
import com.example.androidkotlin.data.repository.NetworkState
import com.example.androidkotlin.ui.single_movie_details.SingleMovie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_single_movie.view.*
//import java.util.*


@Suppress("UNCHECKED_CAST")

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainActivityViewModel
    lateinit var movieRepository: MoviePagedListRepository


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


      /*  button.setOnClickListener {       // Test SingleMovieDetails

            val intent = Intent(this, SingleMovie::class.java)
            intent.putExtra("id", 299534)
            this.startActivity(intent)

        }*/

        val apiService : MovieDBInterface = MovieDBClient.getClient()

        movieRepository = MoviePagedListRepository(apiService)

        viewModel = getViewModel()

        val movieAdapter = PopularMoviePagedListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

            override fun getSpanSize(position: Int): Int {

                val viewType = movieAdapter.getItemViewType(position)
                if(viewType == movieAdapter.MOVIE_VIEW_TYPE)
                    return 1
                else
                    return 3
            }
        };

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {

            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {

            main_progress_bar.visibility = if(viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE
            else View.GONE

            txt_error_popular.visibility = if(viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE
            else View.GONE

            if(!viewModel.listIsEmpty()) {

                movieAdapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel(): MainActivityViewModel {

        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {

            override fun <T : ViewModel?> create(modelClass: Class<T>): T {

                return MainActivityViewModel(movieRepository) as T
            }
        }) [MainActivityViewModel::class.java]
    }
}
