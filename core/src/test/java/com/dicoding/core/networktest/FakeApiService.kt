package com.dicoding.core.networktest

import com.dicoding.core.data.source.remote.response.DetailUpComingMovieResponse
import com.dicoding.core.data.source.remote.response.ListUpComingMovieResponse
import com.dicoding.core.data.source.remote.retrofit.ApiServices
import com.dicoding.core.dummy.DataDummy

class FakeApiService : ApiServices {

    var shouldReturnEmptyList = false

    override suspend fun getUpComingMovies(apiKey: String): ListUpComingMovieResponse {
        return if (shouldReturnEmptyList) {
            ListUpComingMovieResponse(results = emptyList())
        } else {
            DataDummy.generateDummyUpcomingMovieResponse()
        }
    }

    override suspend fun getDetailMovie(movieId: Int, apiKey: String): DetailUpComingMovieResponse {
        val movie = DataDummy.generateDummyUpcomingMovieResponse().results?.firstOrNull { it?.id == movieId }

        return DetailUpComingMovieResponse(
            id = movie?.id ?: 0,
            title = movie?.title ?: "Unknown",
            overview = movie?.overview ?: "No description available",
            releaseDate = movie?.releaseDate ?: "Unknown",
            voteAverage = movie?.voteAverage ?: 0.0,
            voteCount = movie?.voteCount ?: 0,
            backdropPath = movie?.backdropPath ?: "",
            posterPath = movie?.posterPath ?: "",
            popularity = movie?.popularity ?: 0.0,
            originalLanguage = movie?.originalLanguage ?: "Unknown",
            genres = null
        )
    }
}
