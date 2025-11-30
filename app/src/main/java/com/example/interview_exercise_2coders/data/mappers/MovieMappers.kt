package com.example.interview_exercise_2coders.data.mappers

import com.example.interview_exercise_2coders.data.dto.Movie
import com.example.interview_exercise_2coders.data.dto.MovieDetailsResponse
import com.example.interview_exercise_2coders.data.dto.MoviePage
import com.example.interview_exercise_2coders.domain.MovieDetails
import com.example.interview_exercise_2coders.domain.MovieDomain
import com.example.interview_exercise_2coders.domain.MoviePageDomain

fun MoviePage.toDomain(): MoviePageDomain {
    return MoviePageDomain(
        page = page,
        totalPages = totalPages,
        totalResults = totalResults,
        results = results.map { it.toDomain() }
    )
}

fun Movie.toDomain(): MovieDomain {
    return MovieDomain(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropUrl = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" },
        voteAverage = voteAverage,
        releaseDate = releaseDate
    )
}

fun MovieDetailsResponse.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        tagline = tagline,
        overview = overview,
        releaseDate = releaseDate,
        runtime = runtime,
        rating = voteAverage,
        posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropUrl = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" },
        genres = genres.map { it.name },
        productionCompanies = productionCompanies.map { it.name },
        productionCountries = productionCountries.map { it.name },
        spokenLanguages = spokenLanguages.map { it.englishName }
    )
}

