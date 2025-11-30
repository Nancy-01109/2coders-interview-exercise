package com.example.interview_exercise_2coders.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.interview_exercise_2coders.data.MovieService
import com.example.interview_exercise_2coders.data.data_source.MoviePagingDataSource
import com.example.interview_exercise_2coders.domain.MovieDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val service: MovieService
) : MovieRepository {
    override fun getMovies(
    ): Flow<PagingData<MovieDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingDataSource(service) }
        ).flow
    }
}
