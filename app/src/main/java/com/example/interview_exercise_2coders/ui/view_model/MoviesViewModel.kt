package com.example.interview_exercise_2coders.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.interview_exercise_2coders.data.mappers.toFavoriteEntity
import com.example.interview_exercise_2coders.data.repository.FavoriteMovieRepository
import com.example.interview_exercise_2coders.data.repository.MovieRepository
import com.example.interview_exercise_2coders.domain.MediaType
import com.example.interview_exercise_2coders.domain.MovieDetailsDomain
import com.example.interview_exercise_2coders.domain.MovieDomain
import com.example.interview_exercise_2coders.domain.search.SearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val favoriteRepository: FavoriteMovieRepository
) : ViewModel() {

    val movies = repository.getMovies()
        .cachedIn(viewModelScope)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _mediaType = MutableStateFlow(MediaType.MOVIE)
    val mediaType: StateFlow<MediaType> = _mediaType.asStateFlow()

    private val searchParams: Flow<Pair<String, MediaType>> =
        combine(
            _searchQuery.debounce(300), // debounce only user input
            _mediaType
        ) { query, type -> query to type }

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

    val searchResults: Flow<PagingData<SearchItem>> =
        searchParams
            .distinctUntilChanged() // avoid triggering search for same params
            .flatMapLatest { (query, mediaType) ->
                repository.search(query, mediaType)
            }
            .cachedIn(viewModelScope)

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setMediaType(type: MediaType) {
        _mediaType.value = type
    }

    val favoriteIds: StateFlow<Set<Int>> =
        favoriteRepository.getFavorites()
            .map { list -> list.map { it.id }.toSet() }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptySet())

    fun toggleFavorite(movie: MovieDomain) {
        viewModelScope.launch {
            val isFav = favoriteIds.value.contains(movie.id)
            if (isFav) favoriteRepository.removeFavorite(movie.id)
            else favoriteRepository.addFavorite(
                movie.toFavoriteEntity()
            )
        }
    }
}

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    data class Success(val movie: MovieDetailsDomain) : MovieDetailsState()
    data class Error(val message: String) : MovieDetailsState()
}
