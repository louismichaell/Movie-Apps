package com.dicoding.core.data.source

import com.dicoding.core.data.source.local.LocalDataSource
import com.dicoding.core.data.source.remote.RemoteDataSource
import com.dicoding.core.data.source.remote.response.DetailUpComingMovieResponse
import com.dicoding.core.data.source.remote.response.ResultsItem
import com.dicoding.core.data.source.remote.retrofit.ApiResponse
import com.dicoding.core.domain.model.Movie
import com.dicoding.core.domain.repository.IMovieRepository
import com.dicoding.core.utils.AppExecutors
import com.dicoding.core.utils.MovieMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {
    override fun getMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkResourceHandler<List<Movie>, List<ResultsItem>>() {
            override fun fetchFromDB(): Flow<List<Movie>> {
                return localDataSource.getMovie().map {
                    MovieMapper.mapEntitiesToDomain(it)
                }
            }

            @Suppress("UNCHECKED_CAST")
            override suspend fun initiateNetworkCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getUpComingMovies() as Flow<ApiResponse<List<ResultsItem>>>

            override suspend fun saveNetworkResult(data: List<ResultsItem>) {
                val movieList = MovieMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }

            override fun shouldFetchFromNetwork(data: List<Movie>?): Boolean =
                data.isNullOrEmpty()

        }.getResourceFlow()

    override fun getDetailMovie(id: Int): Flow<Resource<Movie>> =
        object : NetworkResourceHandler<Movie, DetailUpComingMovieResponse>() {
            override fun fetchFromDB(): Flow<Movie> {
                return localDataSource.getMovieById(id).map {
                    MovieMapper.mapEntityToDomain(it)
                }
            }

            override suspend fun initiateNetworkCall(): Flow<ApiResponse<DetailUpComingMovieResponse>> =
                remoteDataSource.getDetailMovie(id)

            override suspend fun saveNetworkResult(data: DetailUpComingMovieResponse) {
                val movieEntity = MovieMapper.mapDetailResponseToEntity(data)
                localDataSource.insertMovie(listOf(movieEntity))
            }

            override fun shouldFetchFromNetwork(data: Movie?): Boolean =
                data == null
        }.getResourceFlow()

    override fun getMovieById(id: Int): Flow<Movie> {
        return localDataSource.getMovieById(id).map {
            MovieMapper.mapEntityToDomain(it)
        }
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            MovieMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = MovieMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteMovie(movieEntity, state)
        }
    }

}
