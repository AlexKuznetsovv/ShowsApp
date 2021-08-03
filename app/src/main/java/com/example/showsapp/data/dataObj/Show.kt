package com.example.showsapp.data.dataObj


import com.google.gson.annotations.SerializedName

data class Show(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("image")
    val image: Image? = null,

    @SerializedName("language")
    val language: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("officialSite")
    val officialSite: String? = null,

    @SerializedName("premiered")
    val premiered: String? = null,

    @SerializedName("rating")
    val rating: Rating? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("summary")
    val summary: String? = null,

    @SerializedName("updated")
    val updated: Int? = null,

    @SerializedName("url")
    val url: String? = null,



)
data class Rating(
    @SerializedName("average")
    val average: Double? = null
)
data class Image(
    @SerializedName("medium")
    val medium: String? = null,
    @SerializedName("original")
    val original: String? = null
)