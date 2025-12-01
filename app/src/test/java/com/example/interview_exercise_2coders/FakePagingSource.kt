package com.example.interview_exercise_2coders

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.interview_exercise_2coders.data.local.entity.CachedMovieEntity


class FakePagingSource : PagingSource<Int, CachedMovieEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CachedMovieEntity> {
        return LoadResult.Page(
            data = listOf(
                CachedMovieEntity(
                    id = 1,
                    title = "Fake",
                    overview = "Fake overview",
                    posterUrl = "",
                    releaseDate = "2020-01-01",
                    backdropUrl = " ",
                    voteAverage = 0.0
                )
            ),
            prevKey = null,
            nextKey = null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, CachedMovieEntity>): Int? = null
}
