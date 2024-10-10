package com.dicoding.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.core.domain.usecase.MovieUseCase

class FavoriteMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    fun getFavoriteUpComingMovie() = movieUseCase.getFavoriteMovie().asLiveData()
}