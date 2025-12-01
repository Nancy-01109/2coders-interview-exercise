package com.example.interview_exercise_2coders.data.repository.impl

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.interview_exercise_2coders.core.util.isNetworkAvailable
import com.example.interview_exercise_2coders.data.MovieService
import com.example.interview_exercise_2coders.data.data_source.MovieRemoteMediator
import com.example.interview_exercise_2coders.data.data_source.SearchPagingDataSource
import com.example.interview_exercise_2coders.data.local.database.AppDatabase
import com.example.interview_exercise_2coders.data.mappers.toCachedMovieEntity
import com.example.interview_exercise_2coders.data.mappers.toDomain
import com.example.interview_exercise_2coders.data.mappers.toMovieDetailsDomain
import com.example.interview_exercise_2coders.data.mappers.toMovieDomain
import com.example.interview_exercise_2coders.data.repository.MovieRepository
import com.example.interview_exercise_2coders.domain.MediaType
import com.example.interview_exercise_2coders.domain.MovieDetailsDomain
import com.example.interview_exercise_2coders.domain.MovieDomain
import com.example.interview_exercise_2coders.domain.SearchItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val appDatabase: AppDatabase,
    private val context: Context
) : MovieRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(): Flow<PagingData<MovieDomain>> {
        val pagingSourceFactory = {
            appDatabase.cachedMovieDao().getAllMoviesPaging()
        }
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            remoteMediator = MovieRemoteMediator(service, appDatabase),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData -> pagingData.map { it.toMovieDomain() } }
    }

    override suspend fun getMovieDetails(id: Int): MovieDetailsDomain {
        try {
            return if (context.isNetworkAvailable()) {
                val response = service.getMovieDetails(movieId = id)
                appDatabase.cachedMovieDao()
                    .insertMovies(listOf(response.toCachedMovieEntity()))
                response.toDomain()
            } else {
                val cached = appDatabase.cachedMovieDao().getMovieById(id)
                    ?: throw RuntimeException("No internet and movie not found in cache.")
                cached.toMovieDetailsDomain()
            }

        } catch (e: Exception) {
            throw RuntimeException(
                "Unable to load movie details online or offline: ${e.message}",
                e
            )
        }
    }

    override fun search(searchQuery: String, mediaType: MediaType): Flow<PagingData<SearchItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchPagingDataSource(service, searchQuery, mediaType) }
        ).flow
    }
}
