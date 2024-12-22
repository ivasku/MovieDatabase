package com.tms.moviedatabase.data.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val release_date: String?,
    val poster_path: String,
    val overview: String,
    val genres: List<Genre>,
    val vote_average: Float,
    val runtime: Int,
    val original_language: String
)
