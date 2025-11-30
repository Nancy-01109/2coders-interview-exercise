package com.example.interview_exercise_2coders.data.repository.impl

import com.example.interview_exercise_2coders.data.local.dao.FavoriteMovieDao
import com.example.interview_exercise_2coders.data.local.entity.FavoriteMovieEntity
import com.example.interview_exercise_2coders.data.repository.FavoriteMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteMovieRepositoryImpl @Inject constructor(
    private val dao: FavoriteMovieDao
) : FavoriteMovieRepository {
    override fun getFavorites(): Flow<List<FavoriteMovieEntity>> = dao.getAllFavorites()

    override fun isFavorite(movieId: Int): Flow<Boolean> = dao.isFavorite(movieId)

    override suspend fun addFavorite(movie: FavoriteMovieEntity) = dao.addFavorite(movie)

    override suspend fun removeFavorite(movieId: Int) = dao.removeFavorite(movieId)

}