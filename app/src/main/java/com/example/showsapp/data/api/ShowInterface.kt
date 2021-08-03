package com.example.showsapp.data.api


import com.example.showsapp.data.dataObj.SearchShowsResponse
import com.example.showsapp.data.dataObj.Show
import com.example.showsapp.data.dataObj.Shows
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowInterface {

    @GET("/shows/{showId}")
    @Headers("Accept: application/json")
    fun getShowById(@Path("showId") showId: Int?): Single<Show>

//

    @GET("/shows")
    @Headers("Accept: application/json")
    fun getShows(
        @Query("page")
        page: Int?
    ): Single<Shows>


    @GET("/search/shows/")
    @Headers("Accept: application/json")
    fun getShowsByName(
        @Query("q")
        name: String?
    ): Single<SearchShowsResponse>

}
