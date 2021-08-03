package com.example.showsapp.ui.SearchFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.showsapp.R
import com.example.showsapp.data.api.ShowClient
import com.example.showsapp.data.api.ShowInterface
import com.example.showsapp.repository.NetworkState
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchDataViewModel

    lateinit var searchRepository: SearchDataRepo

    lateinit var searchAdapter: SearchAdapter


    var showName: String? = ""


    override fun onStart() {
        val apiService: ShowInterface = ShowClient.getClient()
        searchRepository = SearchDataRepo(apiService)
        viewModel = getViewModel(showName!!)
        viewModel.showDetails.observe(this, Observer {

            if (it.isNullOrEmpty()) {
                txt_no_results_fragment.visibility = View.VISIBLE

            } else {
                txt_no_results_fragment.visibility = View.GONE
                initRecyclerView()
                searchAdapter.showsData = it
                searchAdapter.notifyDataSetChanged()
            }

            Log.d("aaaaaaa", it.isNullOrEmpty().toString())
            Log.d("aaaaaaa", it.toString())


        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_fragment.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_fragment.visibility =
                if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
            rv_search_show_list.visibility =
                if (it == NetworkState.LOADED) View.VISIBLE else View.GONE

        })

        super.onStart()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = this.arguments
        showName = bundle!!.getString("name")


        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    private fun getViewModel(movieName: String): SearchDataViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SearchDataViewModel(searchRepository, movieName.replace(" ", "%")) as T
            }
        })[SearchDataViewModel::class.java]
    }

    private fun initRecyclerView() {

        rv_search_show_list.apply {
            layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration =
                DividerItemDecoration(context, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            searchAdapter = SearchAdapter(context)
            adapter = searchAdapter
        }

    }

}