package com.example.biodun.mdb.features.Movie

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.biodun.mdb.Adapter.MovieAdapter
import com.example.biodun.mdb.BuildConfig
import com.example.biodun.mdb.Data.model.Movie
import com.example.biodun.mdb.MdbApplication
import com.example.biodun.mdb.MoviePreference
import com.example.biodun.mdb.R
import com.example.biodun.mdb.di.ViewModelFactory
import com.example.biodun.mdb.features.movieDetail.MovieDetailActivity
import kotlinx.android.synthetic.main.activity_movie.*
import javax.inject.Inject

const val API_KEY = BuildConfig.API_KEY

class MovieActivity : AppCompatActivity(), MovieAdapter.MovieClickInterface {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var movieViewModel: MovieViewModel
    lateinit var movieAdapter: MovieAdapter

    private val owner = { lifecycle }
    private var movies: MutableList<Movie> = mutableListOf()
    var moviePreference = MoviePreference.POPULAR.preference

    private val span: Int by lazy {
        val orientation = this.resources.configuration.orientation
        var spanNum = 0
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            spanNum = 2
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            spanNum = 3
        spanNum
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        (application as MdbApplication).appComponent.inject(this)
        setUpUi()
        observeMovieLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.popular -> {
                moviePreference = MoviePreference.POPULAR.preference
                movieViewModel.getMovies(moviePreference, API_KEY)
            }
            R.id.topRated -> {
                moviePreference = MoviePreference.TOP_RATED.preference
                movieViewModel.getMovies(moviePreference, API_KEY)
            }
            R.id.favorite -> {
                movieViewModel.getFavoriteMovies()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun observeMovieLiveData() {
            movieViewModel.moviesLiveData.observe(owner) {
                movies.apply {
                    clear()
                    addAll(it)
                }
                movieAdapter.notifyDataSetChanged()
            }
    }

    private fun setUpUi() {
        movieViewModel = ViewModelProviders.of(this, viewModelFactory)[MovieViewModel::class.java]
        movieAdapter = MovieAdapter(movies, this)

        with(movieRv) {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(this@MovieActivity, span)
        }

        movieViewModel.getMovies(moviePreference, API_KEY)
    }

    override fun onClickMovie(data: Movie) {
        val intent = Intent(this@MovieActivity, MovieDetailActivity::class.java)
        intent.putExtra("movies", data)
        startActivity(intent)
    }
}
