package com.example.showsapp.ui.SearchFragment

import androidx.lifecycle.LiveData
import com.example.showsapp.data.api.ShowInterface
import com.example.showsapp.data.dataObj.ResponseShowItems
import com.example.showsapp.data.dataObj.SearchShowsResponse
import com.example.showsapp.data.dataObj.Show
import com.example.showsapp.data.dataObj.ShowsItem
import com.example.showsapp.repository.NetworkState
import com.example.showsapp.repository.SearchData
import io.reactivex.disposables.CompositeDisposable

class SearchDataRepo (private val apiService:ShowInterface){

    lateinit var searchData: SearchData


    fun searchShowsByName(compositeDisposable: CompositeDisposable, showName:String): LiveData<SearchShowsResponse>{
        searchData = SearchData(apiService,compositeDisposable)
        searchData.fetchShows(showName)
        return searchData.showResponse
    }

    fun getNetworkState():LiveData<NetworkState>{
        return searchData.networkState
    }

}