package com.example.showsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.showsapp.data.api.ShowInterface
import com.example.showsapp.data.dataObj.Show
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ShowDetailsNetworkSource(private val apiService:ShowInterface, private val compositeDisposable:CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() =_networkState


    private val _showResponse = MutableLiveData<Show>()
    val showResponse: LiveData<Show>
        get() =_showResponse

    fun fetchShow(showId:Int){
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiService.getShowById(showId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _showResponse.postValue(it)
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