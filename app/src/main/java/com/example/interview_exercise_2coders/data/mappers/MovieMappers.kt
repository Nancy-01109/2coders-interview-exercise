package com.example.interview_exercise_2coders.data.mappers

import com.example.interview_exercise_2coders.data.dto.Movie
import com.example.interview_exercise_2coders.data.dto.MovieDetailsResponse
import com.example.interview_exercise_2coders.data.dto.SearchMovieDto
import com.example.interview_exercise_2coders.data.dto.SearchTvDto
import com.example.interview_exercise_2coders.data.local.entity.CachedMovieEntity
import com.example.interview_exercise_2coders.domain.MovieDetailsDomain
import com.example.interview_exercise_2coders.domain.MovieDomain
import com.example.interview_exercise_2coders.domain.SearchMovieDomain
import com.example.interview_exercise_2coders.domain.SearchTvShowDomain


fun MovieDetailsResponse.toDomain(): MovieDetailsDomain {
    return MovieDetailsDomain(
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
        spokenLanguages = spokenLanguages.map { it.englishName },
    )
}

fun SearchMovieDto.toDomain() = SearchMovieDomain(
    id = id,
    title = title,
    originalTitle = originalTitle,
    releaseDate = releaseDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    overview = overview,
    genreIds = genreIds,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun SearchTvDto.toDomain() = SearchTvShowDomain(
    id = id,
    name = name,
    originalName = originalName,
    firstAirDate = firstAirDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    overview = overview,
    genreIds = genreIds,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun MovieDetailsDomain.toMovieDomain(): MovieDomain {
    return MovieDomain(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterUrl,
        backdropUrl = this.backdropUrl,
        voteAverage = this.rating,
        releaseDate = this.releaseDate
    )
}

fun MovieDetailsResponse.toCachedMovieEntity(): CachedMovieEntity {
    return CachedMovieEntity(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterPath,
        backdropUrl = this.backdropPath,
        voteAverage = this.voteAverage,
        releaseDate = this.releaseDate
    )
}

fun Movie.toMovieEntity(): CachedMovieEntity {
    return CachedMovieEntity(
        id = this.id,
        title = this.title,
        overview = this.overview,
        posterUrl = this.posterPath,
        backdropUrl = this.backdropPath,
        voteAverage = this.voteAverage,
        releaseDate = this.releaseDate
    )
}

fun CachedMovieEntity.toMovieDomain() = MovieDomain(
    id = id,
    title = title,
    overview = overview,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    voteAverage = voteAverage,
    releaseDate = releaseDate
)
fun CachedMovieEntity.toMovieDetailsDomain() = MovieDetailsDomain(
    id = id,
    title = title,
    overview = overview,
    posterUrl = posterUrl,
    backdropUrl = backdropUrl,
    tagline = null,
    releaseDate = releaseDate,
    runtime = null,
    rating = voteAverage,
    genres = emptyList(),
    productionCompanies = emptyList(),
    productionCountries = emptyList(),
    spokenLanguages = emptyList()
)





