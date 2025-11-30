package com.example.interview_exercise_2coders.data.repository

import com.example.interview_exercise_2coders.data.local.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieRepository {

    fun getFavorites(): Flow<List<FavoriteMovieEntity>>

    fun isFavorite(movieId: Int): Flow<Boolean>

    suspend fun addFavorite(movie: FavoriteMovieEntity)

    suspend fun removeFavorite(movieId: Int)
}
