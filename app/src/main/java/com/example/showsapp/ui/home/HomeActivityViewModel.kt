package com.example.showsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.showsapp.data.dataObj.ShowsItem
import com.example.showsapp.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class HomeActivityViewModel(private val showRepository: ShowPagedListRepo) : ViewModel() {


    private val compositeDisposable = CompositeDisposable()


    val showPagedList: LiveData<PagedList<ShowsItem>> by lazy {
        showRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        showRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return showPagedList.value?.isEmpty() ?: true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}