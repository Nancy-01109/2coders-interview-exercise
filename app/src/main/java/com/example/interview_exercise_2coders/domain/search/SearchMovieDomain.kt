package com.example.interview_exercise_2coders.domain.search

data class SearchMovieDomain(
    override val id: Int,
    val title: String,
    val originalTitle: String,
    val releaseDate: String?,
    override val posterPath: String?,
    override val backdropPath: String?,
    override val overview: String,
    override val genreIds: List<Int>,
    override val popularity: Double,
    override val voteAverage: Double,
    override val voteCount: Int
) : SearchItem
