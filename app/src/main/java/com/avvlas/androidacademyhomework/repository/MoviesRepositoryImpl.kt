package com.avvlas.androidacademyhomework.repository

import android.util.Log
import com.android.academy.fundamentals.homework.data.MovieRepository
import com.avvlas.androidacademyhomework.data.remote.RemoteDataSource
import com.avvlas.androidacademyhomework.model.Movie
import com.avvlas.androidacademyhomework.model.MovieDetails
import com.avvlas.androidacademyhomework.ui.viewstate.ViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MoviesRepositoryImpl private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {

    override suspend fun loadMovies(): Flow<ViewState<List<Movie>>> {
        return safeApiCall { remoteDataSource.loadMovies() }
    }

    override suspend fun loadMovie(movieId: Int): Flow<ViewState<MovieDetails>> {
        return safeApiCall { remoteDataSource.loadMovie(movieId) }
    }

    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Flow<ViewState<T>> =
        flow {
            emit(ViewState.Loading)
            Log.d("view_state", "loading    ")
            try {
                emit(ViewState.Success(apiCall.invoke()))
                Log.d("view_state", "success")
            } catch (throwable: Throwable) {
                emit(ViewState.Error)
                Log.d("view_state", "error")
            }
        }.flowOn(dispatcher)

    companion object {
        private var instance: MovieRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): MovieRepository {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = MoviesRepositoryImpl(remoteDataSource)
                    }
                }
            }
            return instance!!
        }
    }
}