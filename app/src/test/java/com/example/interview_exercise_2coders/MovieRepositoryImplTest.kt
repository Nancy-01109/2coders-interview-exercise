package com.example.interview_exercise_2coders

import android.content.Context
import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.interview_exercise_2coders.core.util.isNetworkAvailable
import com.example.interview_exercise_2coders.data.MovieService
import com.example.interview_exercise_2coders.data.dto.MovieDetailsResponse
import com.example.interview_exercise_2coders.data.local.dao.CachedMovieDao
import com.example.interview_exercise_2coders.data.local.dao.RemoteKeysDao
import com.example.interview_exercise_2coders.data.local.database.AppDatabase
import com.example.interview_exercise_2coders.data.local.entity.CachedMovieEntity
import com.example.interview_exercise_2coders.data.repository.impl.MovieRepositoryImpl
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    private lateinit var repository: MovieRepositoryImpl

    private val service: MovieService = mockk()
    private val db: AppDatabase = mockk()
    private val context: Context = mockk()
    private val cachedDao: CachedMovieDao = mockk()
    private val remoteKeysDao: RemoteKeysDao = mockk()

    @Before
    fun setUp() {
        every { db.cachedMovieDao() } returns cachedDao
        every { db.remoteKeysDao() } returns remoteKeysDao

        coEvery { cachedDao.insertMovies(any()) } just Runs
        coEvery { cachedDao.clearAllMovies() } just Runs
        coEvery { remoteKeysDao.insertAll(any()) } just Runs
        coEvery { remoteKeysDao.clearRemoteKeys() } just Runs
        coEvery { remoteKeysDao.getFirstRemoteKey() } returns null

        repository = MovieRepositoryImpl(service, db, context)
    }


    @Test
    fun getMoviesTest() = runTest {
        every { cachedDao.getAllMoviesPaging() } returns FakePagingSource()

        val flow = repository.getMovies()
        flow.test {
            val item = awaitItem()
            assertTrue(item is PagingData<*>)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getMovieDetailsWithNetworkTest() = runTest {
        val apiResponse = MovieDetailsResponse(
            id = 1,
            title = "Online Movie",
            overview = "online overview",
            posterPath = "poster",
            releaseDate = "2020-01-01",
            adult = false,
            backdropPath = "",
            belongsToCollection = null,
            budget = 0,
            genres = emptyList(),
            homepage = "",
            imdbId = "",
            originalLanguage = "",
            originalTitle = "",
            popularity = 0.0,
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            revenue = 0,
            runtime = 0,
            spokenLanguages = emptyList(),
            status = "",
            tagline = "",
            video = false,
            voteAverage = 0.0,
            voteCount = 0
        )

        coEvery { service.getMovieDetails(1) } returns apiResponse
        every { context.isNetworkAvailable() } returns true

        val result = repository.getMovieDetails(1)
        assertEquals("Online Movie", result.title)
        coVerify { cachedDao.insertMovies(any()) }
    }

    @Test
    fun getMovieDetailsWhenOfflineTest() = runTest {
        val cached = CachedMovieEntity(
            id = 1,
            title = "Cached Movie",
            overview = "offline overview",
            posterUrl = "",
            releaseDate = "2020-01-01",
            backdropUrl = "",
            voteAverage = 0.0
        )

        every { context.isNetworkAvailable() } returns false
        coEvery { cachedDao.getMovieById(1) } returns cached

        val result = repository.getMovieDetails(1)
        assertEquals("Cached Movie", result.title)
    }

    @Test(expected = RuntimeException::class)
    fun getMovieDetailsWhenOfflineAndNoCacheTest() = runTest {
        every { context.isNetworkAvailable() } returns false
        coEvery { cachedDao.getMovieById(1) } returns null

        repository.getMovieDetails(1)
    }
}
