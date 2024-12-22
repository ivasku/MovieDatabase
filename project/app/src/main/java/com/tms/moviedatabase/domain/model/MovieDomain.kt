package com.tms.moviedatabase.domain.model

data class MovieDomain(
    val id: Int,
    val title: String,
    val releaseDate: String?,
    val posterPath: String?,
    val rating: Float
)