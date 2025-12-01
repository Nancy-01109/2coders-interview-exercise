package com.example.interview_exercise_2coders.data.service

import com.example.interview_exercise_2coders.data.dto.MovieDetailsResponse
import com.example.interview_exercise_2coders.data.dto.MoviePage
import com.example.interview_exercise_2coders.data.dto.SearchMovieResponse
import com.example.interview_exercise_2coders.data.dto.SearchTvResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int = 1,
    ): MoviePage

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
    ): MovieDetailsResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): SearchMovieResponse

    @GET("search/tv")
    suspend fun searchTvShows(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): SearchTvResponse
}