package com.example.interview_exercise_2coders.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.interview_exercise_2coders.data.local.dao.CachedMovieDao
import com.example.interview_exercise_2coders.data.local.dao.FavoriteMovieDao
import com.example.interview_exercise_2coders.data.local.dao.RemoteKeysDao
import com.example.interview_exercise_2coders.data.local.entity.FavoriteMovieEntity
import com.example.interview_exercise_2coders.data.local.entity.CachedMovieEntity
import com.example.interview_exercise_2coders.data.local.entity.RemoteKeysEntity

@Database(
    entities = [FavoriteMovieEntity::class, CachedMovieEntity::class, RemoteKeysEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun cachedMovieDao(): CachedMovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}
