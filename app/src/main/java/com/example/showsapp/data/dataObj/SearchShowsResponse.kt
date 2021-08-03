package com.example.showsapp.data.dataObj

class SearchShowsResponse : ArrayList<ResponseShowItems>()


data class ResponseShowItems(
    val score: Double = 0.0,
    val show: Show = Show()
)