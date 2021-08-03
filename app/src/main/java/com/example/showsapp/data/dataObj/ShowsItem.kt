package com.example.showsapp.data.dataObj


import com.google.gson.annotations.SerializedName

data class ShowsItem(
    val averageRuntime: Int = 0,
    val dvdCountry: Any = Any(),

    val genres: List<String> = listOf(),
    val id: Int = 0,
    val image: Image = Image(),
    val language: String = "",
    val name: String = "",
    val officialSite: String = "",
    val premiered: String = "",
    val rating: Rating = Rating(),
    val runtime: Int = 0,
    val status: String = "",
    val summary: String = "",
    val type: String = "",
    val updated: Int = 0,
    val url: String = "",
    val weight: Int = 0
)