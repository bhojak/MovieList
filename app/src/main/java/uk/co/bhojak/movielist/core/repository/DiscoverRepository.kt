package uk.co.bhojak.movielist.core.repository

import androidx.lifecycle.MutableLiveData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import uk.co.bhojak.movielist.core.api.ApiResponse
import uk.co.bhojak.movielist.core.api.client.TheDiscoverClient
import uk.co.bhojak.movielist.core.api.message
import uk.co.bhojak.movielist.data.model.entity.Movie
import uk.co.bhojak.movielist.data.room.MovieDao

class DiscoverRepository constructor(
    private val discoverClient: TheDiscoverClient,
    private val movieDao: MovieDao
) : Repository {

    init {
        Timber.d("Injection DiscoverRepository")
    }

    suspend fun loadMovies(page: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
        val liveDate = MutableLiveData<List<Movie>>()
        var movies = movieDao.getMovieList(page)
        if (movies.isEmpty()) {
            discoverClient.fetchDiscoverMovie(page) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        response.data?.let { data ->
                            movies = data.results
                            movies.forEach { it.page = page }
                            liveDate.postValue(movies)
                            movieDao.insertMovieList(movies)
                        }
                    }
                    is ApiResponse.Failure.Error -> error(response.message())
                    is ApiResponse.Failure.Exception -> error(response.message())
                }
            }
        }
        liveDate.apply { postValue(movies) }
    }


}
