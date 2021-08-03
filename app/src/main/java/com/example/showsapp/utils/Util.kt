package com.example.showsapp.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.showsapp.R


// before bind
fun setEmptyTextIfNull(obj: String): String {
    var text = ""
    if (obj != "null") {
        text = obj
    }
    return text

}

// create Spinner
fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 15f
        start()
    }
}


// load Image and Spinner
fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.not_found)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri).into(this)

}