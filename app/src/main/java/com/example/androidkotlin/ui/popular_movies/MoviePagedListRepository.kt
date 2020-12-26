package com.example.androidkotlin.ui.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.androidkotlin.data.api.MovieDBInterface
import com.example.androidkotlin.data.api.POST_PER_PAGE
import com.example.androidkotlin.data.repository.MovieDataSource
import com.example.androidkotlin.data.repository.MovieDataSourceFactory
import com.example.androidkotlin.data.repository.NetworkState
import com.example.androidkotlin.data.valueObject.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService : MovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {

        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {

        return Transformations.switchMap<MovieDataSource, NetworkState> (

            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState

                )
    }
}