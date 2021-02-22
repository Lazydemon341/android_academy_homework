package com.avvlas.androidacademyhomework.repository

import com.android.academy.fundamentals.homework.data.MovieRepository
import com.avvlas.androidacademyhomework.data.remote.RemoteDataSource
import com.avvlas.androidacademyhomework.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl private constructor(private val remoteDataSource: RemoteDataSource) :
    MovieRepository {


    override suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        remoteDataSource.loadMovies()
    }

    override suspend fun loadMovie(id: Int): Movie = withContext(Dispatchers.IO){
        remoteDataSource.loadMovie(id)
    }

    companion object {
        private var instance: MovieRepository? = null

        fun getInstance(remoteDataSource : RemoteDataSource): MovieRepository {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = MovieRepositoryImpl(remoteDataSource)
                    }
                }
            }
            return instance!!
        }
    }
}