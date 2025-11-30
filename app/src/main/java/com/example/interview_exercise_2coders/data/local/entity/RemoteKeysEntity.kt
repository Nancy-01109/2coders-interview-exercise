package com.example.interview_exercise_2coders.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey val movieId: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
