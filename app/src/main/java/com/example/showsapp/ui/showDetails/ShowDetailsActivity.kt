package com.example.showsapp.ui.showDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.showsapp.R
import com.example.showsapp.data.api.ShowClient
import com.example.showsapp.data.api.ShowInterface
import com.example.showsapp.data.dataObj.Show
import com.example.showsapp.repository.NetworkState
import com.example.showsapp.utils.getProgressDrawable
import com.example.showsapp.utils.loadImage
import com.example.showsapp.utils.setEmptyTextIfNull
import kotlinx.android.synthetic.main.activity_show_page.*


class ShowDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: ShowDetailsViewModel
    private lateinit var showDetailsRepo: ShowDetailsRepo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_page)
        supportActionBar?.hide()
        val showId: Int = intent.getIntExtra("id",1)



        val apiService:ShowInterface = ShowClient.getClient()
        showDetailsRepo = ShowDetailsRepo(apiService)
        viewModel = getViewModel(showId)
        viewModel.showDetails.observe(this, Observer {
            bindUI(it)
        })
    viewModel.networkState.observe(this, Observer {
        progress_bar.visibility = if (it== NetworkState.LOADING) View.VISIBLE else View.GONE
        txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
    })
    }


    fun bindUI( it: Show){
        show_title.text = it.name
        show_lang.text = it.language
        show_premiered_date.text = setEmptyTextIfNull(it.premiered.toString())
        show_rating.text =setEmptyTextIfNull(it.rating?.average.toString())
        show_status.text = setEmptyTextIfNull(it.status.toString())
        show_overview.text = Html.fromHtml(it.summary).toString()

        val progressDrawable = getProgressDrawable(applicationContext)
        iv_poster.loadImage(it.image?.original, progressDrawable)

    }



    private fun getViewModel(movieId:Int): ShowDetailsViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ShowDetailsViewModel(showDetailsRepo,movieId) as T
            }
        })[ShowDetailsViewModel::class.java]
    }
}