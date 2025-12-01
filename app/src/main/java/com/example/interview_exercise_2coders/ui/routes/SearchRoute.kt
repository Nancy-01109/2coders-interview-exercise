package com.example.interview_exercise_2coders.ui.routes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.interview_exercise_2coders.domain.MediaType
import com.example.interview_exercise_2coders.domain.search.SearchItem
import com.example.interview_exercise_2coders.ui.component.SearchElement
import com.example.interview_exercise_2coders.ui.view_model.MoviesViewModel

@Composable
fun SearchRoute(
    viewModel: MoviesViewModel = hiltViewModel(),
    navController: NavController
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val mediaType by viewModel.mediaType.collectAsStateWithLifecycle()
    val searchResults: LazyPagingItems<SearchItem> =
        viewModel.searchResults.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MediaTypeButton(
                text = "Movies",
                selected = mediaType == MediaType.MOVIE
            ) { viewModel.setMediaType(MediaType.MOVIE) }

            MediaTypeButton(
                text = "TV Shows",
                selected = mediaType == MediaType.TV
            ) { viewModel.setMediaType(MediaType.TV) }

        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchResults.itemCount) { item ->
                val result = searchResults[item]
                result?.let { SearchItemRow(it) }
            }

            searchResults.apply {
                when {
                    loadState.append is androidx.paging.LoadState.Loading ||
                            loadState.refresh is androidx.paging.LoadState.Loading -> {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }

                    loadState.append is androidx.paging.LoadState.Error -> {
                        val e = loadState.append as androidx.paging.LoadState.Error
                        item { Text("Error: ${e.error.localizedMessage}", color = Color.Red) }
                    }
                }
            }
        }
    }
}

@Composable
fun MediaTypeButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primary else Color.LightGray,
            contentColor = if (selected) Color.White else Color.Black
        )
    ) {
        Text(text)
    }
}

@Composable
fun SearchItemRow(item: SearchItem) {
    SearchElement(item)
}
