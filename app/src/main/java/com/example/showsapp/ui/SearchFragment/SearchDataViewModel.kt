package com.example.showsapp.ui.SearchFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.showsapp.data.dataObj.SearchShowsResponse
import com.example.showsapp.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SearchDataViewModel(private val searchDataRepo: SearchDataRepo, showName:String): ViewModel (){

    private val compositeDisposable = CompositeDisposable()
    val showDetails: LiveData<SearchShowsResponse> by lazy {
        searchDataRepo.searchShowsByName(compositeDisposable,showName)
    }

    val networkState:LiveData<NetworkState> by lazy {
        searchDataRepo.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}