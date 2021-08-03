package com.example.showsapp.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.showsapp.R
import com.example.showsapp.data.dataObj.ShowsItem
import com.example.showsapp.repository.NetworkState
import com.example.showsapp.ui.showDetails.ShowDetailsActivity
import com.example.showsapp.utils.getProgressDrawable
import com.example.showsapp.utils.loadImage
import com.example.showsapp.utils.setEmptyTextIfNull
import kotlinx.android.synthetic.main.network_state_item.view.*
import kotlinx.android.synthetic.main.show_list_item.view.*

class PageListAdapterHome(val context: Context) :
    PagedListAdapter<ShowsItem, RecyclerView.ViewHolder>(ShowDiffCallback()) {

    val SHOW_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == SHOW_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.show_list_item, parent, false)
            return ShowItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return NetworkStateItemViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == SHOW_VIEW_TYPE) {
            (holder as ShowItemViewHolder).bind(getItem(position), context)
        } else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            SHOW_VIEW_TYPE
        }
    }


    class ShowDiffCallback : DiffUtil.ItemCallback<ShowsItem>() {
        override fun areItemsTheSame(oldItem: ShowsItem, newItem: ShowsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShowsItem, newItem: ShowsItem): Boolean {
            return oldItem == newItem
        }

    }


    class ShowItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(showsItem: ShowsItem?, context: Context) {
            itemView.cv_show_title.text = showsItem?.name
            itemView.cv_show_premiered.text = setEmptyTextIfNull(showsItem?.premiered.toString())

            val progressDrawable = getProgressDrawable(context)
            itemView.cv_iv_show_poster.loadImage(showsItem?.image?.original, progressDrawable)


            itemView.setOnClickListener {
                val intent = Intent(context, ShowDetailsActivity::class.java)
                intent.putExtra("id", showsItem?.id)
                context.startActivity(intent)
            }

        }
    }

    class NetworkStateItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE
            } else {
                itemView.progress_bar_item.visibility = View.GONE
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = networkState.msg
            } else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE
                itemView.error_msg_item.text = networkState.msg
            } else {
                itemView.error_msg_item.visibility = View.GONE
            }
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }

}