package com.example.androidkotlin.data.repository

enum class Status {

    SUCCESS,
    RUNNING,
    FAILED

}


class NetworkState (val status: Status, val message: String) {

    companion object{

        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST: NetworkState

        init {

            LOADED = NetworkState(Status.SUCCESS,"Success")
            LOADING = NetworkState(Status.RUNNING, "Running")
            ERROR = NetworkState(Status.FAILED,"Error")
            ENDOFLIST = NetworkState(Status.FAILED, "This is the end of the list")
        }
    }
}