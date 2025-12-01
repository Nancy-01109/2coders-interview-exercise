package com.example.interview_exercise_2coders.ui.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.interview_exercise_2coders.R
import com.example.interview_exercise_2coders.navigation.NavigationRoutes
import com.example.interview_exercise_2coders.ui.component.MovieItem
import com.example.interview_exercise_2coders.view_model.MoviesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListRoute(
    viewModel: MoviesViewModel = hiltViewModel(),
    navController: NavController
) {
    val movieChanges = viewModel.movies.collectAsLazyPagingItems()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = {
                    navController.navigate(NavigationRoutes.SearchRoute.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Text(stringResource(R.string.search))
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(movieChanges.itemCount) { index ->
                    val movie = movieChanges[index]
                    movie?.let {
                        MovieItem(
                            movie = it,
                            onClick = {
                                navController.navigate(
                                    NavigationRoutes.MovieDetail.createRoute(
                                        it.id
                                    )
                                )
                            },
                            isFavorite = favoriteIds.contains(it.id),
                            onToggleFavorite = { viewModel.toggleFavorite(it) },
                        )
                    }
                }

                item {
                    if (movieChanges.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                item {
                    val appendState = movieChanges.loadState.append
                    if (appendState is LoadState.Error) {
                        Text(
                            text = "Error: ${appendState.error.localizedMessage}",
                            color = Color.Red,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

