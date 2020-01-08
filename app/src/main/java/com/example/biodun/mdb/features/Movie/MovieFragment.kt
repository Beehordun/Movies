package com.example.biodun.mdb.features.Movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.biodun.mdb.R
import com.example.biodun.mdb.di.ViewModelFactory
import javax.inject.Inject

class MovieFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //movieViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MovieViewModel::class.java)

    }
}
