package com.dicoding.core.domain.repository

import com.dicoding.core.data.source.Resource
import com.dicoding.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getMovies(): Flow<Resource<List<Movie>>>

    fun getDetailMovie(id: Int): Flow<Resource<Movie>>

    fun getMovieById(id: Int): Flow<Movie>

    fun getFavoriteMovie(): Flow<List<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)
}