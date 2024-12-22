package com.tms.moviedatabase.domain.usecase

import com.tms.moviedatabase.domain.model.MovieDetailsDomain
import com.tms.moviedatabase.domain.repository.MovieRepository
import javax.inject.Inject

open class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): MovieDetailsDomain {
        return movieRepository.getMovieDetails(movieId)
    }
}
