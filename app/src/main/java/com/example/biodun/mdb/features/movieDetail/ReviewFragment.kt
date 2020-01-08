package com.example.biodun.mdb.features.movieDetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.biodun.mdb.Adapter.ReviewAdapter
import com.example.biodun.mdb.BuildConfig
import com.example.biodun.mdb.Data.model.Review
import com.example.biodun.mdb.MdbApplication
import com.example.biodun.mdb.R
import com.example.biodun.mdb.Utils.NetworkUtil
import com.example.biodun.mdb.di.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_review.*
import javax.inject.Inject

const val API_KEY = BuildConfig.API_KEY

class ReviewFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: MovieDetailViewModel
    lateinit var reviewAdapter: ReviewAdapter

    private val owner = { lifecycle }
    private var reviews: MutableList<Review> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_review, container, false)
        observeReviewLiveData()

        if (NetworkUtil.isNetworkAvailable(context!!)) {
            fetchMovieReviews()
        } else {
            noInternetText.visibility = View.VISIBLE
        }
        return v
    }

    override fun onResume() {
        super.onResume()
        displayMovieReviews()
    }

    private fun observeReviewLiveData() {
        viewModel.reviewsLiveData.observe(owner) {
            reviews.apply {
                clear()
                addAll(it)
            }
            reviewAdapter.notifyDataSetChanged()
        }
    }

    private fun displayMovieReviews() {
        reviewAdapter = ReviewAdapter(reviews)

        reviewRecyclerView.apply {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MdbApplication).appComponent.inject(this)
        viewModel = ViewModelProviders.of(context as MovieDetailActivity, viewModelFactory)[MovieDetailViewModel::class.java]
    }

    private fun fetchMovieReviews() {
        viewModel.apply {
            getMovieReviews(getSelectedMovie().id.toString(), API_KEY)
        }
    }


    companion object {
        private var instance: ReviewFragment? = null

        fun getFragmentInstance(): ReviewFragment {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = ReviewFragment()
                    }
                }
            }
            return instance!!
        }
    }
}