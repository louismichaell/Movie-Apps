package com.dicoding.core.domain.usecase

import com.dicoding.core.data.source.Resource
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: IMovieRepository
) : MovieUseCase {
    override fun getUpComingMovies() = movieRepository.getMovies()

    override fun getDetailMovie(id: Int): Flow<Resource<Movie>> = movieRepository.getDetailMovie(id)

    override fun getMovieById(id: Int): Flow<Movie> = movieRepository.getMovieById(id)

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) =
        movieRepository.setFavoriteMovie(movie, state)

}