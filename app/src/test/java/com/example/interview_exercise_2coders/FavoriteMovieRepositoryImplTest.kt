package com.example.interview_exercise_2coders

import com.example.interview_exercise_2coders.data.local.dao.FavoriteMovieDao
import com.example.interview_exercise_2coders.data.local.entity.FavoriteMovieEntity
import com.example.interview_exercise_2coders.data.repository.impl.FavoriteMovieRepositoryImpl
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteMovieRepositoryImplTest{

    private lateinit var repository: FavoriteMovieRepositoryImpl
    private val dao: FavoriteMovieDao = mockk()

    @Before
    fun setUp() {
        repository = FavoriteMovieRepositoryImpl(dao)
    }

    @Test
    fun getFavoritesTest() = runTest {
        val favorites = listOf(
            FavoriteMovieEntity(
                id = 1, title = "Movie 1",
                overview = "",
                posterUrl = "",
                releaseDate = ""
            ),
            FavoriteMovieEntity(
                id = 2,
                title = "Movie 2",
                overview = "",
                posterUrl = "",
                releaseDate = ""
            )
        )

        every { dao.getAllFavorites() } returns flowOf(favorites)

        val result = repository.getFavorites().first()
        assertEquals(2, result.size)
        assertEquals("Movie 1", result[0].title)
        assertEquals("Movie 2", result[1].title)
    }

    @Test
    fun checkIfItsFavoriteTestTrue() = runTest {
        every { dao.isFavorite(1) } returns flowOf(true)

        val result = repository.isFavorite(1).first()
        assertTrue(result)
    }

    @Test
    fun checkIfItsFavoriteTestFalse() = runTest {
        every { dao.isFavorite(2) } returns flowOf(false)

        val result = repository.isFavorite(2).first()
        assertTrue(!result)
    }

    @Test
    fun addFavoriteTest() = runTest {
        val movie = FavoriteMovieEntity(
            id = 3, title = "Movie 3", overview = "",
            posterUrl = "",
            releaseDate = ""
        )
        coEvery { dao.addFavorite(movie) } just Runs

        repository.addFavorite(movie)

        coVerify { dao.addFavorite(movie) }
    }

    @Test
    fun removeFavorite() = runTest {
        val movieId = 3
        coEvery { dao.removeFavorite(movieId) } just Runs

        repository.removeFavorite(movieId)

        coVerify { dao.removeFavorite(movieId) }
    }
}