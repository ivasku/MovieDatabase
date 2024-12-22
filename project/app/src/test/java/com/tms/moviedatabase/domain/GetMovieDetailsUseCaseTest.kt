package com.tms.moviedatabase.domain

import com.tms.moviedatabase.domain.model.MovieDetailsDomain
import com.tms.moviedatabase.domain.repository.MovieRepository
import com.tms.moviedatabase.domain.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetMovieDetailsUseCaseTest {

    private val repository = mock<MovieRepository>()
    private val useCase = GetMovieDetailsUseCase(repository)

    @Test
    fun `should return movie details`() = runBlocking {
        val movieDetails = MovieDetailsDomain(
            id = 1,
            title = "Movie Details",
            releaseDate = "2024-01-01",
            posterPath = "/details.jpg",
            overview = "Movie overview",
            genres = listOf("Action", "Comedy"),
            rating = 9.0f,
            runtime = 120,
            language = "en"
        )

        whenever(repository.getMovieDetails(1)).thenReturn(movieDetails)

        val result = useCase(1)
        assertEquals("Movie Details", result.title)
        assertEquals("Action", result.genres[0])
        assertEquals(120, result.runtime)
    }
}
