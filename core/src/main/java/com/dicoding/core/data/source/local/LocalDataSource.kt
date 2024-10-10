package com.dicoding.core.data.source.local

import com.dicoding.core.data.source.local.entity.MovieEntity
import com.dicoding.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    fun getMovie(): Flow<List<MovieEntity>> = movieDao.getMovie()

    fun getMovieById(id: Int): Flow<MovieEntity> = movieDao.getMovieById(id)

    suspend fun insertMovie(movieList: List<MovieEntity>) = movieDao.insertMovie(movieList)

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }
}