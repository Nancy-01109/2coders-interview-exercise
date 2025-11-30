package com.example.interview_exercise_2coders.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.interview_exercise_2coders.data.local.dao.FavoriteMovieDao
import com.example.interview_exercise_2coders.data.local.entity.FavoriteMovieEntity

@Database(entities = [FavoriteMovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
