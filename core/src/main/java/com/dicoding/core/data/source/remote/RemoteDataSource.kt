package com.dicoding.core.data.source.remote

import android.util.Log
import com.dicoding.core.data.source.remote.response.DetailUpComingMovieResponse
import com.dicoding.core.data.source.remote.response.ResultsItem
import com.dicoding.core.data.source.remote.retrofit.ApiResponse
import com.dicoding.core.data.source.remote.retrofit.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiServices){
    fun getUpComingMovies(): Flow<ApiResponse<List<ResultsItem?>?>> {
        return flow {
            try {
                val response = apiService.getUpComingMovies()
                val dataArray = response.results
                if (dataArray != null) {
                    if (dataArray.isNotEmpty()) {
                        emit(ApiResponse.Success(response.results))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailMovie(movieId: Int): Flow<ApiResponse<DetailUpComingMovieResponse>> {
        return flow {
            try {
                val response = apiService.getDetailMovie(movieId)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}