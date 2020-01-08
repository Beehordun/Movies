package com.example.biodun.mdb.features.movieDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import com.example.biodun.mdb.Constants.EMPTY_STRING
import com.example.biodun.mdb.Data.model.Movie
import com.example.biodun.mdb.MdbApplication
import com.example.biodun.mdb.R
import com.example.biodun.mdb.Utils.ImageUtil
import com.example.biodun.mdb.di.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject

class MovieDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    private val owner = { lifecycle }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        (application as MdbApplication).appComponent.inject(this)
        getViewModel()
        setupUI()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getViewModel() {
        movieDetailViewModel = ViewModelProviders.of(this, viewModelFactory)[MovieDetailViewModel::class.java]
    }

    private fun setupUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = EMPTY_STRING
        showMovieImage()
        setupViewPagerAdapter()
        observeToastMessage()
        favMovieBtn.setOnClickListener {
            insertOrDeleteFavoriteMovie()
        }
    }

    private fun observeToastMessage() {
        movieDetailViewModel.toastMessage.observe(owner) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun insertOrDeleteFavoriteMovie() {
        movieDetailViewModel.apply {
            val movie = getSelectedMovie()

            if (isMoviePresentInDb(movie.id)) {
                showDeleteDialog()
            } else {
                insertFavorite(movie)
            }
        }
    }

    private fun showDeleteDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Delete Favorite Movie")
        alertDialog.setMessage("Do you really want to delete from favorites?")
        alertDialog.setPositiveButton("YES") { dialogInterface, i ->
           movieDetailViewModel.apply {
               deleteFavoriteMovie(getSelectedMovie())
           }
        }
        alertDialog.setNegativeButton("NO") { dialogInterface, i ->
            Toast.makeText(this, "Favorite movie is intact!", Toast.LENGTH_SHORT).show()
            dialogInterface.cancel()
        }
        alertDialog.show()
    }

    private fun showMovieImage() {
        val intentExtras = intent.extras
        val movie: Movie? = intentExtras?.getParcelable("movies")

        movie?.let {
            val imageUrl = ImageUtil.getSmallImageUrl(it.imageUrl)
            Picasso.with(movieImage.context).load(imageUrl).placeholder(R.drawable.poster1).into(movieImage)
            movieDetailViewModel.setSelectedMovie(it)
        }
    }

    private fun setupViewPagerAdapter() {
        movieViewPager.adapter = with(ViewPagerAdapter(supportFragmentManager)) {
            addFragment(OverviewFragment.getFragmentInstance(), getString(R.string.overview))
            addFragment(TrailerFragment.getFragmentInstance(), getString(R.string.trailer))
            addFragment(ReviewFragment.getFragmentInstance(), getString(R.string.review))
            this
        }
        movieTabs.setupWithViewPager(movieViewPager)
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList: MutableList<Fragment> = mutableListOf()
        private val mFragmentTitleList: MutableList<String> = mutableListOf()

        override fun getItem(position: Int): Fragment = mFragmentList[position]

        override fun getCount(): Int = mFragmentList.size

        override fun getPageTitle(position: Int): CharSequence = mFragmentTitleList[position]

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }
    }
}
