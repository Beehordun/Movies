package com.example.biodun.mdb.features.movieDetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.biodun.mdb.MdbApplication
import com.example.biodun.mdb.R
import com.example.biodun.mdb.Utils.ImageUtil
import com.example.biodun.mdb.di.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_overview.*
import javax.inject.Inject

class OverviewFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: MovieDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onResume() {
        super.onResume()
        displayMovieOverview()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MdbApplication).appComponent.inject(this)
        viewModel = ViewModelProviders.of(context as MovieDetailActivity, viewModelFactory)[MovieDetailViewModel::class.java]
    }

    private fun displayMovieOverview() {
        with(viewModel.getSelectedMovie()) {
            overText.text = overview
            overview_name.text = title
            overview_date.text = releaseDate
            textRating.text = ratings.toString()
            Picasso.with(context).load(ImageUtil.getSmallImageUrl(imageUrl))
                    .placeholder(R.drawable.poster1)
                    .into(poster)
        }
    }

    companion object {
        private var instance: OverviewFragment? = null

        fun getFragmentInstance(): OverviewFragment {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = OverviewFragment()
                    }
                }
            }
            return instance!!
        }
    }
}
