package com.tms.moviedatabase.domain.usecase

import com.tms.moviedatabase.domain.model.MovieDomain
import com.tms.moviedatabase.domain.repository.MovieRepository
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<MovieDomain> {
        return movieRepository.getTopRatedMovies()
    }
}
