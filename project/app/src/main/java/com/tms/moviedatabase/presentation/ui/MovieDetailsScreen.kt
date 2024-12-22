package com.tms.moviedatabase.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.tms.moviedatabase.presentation.viewmodel.MovieDetailsViewModel

@Composable
fun MovieDetailsScreen(viewModel: MovieDetailsViewModel, movieId: Int) {
    val movieDetails by viewModel.movieDetails.collectAsState()
    val error by viewModel.error.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            movieDetails?.let { movie ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.posterPath}"),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 400.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(movie.title, style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(movie.releaseDate)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(movie.overview, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Genres: ${movie.genres.joinToString(", ")}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Rating: ${movie.rating}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Runtime: ${movie.runtime} minutes")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Language: ${movie.language}")
                }
            }
        }
    }

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetails(movieId)
    }
}