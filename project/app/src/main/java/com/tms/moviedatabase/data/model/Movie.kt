package com.tms.moviedatabase.data.model

data class Movie(
    val id: Int,
    val title: String,
    val release_date: String,
    val poster_path: String?,
    val vote_average: Float
)
