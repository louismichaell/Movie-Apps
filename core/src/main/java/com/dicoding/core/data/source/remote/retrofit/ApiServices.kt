package com.dicoding.core.data.source.remote.retrofit

import com.dicoding.core.BuildConfig
import com.dicoding.core.data.source.remote.response.DetailUpComingMovieResponse
import com.dicoding.core.data.source.remote.response.ListUpComingMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("movie/upcoming?language=en-US&page=1")
    suspend fun getUpComingMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): ListUpComingMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): DetailUpComingMovieResponse
}
