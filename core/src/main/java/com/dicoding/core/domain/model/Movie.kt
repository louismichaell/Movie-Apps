package com.dicoding.core.domain.model

import android.os.Parcelable
import com.dicoding.core.utils.BASE_IMAGE_URL
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val overview: String,
    val movieId: Int,
    val isFavorite: Boolean,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val popularity: Double,
    val originalLanguage: String,
    val voteAverage: Double,
    val title: String
) : Parcelable {

    val fullPosterPath: String
        get() = "$BASE_IMAGE_URL$posterPath"

    val fullDropPath: String
        get() = "$BASE_IMAGE_URL$backdropPath"
}