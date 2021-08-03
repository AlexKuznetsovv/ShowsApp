package com.example.showsapp.ui.showDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.showsapp.data.dataObj.Show
import com.example.showsapp.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class ShowDetailsViewModel(private val showDetailsRepo: ShowDetailsRepo, showId:Int): ViewModel (){

    private val compositeDisposable = CompositeDisposable()
    val showDetails: LiveData<Show> by lazy {
        showDetailsRepo.fetchShow(compositeDisposable,showId)
    }

    val networkState:LiveData<NetworkState> by lazy {
        showDetailsRepo.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}