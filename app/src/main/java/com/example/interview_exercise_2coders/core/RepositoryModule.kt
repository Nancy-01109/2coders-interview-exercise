package com.example.interview_exercise_2coders.core

import com.example.interview_exercise_2coders.data.MovieService
import com.example.interview_exercise_2coders.data.local.dao.FavoriteMovieDao
import com.example.interview_exercise_2coders.data.repository.FavoriteMovieRepository
import com.example.interview_exercise_2coders.data.repository.MovieRepository
import com.example.interview_exercise_2coders.data.repository.impl.FavoriteMovieRepositoryImpl
import com.example.interview_exercise_2coders.data.repository.impl.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providesMoviesRepository(
        movieService: MovieService
    ): MovieRepository = MovieRepositoryImpl(
        service = movieService,
    )

    @Provides
    @Singleton
    fun provideFavoriteMovieRepository(
        dao: FavoriteMovieDao
    ): FavoriteMovieRepository =
        FavoriteMovieRepositoryImpl(
            dao = dao,
        )
}