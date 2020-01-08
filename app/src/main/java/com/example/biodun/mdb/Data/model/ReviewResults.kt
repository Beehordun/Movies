package com.example.biodun.mdb.Data.model

import com.example.biodun.mdb.Data.model.Review
import com.google.gson.annotations.SerializedName

data class ReviewResults(@SerializedName("results")
                        var results: List<Review>)