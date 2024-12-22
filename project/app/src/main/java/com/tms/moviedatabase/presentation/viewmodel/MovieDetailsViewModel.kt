package com.tms.moviedatabase.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.moviedatabase.domain.model.MovieDetailsDomain
import com.tms.moviedatabase.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<MovieDetailsDomain?>(null)
    val movieDetails: StateFlow<MovieDetailsDomain?> = _movieDetails.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                _movieDetails.value = getMovieDetailsUseCase(movieId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}