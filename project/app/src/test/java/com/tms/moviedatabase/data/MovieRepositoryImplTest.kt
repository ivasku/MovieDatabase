package com.tms.moviedatabase.data

import com.tms.moviedatabase.data.model.Genre
import com.tms.moviedatabase.data.model.Movie
import com.tms.moviedatabase.data.model.MovieDetails
import com.tms.moviedatabase.data.model.MovieResponse
import com.tms.moviedatabase.data.remote.TMDBApi
import com.tms.moviedatabase.data.repository.MovieRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    private val mockApi = mock(TMDBApi::class.java)
    private val apiKey = "test_api_key"
    private val repository = MovieRepositoryImpl(mockApi, apiKey)

    @Test
    fun `test getPopularMovies returns mapped domain models`() = runBlocking {
        val response = MovieResponse(
            results = listOf(
                Movie(1, "Popular 1", "2024-01-01", "/popular1.jpg", 8.5f),
                Movie(2, "Popular 2", "2024-01-02", "/popular2.jpg", 7.0f)
            )
        )

        `when`(mockApi.getPopularMovies(apiKey)).thenReturn(response)

        val result = repository.getPopularMovies()
        assertEquals(2, result.size)
        assertEquals("Popular 1", result[0].title)
        assertEquals("Popular 2", result[1].title)
    }

    @Test
    fun `test searchMovies returns mapped domain models`() = runBlocking {
        val response = MovieResponse(
            results = listOf(
                Movie(1, "Search 1", "2024-01-01", "/search1.jpg", 8.0f),
                Movie(2, "Search 2", "2024-01-02", "/search2.jpg", 6.5f)
            )
        )

        `when`(mockApi.searchMovies(apiKey, "query")).thenReturn(response)

        val result = repository.searchMovies("query")
        assertEquals(2, result.size)
        assertEquals("Search 1", result[0].title)
        assertEquals("Search 2", result[1].title)
    }

    @Test
    fun `test getMovieDetails returns mapped domain model`() = runBlocking {
        val movieDetails = MovieDetails(
            id = 1,
            title = "Details",
            release_date = "2024-01-01",
            poster_path = "/details.jpg",
            overview = "Details overview",
            genres = listOf(Genre(1, "Action")),
            vote_average = 9.0f,
            runtime = 120,
            original_language = "en"
        )

        `when`(mockApi.getMovieDetails(1, apiKey)).thenReturn(movieDetails)

        val result = repository.getMovieDetails(1)
        assertEquals("Details", result.title)
        assertEquals("Action", result.genres[0])
        assertEquals(120, result.runtime)
    }
}
