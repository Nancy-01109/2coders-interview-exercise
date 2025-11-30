package com.example.interview_exercise_2coders.data

import com.example.interview_exercise_2coders.data.dto.MoviePage
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int = 1,
    ): MoviePage
}