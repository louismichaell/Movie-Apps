package com.dicoding.core.di

import com.dicoding.core.data.source.MovieRepository
import com.dicoding.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(recipeRepository: MovieRepository): IMovieRepository
}
