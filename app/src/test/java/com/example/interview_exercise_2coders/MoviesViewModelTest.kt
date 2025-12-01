package com.example.interview_exercise_2coders

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.interview_exercise_2coders.data.local.entity.FavoriteMovieEntity
import com.example.interview_exercise_2coders.data.repository.FavoriteMovieRepository
import com.example.interview_exercise_2coders.data.repository.MovieRepository
import com.example.interview_exercise_2coders.domain.MediaType
import com.example.interview_exercise_2coders.domain.MovieDetailsDomain
import com.example.interview_exercise_2coders.domain.MovieDomain
import com.example.interview_exercise_2coders.view_model.MovieDetailsState
import com.example.interview_exercise_2coders.view_model.MoviesViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(UnconfinedTestDispatcher())

    private lateinit var viewModel: MoviesViewModel
    private val movieRepository: MovieRepository = mockk()
    private val favoriteRepository: FavoriteMovieRepository = mockk()

    @Before
    fun setUp() {
        every { favoriteRepository.getFavorites() } returns flowOf(
            listOf(FavoriteMovieEntity(1, "Fav Movie", "Overview", "", "2020-01-01"))
        )
        coEvery { favoriteRepository.addFavorite(any()) } just Runs
        coEvery { favoriteRepository.removeFavorite(any()) } just Runs

        every { movieRepository.search(any(), any()) } returns flowOf(PagingData.empty())
        every { movieRepository.getMovies() } returns flowOf(PagingData.empty())
        coEvery { movieRepository.getMovieDetails(any()) } returns MovieDetailsDomain(
            id = 1,
            title = "Movie Detail",
            overview = "Overview",
            posterUrl = "",
            releaseDate = "2020-01-01",
            genres = emptyList(),
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            spokenLanguages = emptyList(),
        )

        viewModel = MoviesViewModel(movieRepository, favoriteRepository)
    }

    @Test
    fun toggleToFavoriteTest() = runTest {
        val movie = MovieDomain(2, "New Movie", "Overview", "", "2020-01-01", 0.0, releaseDate = "")

        viewModel.toggleFavorite(movie)

        coVerify { favoriteRepository.addFavorite(any()) }
    }

    @Test
    fun toggleToUnfavoriteTest() = runTest {
        val movie = MovieDomain(
            1, "Fav Movie", "Overview", "", "2020-01-01", 0.0,
            releaseDate = ""
        )
        viewModel.favoriteIds.stateIn(viewModel.viewModelScope)
        viewModel.toggleFavorite(movie)
        coVerify { favoriteRepository.removeFavorite(1) }
    }

    @Test
    fun loadMoviesSuccessTest() = runTest {
        viewModel.loadMovie(1)
        val state = viewModel.state.first()
        assertTrue(state is MovieDetailsState.Success)
        assertEquals("Movie Detail", (state as MovieDetailsState.Success).movie.title)
    }

    @Test
    fun setQueryStateTest() = runTest {
        viewModel.setSearchQuery("Batman")
        val query = viewModel.searchQuery.first()
        assertEquals("Batman", query)
    }

    @Test
    fun setMediaTypeTest() = runTest {
        viewModel.setMediaType(MediaType.MOVIE)
        val type = viewModel.mediaType.first()
        assertEquals(MediaType.MOVIE, type)
    }
}
