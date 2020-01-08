package com.example.biodun.mdb.features.movieDetail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.biodun.mdb.Adapter.TrailerAdapter
import com.example.biodun.mdb.Data.model.Trailer
import com.example.biodun.mdb.MdbApplication
import com.example.biodun.mdb.R
import com.example.biodun.mdb.Utils.NetworkUtil
import com.example.biodun.mdb.di.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_trailer.*
import java.lang.StringBuilder
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class TrailerFragment : Fragment(), TrailerAdapter.TrailerClickHandler {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: MovieDetailViewModel
    lateinit var trailerAdapter: TrailerAdapter

    private val owner = { lifecycle }
    private var trailers: MutableList<Trailer> = mutableListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MdbApplication).appComponent.inject(this)
        viewModel = ViewModelProviders.of(context as MovieDetailActivity, viewModelFactory)[MovieDetailViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_trailer, container, false)
        observeTrailerLiveData()

        if (NetworkUtil.isNetworkAvailable(context!!)) {
            fetchMovieTrailers()
        } else {
            trailerNoInternetText.visibility = View.VISIBLE
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        displayMovieTrailers()
        fab.setOnClickListener {
            shareTrailerUrl()
        }
    }

    private fun observeTrailerLiveData() {
        viewModel.trailersLiveData.observe(owner) {
            trailers.apply {
                clear()
                addAll(it)
            }
            trailerAdapter.notifyDataSetChanged()
        }
    }

    private fun displayMovieTrailers() {
        trailerAdapter = TrailerAdapter(trailers, this)

        trailerRecyclerView.apply {
            adapter = trailerAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onClick(trailer: Trailer) {
        openTrailer(trailer.key)
    }

    private fun fetchMovieTrailers() {
        viewModel.apply {
            getMovieTrailers(getSelectedMovie().id.toString(), API_KEY)
        }
    }

    private fun openTrailer(key: String?) {
        val youtubeApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key"))
        val web = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$key"))
        activity?.let {
            try {
                if (youtubeApp.resolveActivity(it.packageManager) != null) {
                    startActivity(youtubeApp)
                }
            } catch (e: ActivityNotFoundException) {
                if (web.resolveActivity(it.packageManager) != null) {
                    startActivity(web)
                }
            }
        }
    }

    fun shareTrailerUrl() {
        val trailerUrl = "http://www.youtube.com/watch?v=${trailers[0].key}"
        val message = StringBuilder("Check out this movie\n")
                .appendln("Title: ${viewModel.getSelectedMovie().title}")
                .appendln("Link: $trailerUrl")
                .toString()

        val intents = Intent(Intent.ACTION_SEND)
        intents.type = "text/plain"
        intents.putExtra(Intent.EXTRA_TEXT, message)
        val chooseIntent = Intent.createChooser(intents, "Share")
        startActivity(chooseIntent)
    }

    companion object {
        private var instance: TrailerFragment? = null

        fun getFragmentInstance(): TrailerFragment {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = TrailerFragment()
                    }
                }
            }
            return instance!!
        }
    }
}