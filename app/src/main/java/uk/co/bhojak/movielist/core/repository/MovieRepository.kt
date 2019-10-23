package uk.co.bhojak.movielist.core.repository

import androidx.lifecycle.MutableLiveData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import uk.co.bhojak.movielist.core.api.ApiResponse
import uk.co.bhojak.movielist.core.api.client.MovieClient
import uk.co.bhojak.movielist.core.api.message
import uk.co.bhojak.movielist.data.model.Keyword
import uk.co.bhojak.movielist.data.room.MovieDao

class MovieRepository constructor(
    private val movieClient: MovieClient,
    private val movieDao: MovieDao
) : Repository {

    init {
        Timber.d("Injection MovieRepository")
    }

    suspend fun loadKeywordList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
        val liveData = MutableLiveData<List<Keyword>>()
        val movie = movieDao.getMovie(id)
        var keywords = movie.keywords
        if (keywords.isNullOrEmpty()) {
            movieClient.fetchKeywords(id) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        response.data?.let { data ->
                            keywords = data.keywords
                            movie.keywords = keywords
                            liveData.postValue(keywords)
                            movieDao.updateMovie(movie)
                        }
                    }
                    is ApiResponse.Failure.Error -> error(response.message())
                    is ApiResponse.Failure.Exception -> error(response.message())
                }
            }
        }
        liveData.apply { postValue(keywords) }
    }


}
