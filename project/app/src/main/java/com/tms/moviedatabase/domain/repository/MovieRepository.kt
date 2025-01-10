package com.tms.moviedatabase.domain.repository

import com.tms.moviedatabase.domain.model.MovieDomain
import com.tms.moviedatabase.domain.model.MovieDetailsDomain

interface MovieRepository {
    suspend fun getPopularMovies(): List<MovieDomain>
    suspend fun searchMovies(query: String): List<MovieDomain>
    suspend fun getMovieDetails(movieId: Int): MovieDetailsDomain
    suspend fun getTopRatedMovies(): List<MovieDomain>
}
