package com.tms.moviedatabase.data.model

import com.tms.moviedatabase.domain.model.MovieDetailsDomain
import com.tms.moviedatabase.domain.model.MovieDomain

fun Movie.toDomainModel(): MovieDomain {
    return MovieDomain(
        id = id,
        title = title,
        releaseDate = release_date,
        posterPath = poster_path ?: "unknown posterPath",
        rating = vote_average
    )
}

fun MovieDetails.toDomainModel(): MovieDetailsDomain {
    return MovieDetailsDomain(
        id = id,
        title = title,
        releaseDate = release_date ?: "unknown release date",
        posterPath = poster_path,
        overview = overview,
        genres = genres.map { it.name },
        rating = vote_average,
        runtime = runtime,
        language = original_language
    )
}
