package com.example.interview_exercise_2coders.data.mappers

import com.example.interview_exercise_2coders.data.dto.Movie
import com.example.interview_exercise_2coders.data.dto.MoviePage
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
