package com.example.interview_exercise_2coders.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.interview_exercise_2coders.data.local.entity.CachedMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CachedMovieDao {

    @Query("SELECT * FROM cached_movies ORDER BY id ASC")
    fun getAllMoviesPaging(): PagingSource<Int, CachedMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<CachedMovieEntity>)

    @Query("DELETE FROM cached_movies")
    suspend fun clearAllMovies()

    @Query("SELECT COUNT(*) FROM cached_movies")
    suspend fun getMovieCount(): Int

    @Query("SELECT * FROM cached_movies")
    fun getAllMovies(): Flow<List<CachedMovieEntity>>

    @Query("SELECT * FROM cached_movies ORDER BY id DESC LIMIT 1")
    suspend fun getLastMovie(): CachedMovieEntity?

    @Query("SELECT * FROM cached_movies ORDER BY id ASC LIMIT 1")
    suspend fun getFirstMovie(): CachedMovieEntity?

    @Query("SELECT * FROM cached_movies WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): CachedMovieEntity?
}
