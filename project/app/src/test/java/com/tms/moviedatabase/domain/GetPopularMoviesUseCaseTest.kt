package com.tms.moviedatabase.domain

import com.tms.moviedatabase.domain.model.MovieDomain
import com.tms.moviedatabase.domain.repository.MovieRepository
import com.tms.moviedatabase.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetPopularMoviesUseCaseTest {

    private val repository = mock<MovieRepository>()
    private val useCase = GetPopularMoviesUseCase(repository)

    @Test
    fun `should return list of popular movies`() = runBlocking {
        val movies = listOf(
            MovieDomain(1, "Movie 1", "2024-01-01", "/poster1.jpg", 8.5f),
            MovieDomain(2, "Movie 2", "2024-02-01", "/poster2.jpg", 7.0f)
        )

        whenever(repository.getPopularMovies()).thenReturn(movies)

        val result = useCase()
        assertEquals(2, result.size)
        assertEquals("Movie 1", result[0].title)
        assertEquals("Movie 2", result[1].title)
    }
}
