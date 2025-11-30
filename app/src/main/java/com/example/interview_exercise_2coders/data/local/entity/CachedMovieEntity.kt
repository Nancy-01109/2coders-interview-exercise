package com.example.interview_exercise_2coders.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_movies")
data class CachedMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val voteAverage: Double?,
    val releaseDate: String?
)
