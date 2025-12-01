package com.example.interview_exercise_2coders.domain

data class MovieDetailsDomain(
    val id: Int,
    val title: String,
    val tagline: String? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    val runtime: Int? = null,
    val rating: Double? = null,
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val genres: List<String>,
    val productionCompanies: List<String>,
    val productionCountries: List<String>,
    val spokenLanguages: List<String>
)
