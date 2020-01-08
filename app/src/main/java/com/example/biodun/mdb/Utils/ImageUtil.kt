package com.example.biodun.mdb.Utils

import android.net.Uri

/**
 * Created by Biodun on 4/21/2017.
 */
object ImageUtil {
    private val SMALL_BASE_IMAGE_URL: String? = "http://image.tmdb.org/t/p/w185/"
    private val BIG_BASE_IMAGE_URL: String? = "http://image.tmdb.org/t/p/w342/"

    @Throws(NullPointerException::class)
    fun getSmallImageUrl(path: String): String? {
        return try {
            Uri.parse(SMALL_BASE_IMAGE_URL).buildUpon().appendEncodedPath(path).build().toString()
        } catch (e: NullPointerException) {
            null
        }
    }

    @Throws(NullPointerException::class)
    fun getBigImageUrl(path: String?): String? {
        return try {
             Uri.parse(BIG_BASE_IMAGE_URL).buildUpon().appendEncodedPath(path).build().toString()
        } catch (e: NullPointerException) {
            null
        }
    }
}
