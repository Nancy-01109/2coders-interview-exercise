package com.example.interview_exercise_2coders.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// It should just be an Id, here there is no need for the other elements, i can find them in the cache
@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String?,
    val posterUrl: String?,
    val releaseDate: String?
)