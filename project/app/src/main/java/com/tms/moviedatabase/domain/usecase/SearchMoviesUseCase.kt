package com.tms.moviedatabase.domain.usecase

import com.tms.moviedatabase.domain.model.MovieDomain
import com.tms.moviedatabase.domain.repository.MovieRepository
import javax.inject.Inject

open class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(query: String): List<MovieDomain> {
        return movieRepository.searchMovies(query)
    }
}
