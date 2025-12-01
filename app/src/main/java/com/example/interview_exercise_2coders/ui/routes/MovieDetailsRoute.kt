package com.example.interview_exercise_2coders.ui.routes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.interview_exercise_2coders.R
import com.example.interview_exercise_2coders.data.mappers.toMovieDomain
import com.example.interview_exercise_2coders.ui.helper.ImageHelper
import com.example.interview_exercise_2coders.ui.view_model.MovieDetailsState
import com.example.interview_exercise_2coders.ui.view_model.MoviesViewModel

@Composable
fun MovieDetailsRoute(
    movieId: Int,
    viewModel: MoviesViewModel,
) {
    LaunchedEffect(movieId) {
        viewModel.loadMovie(movieId)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val isFavorite by viewModel.favoriteIds.collectAsStateWithLifecycle()

    when (state) {
        is MovieDetailsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MovieDetailsState.Success -> {
            val movie = (state as MovieDetailsState.Success).movie
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    movie.posterUrl?.let { url ->
                        val posterUrl = ImageHelper.getPosterUrl(url)

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(posterUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "${movie.title} avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                        )
                    }
                    IconButton(onClick = {
                        viewModel.toggleFavorite(
                            movie.toMovieDomain()
                        )
                    }) {
                        Icon(
                            imageVector = if (isFavorite.contains(movie.id)) Icons.Filled.Favorite
                            else Icons.Outlined.FavoriteBorder,
                            tint = if (isFavorite.contains(movie.id)) Color.Red else Color.Gray,
                            contentDescription = "Favorite"
                        )
                    }

                }
                Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
                movie.tagline?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
                Spacer(modifier = Modifier.height(8.dp))
                movie.overview?.let { Text(it) }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stringResource(R.string.ratings))
                    movie.rating?.let { Text(it.toString()) }
                }
                movie.productionCompanies.forEach { Text(it) }
            }
        }

        is MovieDetailsState.Error -> {
            val message = (state as MovieDetailsState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: $message", color = Color.Red)
            }
        }
    }
}




