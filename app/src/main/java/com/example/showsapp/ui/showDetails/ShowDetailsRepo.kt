package com.example.showsapp.ui.showDetails

import androidx.lifecycle.LiveData
import com.example.showsapp.data.api.ShowInterface
import com.example.showsapp.data.dataObj.Show
import com.example.showsapp.repository.NetworkState
import com.example.showsapp.repository.ShowDetailsNetworkSource
import io.reactivex.disposables.CompositeDisposable

class ShowDetailsRepo(private val apiService: ShowInterface) {

    lateinit var showDetailsNetworkSource: ShowDetailsNetworkSource


    fun fetchShow(compositeDisposable: CompositeDisposable, showId: Int): LiveData<Show> {
        showDetailsNetworkSource = ShowDetailsNetworkSource(apiService, compositeDisposable)
        showDetailsNetworkSource.fetchShow(showId)
        return showDetailsNetworkSource.showResponse
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return showDetailsNetworkSource.networkState
    }

}