package com.example.showsapp.ui.SearchFragment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.showsapp.R
import com.example.showsapp.data.dataObj.ResponseShowItems
import com.example.showsapp.data.dataObj.Show
import com.example.showsapp.ui.showDetails.ShowDetailsActivity
import com.example.showsapp.utils.getProgressDrawable
import com.example.showsapp.utils.loadImage
import kotlinx.android.synthetic.main.search_shows_list.view.*


class SearchAdapter(val context: Context) :
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    lateinit var showsData: List<ResponseShowItems>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.search_shows_list, parent, false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: SearchAdapter.MyViewHolder, position: Int) {
        holder.bind(showsData[position].show)
    }

    override fun getItemCount(): Int {
        return showsData.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.search_show_name
        val lang = view.search_show_lang
        val rating = view.search_show_rating
        val image = view.search_show_image
        val premiered = view.search_show_premiered
        val progressDrawable = getProgressDrawable(view.context)

        fun bind(data: Show) {
            title.text = data.name
            lang.text = "Language : " + data.language
            if (data.rating?.average != null) {
                rating.text =
                    "Rating : " + data.rating.average.toString()
                rating.visibility = View.VISIBLE
            } else {
                rating.visibility = View.GONE
            }

            premiered.text = "Premiered : " + data.premiered

            image.loadImage(data.image?.original, progressDrawable)


            itemView.setOnClickListener {
                val intent = Intent(it.context, ShowDetailsActivity::class.java)
                intent.putExtra("id", data.id)
                it.context.startActivity(intent)
            }
        }
    }
}