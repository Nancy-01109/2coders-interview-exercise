package com.example.interview_exercise_2coders.data.repository

import androidx.paging.PagingData
import com.example.interview_exercise_2coders.domain.MediaType
import com.example.interview_exercise_2coders.domain.MovieDetailsDomain
import com.example.interview_exercise_2coders.domain.MovieDomain
import com.example.interview_exercise_2coders.domain.SearchItem
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<MovieDomain>>
    suspend fun getMovieDetails(id: Int): MovieDetailsDomain
    fun search(searchQuery: String, mediaType: MediaType): Flow<PagingData<SearchItem>>
}