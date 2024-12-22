package com.tms.moviedatabase.presentation

import com.tms.moviedatabase.domain.model.MovieDomain
import com.tms.moviedatabase.domain.repository.MovieRepository
import com.tms.moviedatabase.domain.usecase.GetPopularMoviesUseCase
import com.tms.moviedatabase.domain.usecase.SearchMoviesUseCase
import com.tms.moviedatabase.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val movieRepository: MovieRepository = mock() // Mock MovieRepository
    private val getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository) // Inject mock repository
    private val searchMoviesUseCase = SearchMoviesUseCase(movieRepository) // Inject mock repository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = HomeViewModel(getPopularMoviesUseCase, searchMoviesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadPopularMovies updates popularMovies state`() = runTest {
        val movies = listOf(
            MovieDomain(1, "Movie 1", "2024-01-01", "/poster1.jpg", 8.5f),
            MovieDomain(2, "Movie 2", "2024-02-01", "/poster2.jpg", 7.0f)
        )

        // Stub the repository to return movies
        whenever(movieRepository.getPopularMovies()).thenReturn(movies)

        viewModel.loadPopularMovies()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(movies, viewModel.popularMovies.first())
    }

    @Test
    fun `searchMovies updates searchResults state`() = runTest {
        val movies = listOf(
            MovieDomain(1, "Search 1", "2024-01-01", "/search1.jpg", 8.0f),
            MovieDomain(2, "Search 2", "2024-02-01", "/search2.jpg", 6.5f)
        )

        // Stub the repository to return search results
        whenever(movieRepository.searchMovies("query")).thenReturn(movies)

        viewModel.searchMovies("query")
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(movies, viewModel.searchResults.first())
    }

    @Test
    fun `loadPopularMovies handles exceptions and updates error state`() = runTest {
        // Stub the repository to throw an exception
        whenever(movieRepository.getPopularMovies()).thenThrow(RuntimeException("Network error"))

        viewModel.loadPopularMovies()
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("Network error", viewModel.error.first())
    }

    @Test
    fun `searchMovies handles exceptions and updates error state`() = runTest {
        // Stub the repository to throw an exception
        whenever(movieRepository.searchMovies(any())).thenThrow(RuntimeException("Search error"))

        viewModel.searchMovies("query")
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("Search error", viewModel.error.first())
    }
}
