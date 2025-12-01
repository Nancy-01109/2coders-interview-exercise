package com.example.interview_exercise_2coders.domain

data class MovieDomain(
    val id: Int,
    val title: String,
    val overview: String? = null,
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val voteAverage: Double? = null,
    val releaseDate: String? = null
)

