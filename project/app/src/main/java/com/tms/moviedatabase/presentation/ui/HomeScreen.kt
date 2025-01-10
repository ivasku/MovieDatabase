package com.tms.moviedatabase.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.tms.moviedatabase.presentation.viewmodel.HomeViewModel
import com.tms.moviedatabase.domain.model.MovieDomain
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onMovieClick: (Int) -> Unit
) {
    val popularMovies by viewModel.popularMovies.collectAsState(emptyList())
    val searchResults by viewModel.searchResults.collectAsState(emptyList())
    val isSearching by viewModel.isSearching.collectAsState(false)
    val error by viewModel.error.collectAsState()
    var showTopRated by remember { mutableStateOf(false) }
    val topRatedMovies by viewModel.topRatedMovies.collectAsState(emptyList())

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short
            )
        }

        viewModel.resetState()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            SearchBar(

                onSearch = {
                        query -> viewModel.searchMovies(query) },
                onClear = { viewModel.resetState() }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { showTopRated = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!showTopRated) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Popular Movies")
                }
                Button(
                    onClick = { showTopRated = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (showTopRated) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Top Rated Movies")
                }
            }

            // LazyColumn for movies
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if (isSearching) {
                    // Search Results Section
                    item {
                        Text(
                            "Search Results",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    items(searchResults) { movie ->
                        MovieListItem(movie) {
                            onMovieClick(movie.id)
                        }
                    }
                } else {
                    // Top-Rated Movies Section
                    /* item {
                         if (topRatedMovies.isNotEmpty()) {
                             Text(
                                 "Top Rated Movies",
                                 style = MaterialTheme.typography.headlineSmall,
                                 modifier = Modifier.padding(16.dp)
                             )
                         }
                     }
                     items(topRatedMovies) { movie ->
                         MovieListItem(movie) {
                             onMovieClick(movie.id)
                         }
                     }

                     // Spacer between sections
                     item { Spacer(modifier = Modifier.height(16.dp)) }

                     // Popular Movies Section
                     item {
                         Text(
                             "Popular Movies",
                             style = MaterialTheme.typography.headlineSmall,
                             modifier = Modifier.padding(16.dp)
                         )
                     }
                     items(popularMovies) { movie ->
                         MovieListItem(movie) {
                             onMovieClick(movie.id)
                         }
                     }*/

                    val moviesToDisplay = if (showTopRated) topRatedMovies else popularMovies
                    item {
                        Text(
                            if (showTopRated) "Top Rated Movies" else "Popular Movies",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    items(moviesToDisplay) { movie ->
                        MovieListItem(movie) {
                            onMovieClick(movie.id)
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    onClear: () -> Unit
) {
    var query by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        query = ""
    }

    TextField(
        value = query,
        onValueChange = {
            query = it
            if (query.isEmpty()) {
                onClear()
            } else {
                onSearch(query)
            }
        },
        label = { Text("Search Movies") },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    query = ""
                    onClear()
                }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                }
            }
        }
    )
}

@Composable
fun MovieListItem(movie: MovieDomain, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w200${movie.posterPath}"),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(movie.title, style = MaterialTheme.typography.titleMedium)
            movie.releaseDate?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
        }
    }
}