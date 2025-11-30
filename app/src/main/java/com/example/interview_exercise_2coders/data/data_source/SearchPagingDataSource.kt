package com.example.interview_exercise_2coders.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.interview_exercise_2coders.data.MovieService
import com.example.interview_exercise_2coders.data.mappers.toDomain
import com.example.interview_exercise_2coders.domain.MediaType
import com.example.interview_exercise_2coders.domain.SearchItem

class SearchPagingDataSource(
    private val service: MovieService,
    private val query: String,
    private val mediaType: MediaType
) : PagingSource<Int, SearchItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        val page = params.key ?: 1

        return try {
            val items: List<SearchItem>
            val totalPages: Int

            when (mediaType) {
                MediaType.MOVIE -> {
                    val response = service.searchMovies(page = page, query = query)
                    items = response.results.map { it.toDomain() }
                    totalPages = response.totalPages
                }

                MediaType.TV -> {
                    val response = service.searchTvShows(page = page, query = query)
                    items = response.results.map { it.toDomain() }
                    totalPages = response.totalPages
                }
            }

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= totalPages) null else page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}


