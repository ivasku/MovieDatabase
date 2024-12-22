package com.tms.moviedatabase.data.remote

import com.tms.moviedatabase.data.model.MovieDetails
import com.tms.moviedatabase.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetails
}
