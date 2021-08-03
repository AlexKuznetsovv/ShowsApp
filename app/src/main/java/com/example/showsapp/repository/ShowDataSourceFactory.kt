package com.example.showsapp.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.showsapp.data.api.ShowInterface
import com.example.showsapp.data.dataObj.ShowsItem
import io.reactivex.disposables.CompositeDisposable


class ShowDataSourceFactory(
    private val apiService: ShowInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, ShowsItem>() {

    val showLiveDataSource = MutableLiveData<ShowDataSource>()

    override fun create(): DataSource<Int, ShowsItem> {
        val movieDataSource = ShowDataSource(apiService, compositeDisposable)

        showLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}