package com.example.interview_exercise_2coders.ui.helper

object ImageHelper {
    private const val BASE_URL = "https://image.tmdb.org/t/p/"

    fun getPosterUrl(posterPath: String?, size: String = "w500"): String? {
        return posterPath?.let { "$BASE_URL$size$it" }
    }

    fun getBackdropUrl(backdropPath: String?, size: String = "w780"): String? {
        return backdropPath?.let { "$BASE_URL$size$it" }
    }
}
