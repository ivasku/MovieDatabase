package com.tms.moviedatabase.presentation

import com.tms.moviedatabase.domain.model.MovieDetailsDomain
import com.tms.moviedatabase.domain.repository.MovieRepository
import com.tms.moviedatabase.domain.usecase.GetMovieDetailsUseCase
import com.tms.moviedatabase.presentation.viewmodel.MovieDetailsViewModel
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
class MovieDetailsViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val movieRepository: MovieRepository = mock()
    private val getMovieDetailsUseCase = GetMovieDetailsUseCase(movieRepository)
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = MovieDetailsViewModel(getMovieDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadMovieDetails updates movieDetails state`() = runTest {
        val movieDetails = MovieDetailsDomain(
            id = 1,
            title = "Movie Details",
            releaseDate = "2024-01-01",
            posterPath = "/details.jpg",
            overview = "Overview",
            genres = listOf("Action", "Comedy"),
            rating = 9.0f,
            runtime = 120,
            language = "en"
        )

        // Stub the repository to return the expected details
        whenever(movieRepository.getMovieDetails(eq(1))).thenReturn(movieDetails)

        viewModel.loadMovieDetails(1)
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(movieDetails, viewModel.movieDetails.first())
    }

    @Test
    fun `loadMovieDetails handles exceptions and updates error state`() = runTest {
        // Stub the repository to throw an exception
        whenever(movieRepository.getMovieDetails(any())).thenThrow(RuntimeException("Network error"))

        viewModel.loadMovieDetails(1)
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals("Network error", viewModel.error.first())
    }
}
