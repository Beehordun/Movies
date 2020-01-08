package com.example.biodun.mdb.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.biodun.mdb.Adapter.TrailerAdapter.TrailerAdapterViewHolder
import com.example.biodun.mdb.Data.model.Trailer
import com.example.biodun.mdb.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_trailer.view.*

/**
 * Created by Biodun on 5/13/2017.
 */
class TrailerAdapter(data: List<Trailer>, handler: TrailerClickHandler) : RecyclerView.Adapter<TrailerAdapterViewHolder>() {
    private val trailers: List<Trailer> = data
    private val clickHandler: TrailerClickHandler = handler

    interface TrailerClickHandler {
        fun onClick(trailer: Trailer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_trailer, parent, false)
        return TrailerAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrailerAdapterViewHolder, position: Int) {
        val data = trailers[position]
        holder.itemView.trailerName.text = data.name
        val url = getTrailerImage(data.key)
        Picasso.with(holder.itemView.trailerImage.context).load(url)
                .placeholder(R.drawable.video2)
                .into(holder.itemView.trailerImage)
    }

    private fun getTrailerImage(key: String) = StringBuilder("http://img.youtube.com/vi/")
                                                           .append(key)
                                                           .append("/1.jpg")
                                                           .toString()

    override fun getItemCount(): Int = trailers.size

    inner class TrailerAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(view: View?) {
            val holdMovieData = trailers[adapterPosition]
            clickHandler.onClick(holdMovieData)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}
