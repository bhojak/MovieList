package uk.co.bhojak.movielist.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.bhojak.movielist.ApiUtil.getCall
import uk.co.bhojak.movielist.MainCoroutinesRule
import uk.co.bhojak.movielist.MockTestUtil.Companion.mockMovieList
import uk.co.bhojak.movielist.core.api.ApiResponse
import uk.co.bhojak.movielist.core.api.client.TheDiscoverClient
import uk.co.bhojak.movielist.core.api.service.TheDiscoverService
import uk.co.bhojak.movielist.core.repository.DiscoverRepository
import uk.co.bhojak.movielist.data.model.entity.Movie
import uk.co.bhojak.movielist.data.model.network.DiscoverMovieResponse
import uk.co.bhojak.movielist.data.room.MovieDao

@ExperimentalCoroutinesApi
class DiscoveryRepositoryTest {

    private lateinit var repository: DiscoverRepository
    private lateinit var client: TheDiscoverClient
    private val service = mock<TheDiscoverService>()
    private val movieDao = mock<MovieDao>()


    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        client = TheDiscoverClient(service)
        repository = DiscoverRepository(client, movieDao)
    }

    @Test
    fun loadMovieListFromNetworkTest() = runBlocking {
        val loadFromDB = movieDao.getMovieList(1)
        whenever(movieDao.getMovieList(1)).thenReturn(loadFromDB)

        val mockResponse = DiscoverMovieResponse(1, emptyList(), 100, 10)
        whenever(service.fetchDiscoverMovie(1)).thenReturn(getCall(mockResponse))

        val data = repository.loadMovies(1) { }
        verify(movieDao, times(2)).getMovieList(1)

        val observer = mock<Observer<List<Movie>>>()
        data.observeForever(observer)
        val updatedData = mockMovieList()
        whenever(movieDao.getMovieList(1)).thenReturn(updatedData)
        data.postValue(updatedData)
        verify(observer).onChanged(updatedData)

        client.fetchDiscoverMovie(1) {
            when (it) {
                is ApiResponse.Success -> {
                    assertEquals(it.data, `is`(mockResponse))
                    assertEquals(it.data?.results, `is`(updatedData))
                }
                else -> assertThat(it, instanceOf(ApiResponse.Failure::class.java))
            }
        }
    }

}
