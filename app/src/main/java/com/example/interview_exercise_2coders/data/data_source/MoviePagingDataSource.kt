//package com.example.interview_exercise_2coders.data.data_source
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.example.interview_exercise_2coders.data.MovieService
//import com.example.interview_exercise_2coders.data.mappers.toDomain
//import com.example.interview_exercise_2coders.domain.MovieDomain
//
//class MoviePagingDataSource(
//    private val service: MovieService,
//
//    ) : PagingSource<Int, MovieDomain>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDomain> {
//        val page = params.key ?: 1
//
//        return try {
//            val response = service.discoverMovies(
//                page = page,
//            )
//            println("Received ${response.results.size} items from API")
//
//            val nextKey = if (page >= response.totalPages) null else page + 1
//
//            LoadResult.Page(
//                data = response.results.map { it.toDomain() },
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = nextKey
//            )
//        } catch (e: Exception) {
//            e.printStackTrace()
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, MovieDomain>): Int? {
//        return state.anchorPosition?.let { anchor ->
//            val page = state.closestPageToPosition(anchor)
//            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
//        }
//    }
//}
//
//
