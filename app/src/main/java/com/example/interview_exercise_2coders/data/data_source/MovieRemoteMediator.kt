package com.example.interview_exercise_2coders.data.data_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.interview_exercise_2coders.data.MovieService
import com.example.interview_exercise_2coders.data.local.database.AppDatabase
import com.example.interview_exercise_2coders.data.local.entity.CachedMovieEntity
import com.example.interview_exercise_2coders.data.local.entity.RemoteKeysEntity
import com.example.interview_exercise_2coders.data.mappers.toMovieEntity

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val service: MovieService,
    private val db: AppDatabase
) : RemoteMediator<Int, CachedMovieEntity>() {

    private val cachedDao = db.cachedMovieDao()
    private val remoteKeysDao = db.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CachedMovieEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    println("REFRESH: Starting from page 1")
                    1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val remoteKey = remoteKeysDao.getFirstRemoteKey()
                    remoteKey?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            println("Fetching API page: $page")
            val response = service.discoverMovies(page)
            val movies = response.results.map { it.toMovieEntity() }
            println("Received ${movies.size} movies, total pages: ${response.totalPages}")

            val endOfPaginationReached = movies.isEmpty() || page >= response.totalPages
            val nextPage = if (endOfPaginationReached) null else page + 1

            println("Pagination - End reached: $endOfPaginationReached, Next page: $nextPage")

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    cachedDao.clearAllMovies()
                    remoteKeysDao.clearRemoteKeys()
                }

                cachedDao.insertMovies(movies)
                println("Inserted ${movies.size} movies")

                val paginationKey = RemoteKeysEntity(
                    movieId = -1,
                    prevPage = if (page > 1) page - 1 else null,
                    nextPage = nextPage
                )
                remoteKeysDao.insertAll(listOf(paginationKey))
                println("Updated pagination key with nextPage: $nextPage")
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            println("RemoteMediator ERROR: ${e.message}")
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }
}