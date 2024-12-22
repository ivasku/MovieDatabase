package com.tms.moviedatabase.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tms.moviedatabase.domain.model.MovieDomain
import com.tms.moviedatabase.domain.usecase.GetPopularMoviesUseCase
import com.tms.moviedatabase.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _popularMovies = MutableStateFlow<List<MovieDomain>>(emptyList())
    val popularMovies: StateFlow<List<MovieDomain>> = _popularMovies.asStateFlow()

    private val _searchResults = MutableStateFlow<List<MovieDomain>>(emptyList())
    val searchResults: StateFlow<List<MovieDomain>> = _searchResults.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadPopularMovies()
    }

    fun loadPopularMovies() {
        viewModelScope.launch {
            try {
                _isSearching.value = false
                _popularMovies.value = getPopularMoviesUseCase()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                _isSearching.value = true
                _searchResults.value = searchMoviesUseCase(query)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun resetState() {
        _isSearching.value = false
        _searchResults.value = emptyList()
        loadPopularMovies()
    }
}