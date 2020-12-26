package com.example.androidkotlin.ui.popular_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.androidkotlin.data.repository.NetworkState
import com.example.androidkotlin.data.valueObject.Movie
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepository : MoviePagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movie>> by lazy {

        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {

        movieRepository.getNetworkState()
    }

    fun listEmpty(): Boolean {

        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {

        super.onCleared()
        compositeDisposable.dispose()
    }
}