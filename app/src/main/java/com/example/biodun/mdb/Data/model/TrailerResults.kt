package com.example.biodun.mdb.Data.model

import com.example.biodun.mdb.Data.model.Trailer
import com.google.gson.annotations.SerializedName

data class TrailerResults(@SerializedName("results")
                        var results: List<Trailer>)