package com.example.showsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.showsapp.data.api.ShowInterface
import com.example.showsapp.data.dataObj.SearchShowsResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SearchData(private val apiService: ShowInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() =_networkState


    private val _showResponse = MutableLiveData<SearchShowsResponse>()
    val showResponse: LiveData<SearchShowsResponse>
        get() =_showResponse


    fun fetchShows(name:String){
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getShowsByName(name)
                    .subscribeOn(Schedulers.io())
//                    .debounce(200, TimeUnit.MILLISECONDS)
                    .subscribe(
                        {
                            _showResponse.postValue(it)
                            Log.e("SEARCH", it.toString())
                            _networkState.postValue(NetworkState.LOADED)
                        },{
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message.toString())

                        }
                    )
            )

        }
        catch (e: Exception){
            Log.e("MovieDetailsDataSource",e.message.toString())
        }

    }

}