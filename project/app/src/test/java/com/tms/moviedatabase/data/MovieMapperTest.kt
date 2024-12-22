package com.tms.moviedatabase.data

import com.tms.moviedatabase.data.model.*
import com.tms.moviedatabase.domain.model.MovieDetailsDomain
import com.tms.moviedatabase.domain.model.MovieDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieMapperTest {

    @Test
    fun `test Movie to MovieDomain mapping`() {
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            release_date = "2024-01-01",
            poster_path = "/test.jpg",
            vote_average = 8.5f
        )

        val expected = MovieDomain(
            id = 1,
            title = "Test Movie",
            releaseDate = "2024-01-01",
            posterPath = "/test.jpg",
            rating = 8.5f
        )

        assertEquals(expected, movie.toDomainModel())
    }

    @Test
    fun `test MovieDetails to MovieDetailsDomain mapping`() {
        val movieDetails = MovieDetails(
            id = 1,
            title = "Test Details",
            release_date = "2024-01-01",
            poster_path = "/details.jpg",
            overview = "Test Overview",
            genres = listOf(Genre(1, "Action"), Genre(2, "Comedy")),
            vote_average = 7.5f,
            runtime = 120,
            original_language = "en"
        )

        val expected = MovieDetailsDomain(
            id = 1,
            title = "Test Details",
            releaseDate = "2024-01-01",
            posterPath = "/details.jpg",
            overview = "Test Overview",
            genres = listOf("Action", "Comedy"),
            rating = 7.5f,
            runtime = 120,
            language = "en"
        )

        assertEquals(expected, movieDetails.toDomainModel())
    }
}
