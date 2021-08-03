package com.example.showsapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.showsapp.R
import com.example.showsapp.data.api.ShowClient
import com.example.showsapp.data.api.ShowInterface
import com.example.showsapp.repository.NetworkState
import com.example.showsapp.ui.SearchFragment.SearchDataRepo
import com.example.showsapp.ui.SearchFragment.SearchFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    private lateinit var viewModel: HomeActivityViewModel

    lateinit var movieRepository: ShowPagedListRepo

    lateinit var searchRepository: SearchDataRepo

    lateinit var searchFragment: SearchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        val apiService: ShowInterface = ShowClient.getClient()

        movieRepository = ShowPagedListRepo(apiService)
        searchRepository = SearchDataRepo(apiService)

        viewModel = getViewModel()

        val movieAdapter = PageListAdapterHome(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.SHOW_VIEW_TYPE) return 1    // Movie_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        }


        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.showPagedList.observe(this, Observer {

            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_fragment.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })




        search_EditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


                if (s!!.isNotEmpty()) {
                    closeEditText.visibility = View.VISIBLE
                    val bundle = Bundle()
                    bundle.putString("name", s.toString())

                    rv_movie_list.visibility = View.GONE
                    searchFragment = SearchFragment()
                    searchFragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.searchFragment, searchFragment)
                        .commit()

                } else {
                    closeEditText.visibility = View.VISIBLE
                    rv_movie_list.visibility = View.VISIBLE
                }
            }

        })

        closeEditText.setOnClickListener(View.OnClickListener {
            search_EditText.text.clear()
            search_EditText.clearFocus()
            closeEditText.visibility = View.GONE
            supportFragmentManager.beginTransaction().remove(searchFragment)


        })


    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


    private fun getViewModel(): HomeActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HomeActivityViewModel(movieRepository) as T
            }
        })[HomeActivityViewModel::class.java]
    }


}




