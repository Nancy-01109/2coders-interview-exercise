package com.example.interview_exercise_2coders.domain

data class MoviePageDomain(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val results: List<MovieDomain>
)

data class MovieDomain(
    val id: Int,
    val title: String,
    val overview: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val voteAverage: Double?,
    val releaseDate: String?
)

