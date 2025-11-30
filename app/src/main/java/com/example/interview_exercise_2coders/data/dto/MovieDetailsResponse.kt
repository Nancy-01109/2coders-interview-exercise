package com.example.interview_exercise_2coders.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    val adult: Boolean,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("belongs_to_collection")
    val belongsToCollection: CollectionInfo? = null,

    val budget: Long,

    val genres: List<Genre> = emptyList(),

    val homepage: String? = null,
    val id: Int,

    @SerialName("imdb_id")
    val imdbId: String? = null,

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("original_title")
    val originalTitle: String,

    val overview: String? = null,
    val popularity: Double? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany> = emptyList(),

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry> = emptyList(),

    @SerialName("release_date")
    val releaseDate: String? = null,

    val revenue: Long,
    val runtime: Int? = null,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage> = emptyList(),

    val status: String? = null,
    val tagline: String? = null,
    val title: String,
    val video: Boolean,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null
)

@Serializable
data class Genre(
    val id: Int,
    val name: String
)

@Serializable
data class ProductionCompany(
    val id: Int,

    @SerialName("logo_path")
    val logoPath: String? = null,

    val name: String,

    @SerialName("origin_country")
    val originCountry: String
)

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1")
    val iso: String,

    val name: String
)

@Serializable
data class SpokenLanguage(
    @SerialName("english_name")
    val englishName: String,

    @SerialName("iso_639_1")
    val iso: String,

    val name: String
)

@Serializable
data class CollectionInfo(
    val id: Int,

    @SerialName("name")
    val title: String,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null
)

