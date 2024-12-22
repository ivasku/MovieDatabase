package com.tms.moviedatabase.di

import com.tms.moviedatabase.data.remote.TMDBApi
import com.tms.moviedatabase.data.repository.MovieRepositoryImpl
import com.tms.moviedatabase.domain.repository.MovieRepository
import com.tms.moviedatabase.domain.usecase.GetMovieDetailsUseCase
import com.tms.moviedatabase.domain.usecase.GetPopularMoviesUseCase
import com.tms.moviedatabase.domain.usecase.SearchMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: TMDBApi,
        apiKey: String
    ): MovieRepository {
        return MovieRepositoryImpl(api, apiKey)
    }

    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(movieRepository: MovieRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(movieRepository)
    }

    @Provides
    @Singleton
    fun provideSearchMoviesUseCase(movieRepository: MovieRepository): SearchMoviesUseCase {
        return SearchMoviesUseCase(movieRepository)
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailsUseCase(movieRepository: MovieRepository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(movieRepository)
    }
}
