package com.example.biodun.mdb.Data.model

import com.example.biodun.mdb.Data.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResults(@SerializedName("results")
                        var results: List<Movie>)