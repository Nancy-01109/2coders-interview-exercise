package com.example.interview_exercise_2coders.domain

data class MovieDetailsDomain(
    val id: Int,
    val title: String,
    val tagline: String?,
    val overview: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val rating: Double?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val genres: List<String>,
    val productionCompanies: List<String>,
    val productionCountries: List<String>,
    val spokenLanguages: List<String>
)
