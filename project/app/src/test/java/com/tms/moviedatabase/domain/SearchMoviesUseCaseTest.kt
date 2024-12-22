package com.tms.moviedatabase.domain

import com.tms.moviedatabase.domain.model.MovieDomain
import com.tms.moviedatabase.domain.repository.MovieRepository
import com.tms.moviedatabase.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SearchMoviesUseCaseTest {

    private val repository = mock<MovieRepository>()
    private val useCase = SearchMoviesUseCase(repository)

    @Test
    fun `should return list of movies for search query`() = runBlocking {
        val movies = listOf(
            MovieDomain(1, "Search Result 1", "2024-01-01", "/poster1.jpg", 8.0f),
            MovieDomain(2, "Search Result 2", "2024-02-01", "/poster2.jpg", 6.5f)
        )

        whenever(repository.searchMovies("query")).thenReturn(movies)

        val result = useCase("query")
        assertEquals(2, result.size)
        assertEquals("Search Result 1", result[0].title)
        assertEquals("Search Result 2", result[1].title)
    }
}
