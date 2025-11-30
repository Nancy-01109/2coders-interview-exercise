package com.example.interview_exercise_2coders.data.repository

import androidx.paging.PagingData
import com.example.interview_exercise_2coders.domain.MovieDetails
import com.example.interview_exercise_2coders.domain.MovieDomain
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<MovieDomain>>
    suspend fun getMovieDetails(id: Int): MovieDetails
}