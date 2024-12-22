package com.tms.moviedatabase.data.repository

import com.tms.moviedatabase.data.model.toDomainModel
import com.tms.moviedatabase.data.remote.TMDBApi
import com.tms.moviedatabase.domain.model.MovieDetailsDomain
import com.tms.moviedatabase.domain.model.MovieDomain
import com.tms.moviedatabase.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TMDBApi,
    private val apiKey: String
) : MovieRepository {

    override suspend fun getPopularMovies(): List<MovieDomain> {
        return api.getPopularMovies(apiKey).results.map { it.toDomainModel() }
    }

    override suspend fun searchMovies(query: String): List<MovieDomain> {
        return api.searchMovies(apiKey, query).results.map { it.toDomainModel() }
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsDomain {
        return api.getMovieDetails(movieId, apiKey).toDomainModel()
    }
}
