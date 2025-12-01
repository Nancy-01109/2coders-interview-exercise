package com.example.interview_exercise_2coders.core

import android.content.Context
import com.example.interview_exercise_2coders.data.MovieService
import com.example.interview_exercise_2coders.data.local.dao.FavoriteMovieDao
import com.example.interview_exercise_2coders.data.local.database.AppDatabase
import com.example.interview_exercise_2coders.data.repository.FavoriteMovieRepository
import com.example.interview_exercise_2coders.data.repository.MovieRepository
import com.example.interview_exercise_2coders.data.repository.impl.FavoriteMovieRepositoryImpl
import com.example.interview_exercise_2coders.data.repository.impl.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesMoviesRepository(
        movieService: MovieService,
        appDatabase: AppDatabase,
        @ApplicationContext context: Context
    ): MovieRepository = MovieRepositoryImpl(
        service = movieService,
        appDatabase = appDatabase,
        context=context
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