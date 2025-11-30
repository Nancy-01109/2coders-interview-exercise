package com.example.interview_exercise_2coders.domain

sealed interface SearchItem {
    val id: Int
    val posterPath: String?
    val backdropPath: String?
    val overview: String?
    val genreIds: List<Int>
    val popularity: Double?
    val voteAverage: Double?
    val voteCount: Int?
}
