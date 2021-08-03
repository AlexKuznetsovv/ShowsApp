package com.example.showsapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.showsapp.data.api.FIRST_PAGE
import com.example.showsapp.data.api.ShowInterface
import com.example.showsapp.data.dataObj.ShowsItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ShowDataSource(
    private val apiService: ShowInterface,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, ShowsItem>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ShowsItem>
    ) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getShows(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                        Log.e("MovieDataSource", it[0].toString())
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource", it.message.toString())
                    }
                )
        )
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ShowsItem>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ShowsItem>) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getShows(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it[0].id >= params.key) {
                            callback.onResult(it, params.key + 1)
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState.ENDOFLIST)

                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("ShowDataSource", it.message.toString())
                    }
                )
        )

    }
}