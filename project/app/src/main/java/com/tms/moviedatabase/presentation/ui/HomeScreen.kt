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

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onMovieClick: (Int) -> Unit
) {
    val popularMovies by viewModel.popularMovies.collectAsState(emptyList())
    val searchResults by viewModel.searchResults.collectAsState(emptyList())
    val isSearching by viewModel.isSearching.collectAsState(false)
    val error by viewModel.error.collectAsState()

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
            modifier = Modifier.padding(paddingValues)
        ) {
            SearchBar(
                onSearch = { query -> viewModel.searchMovies(query) },
                onClear = { viewModel.loadPopularMovies() }
            )
            LazyColumn {
                items(if (isSearching) searchResults else popularMovies) { movie ->
                    MovieListItem(movie) {
                        onMovieClick(movie.id)
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