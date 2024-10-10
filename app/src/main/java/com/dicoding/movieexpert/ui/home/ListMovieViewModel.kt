package com.dicoding.movieexpert.ui.home

import androidx.lifecycle.asLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListMovieViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel() {
    val movie = movieUseCase.getUpComingMovies().asLiveData()
}