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
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.bhojak.movielist.ApiUtil
import uk.co.bhojak.movielist.MainCoroutinesRule
import uk.co.bhojak.movielist.MockTestUtil.Companion.mockKeywordList
import uk.co.bhojak.movielist.MockTestUtil.Companion.mockMovie
import uk.co.bhojak.movielist.core.api.ApiResponse
import uk.co.bhojak.movielist.core.api.client.MovieClient
import uk.co.bhojak.movielist.core.api.service.MovieService
import uk.co.bhojak.movielist.core.repository.MovieRepository
import uk.co.bhojak.movielist.data.model.Keyword
import uk.co.bhojak.movielist.data.model.network.KeywordListResponse
import uk.co.bhojak.movielist.data.room.MovieDao

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private lateinit var repository: MovieRepository
    private lateinit var client: MovieClient
    private val service = mock<MovieService>()
    private val movieDao = mock<MovieDao>()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        client = MovieClient(service)
        repository = MovieRepository(client, movieDao)
    }

    @Test
    fun loadKeywordListFromNetworkTest() = runBlocking {
        val mockResponse = KeywordListResponse(1, emptyList())
        whenever(service.fetchKeywords(1)).thenReturn(ApiUtil.getCall(mockResponse))
        whenever(movieDao.getMovie(1)).thenReturn(mockMovie())

        val data = repository.loadKeywordList(1) { }
        val observer = mock<Observer<List<Keyword>>>()
        data.observeForever(observer)
        verify(movieDao).getMovie(1)

        val loadFromDB = movieDao.getMovie(1)
        data.postValue(loadFromDB.keywords)
        verify(observer, times(2)).onChanged(loadFromDB.keywords)

        val updatedData = mockMovie(keywords = mockKeywordList())
        whenever(movieDao.getMovie(1)).thenReturn(updatedData)
        data.postValue(updatedData.keywords)
        verify(observer).onChanged(updatedData.keywords)

        client.fetchKeywords(1) {
            when (it) {
                is ApiResponse.Success -> {
                    assertEquals(it.data, CoreMatchers.`is`(mockResponse))
                    assertEquals(it.data?.keywords, CoreMatchers.`is`(updatedData.keywords))
                }
                else -> MatcherAssert.assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
            }
        }
    }

}
