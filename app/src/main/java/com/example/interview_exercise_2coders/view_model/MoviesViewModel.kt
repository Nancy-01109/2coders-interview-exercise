package com.example.interview_exercise_2coders.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.interview_exercise_2coders.data.repository.MovieRepository
import com.example.interview_exercise_2coders.domain.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val movies = repository.getMovies()
        .cachedIn(viewModelScope)

    private val _state = MutableStateFlow<MovieDetailsState>(MovieDetailsState.Loading)
    val state: StateFlow<MovieDetailsState> = _state

    fun loadMovie(id: Int) {
        viewModelScope.launch {
            _state.value = MovieDetailsState.Loading

            try {
                val movie = repository.getMovieDetails(id)
                _state.value = MovieDetailsState.Success(movie)
            } catch (e: Exception) {
                _state.value = MovieDetailsState.Error(
                    e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    data class Success(val movie: MovieDetails) : MovieDetailsState()
    data class Error(val message: String) : MovieDetailsState()
}
