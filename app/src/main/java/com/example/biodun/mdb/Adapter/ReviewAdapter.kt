package com.example.biodun.mdb.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.biodun.mdb.Adapter.ReviewAdapter.ReviewAdapterViewHolder
import com.example.biodun.mdb.Data.model.Review
import com.example.biodun.mdb.R
import kotlinx.android.synthetic.main.custom_review.view.*

/**
 * Created by Biodun on 5/12/2017.
 */
class ReviewAdapter(data: List<Review>) : RecyclerView.Adapter<ReviewAdapterViewHolder>() {
    private val reviews: List<Review> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.custom_review, parent, false)
        return ReviewAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewAdapterViewHolder, position: Int) {
        val data = reviews[position]
        holder.reviewerText.text = data.author
        holder.reviewText.text = data.content
    }

    override fun getItemCount(): Int = reviews.size

    inner class ReviewAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var reviewerText: TextView = view.reviewer
        var reviewText: TextView = view.review
    }
}
