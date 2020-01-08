package com.example.biodun.mdb.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.biodun.mdb.Adapter.MovieAdapter.AdapterViewHolder
import com.example.biodun.mdb.Data.model.Movie
import com.example.biodun.mdb.R
import com.example.biodun.mdb.Utils.ImageUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_view.view.*

/**
 * Created by Biodun on 4/22/2017.
 */
class MovieAdapter(data: List<Movie>, handler: MovieClickInterface) : RecyclerView.Adapter<AdapterViewHolder>() {
    private var mData: List<Movie> = data
    private val movieClickInterface: MovieClickInterface = handler

    interface MovieClickInterface {
        fun onClickMovie(data: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_view, parent, false)
        return AdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val data = mData[position]
        val url = ImageUtil.getSmallImageUrl(data.imageUrl)
        holder.movieTitle.text = data.title

        Picasso.with(holder.movieImage.context)
                .load(url)
                .placeholder(R.drawable.poster1)
                .into(holder.movieImage)
    }

    override fun getItemCount(): Int = mData.size

    inner class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var movieTitle: TextView = view.movieTitle
        var movieImage: ImageView = view.movieImage

        override fun onClick(view: View?) {
            val movie = mData[adapterPosition]
            movieClickInterface.onClickMovie(movie)
        }

        init {
            view.setOnClickListener(this)
        }
    }
}
