package com.example.showsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.showsapp.data.api.POST_PER_PAGE
import com.example.showsapp.data.api.ShowInterface
import com.example.showsapp.data.dataObj.ShowsItem
import com.example.showsapp.repository.NetworkState
import com.example.showsapp.repository.ShowDataSource
import com.example.showsapp.repository.ShowDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class ShowPagedListRepo (private val apiService: ShowInterface){

    lateinit var showPagedList: LiveData<PagedList<ShowsItem>>
    lateinit var ShowDataSourceFactory: ShowDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<ShowsItem>> {
        ShowDataSourceFactory = ShowDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        showPagedList = LivePagedListBuilder(ShowDataSourceFactory, config).build()

        return showPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {

        return Transformations.switchMap<ShowDataSource, NetworkState>(
            ShowDataSourceFactory.showLiveDataSource, ShowDataSource::networkState)
    }
}