package com.example.interview_exercise_2coders.domain.search

data class SearchTvShowDomain(
    override val id: Int,
    val name: String,
    val originalName: String?,
    val firstAirDate: String?,
    override val posterPath: String?,
    override val backdropPath: String?,
    override val overview: String?,
    override val genreIds: List<Int>,
    override val popularity: Double?,
    override val voteAverage: Double?,
    override val voteCount: Int?
) : SearchItem
