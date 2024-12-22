package com.tms.moviedatabase.domain.model

data class MovieDetailsDomain(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
    val overview: String,
    val genres: List<String>,
    val rating: Float,
    val runtime: Int,
    val language: String
)