package uk.co.bhojak.movielist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.bhojak.movielist.ApiUtil
import uk.co.bhojak.movielist.MainCoroutinesRule
import uk.co.bhojak.movielist.MockTestUtil.Companion.mockKeywordList
import uk.co.bhojak.movielist.MockTestUtil.Companion.mockMovie
import uk.co.bhojak.movielist.core.api.client.MovieClient
import uk.co.bhojak.movielist.core.api.service.MovieService
import uk.co.bhojak.movielist.core.repository.MovieRepository
import uk.co.bhojak.movielist.data.model.Keyword
import uk.co.bhojak.movielist.data.model.network.KeywordListResponse
import uk.co.bhojak.movielist.data.room.MovieDao
import uk.co.bhojak.movielist.ui.detail.MovieDetailViewModel

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var repository: MovieRepository
    private val service = mock<MovieService>()
    private val client = MovieClient(service)
    private val movieDao = mock<MovieDao>()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        this.repository = MovieRepository(client, movieDao)
        this.viewModel = MovieDetailViewModel(repository)
    }

    @Test
    fun loadKeywordListFromNetwork() {
        coroutinesRule.runBlockingTest {
            val loadFromDB = mockMovie()
            whenever(movieDao.getMovie(1)).thenReturn(loadFromDB)

            val mockResponse = KeywordListResponse(1, mockKeywordList())
            whenever(service.fetchKeywords(1)).thenReturn(ApiUtil.getCall(mockResponse))

            val data = viewModel.keywordListLiveData
            val observer = mock<Observer<List<Keyword>>>()
            data.observeForever(observer)

            viewModel.postMovieId(1)
            viewModel.postMovieId(1)

            verify(movieDao, atLeastOnce()).getMovie(1)
            verify(service, atLeastOnce()).fetchKeywords(1)
            verify(observer, atLeastOnce()).onChanged(loadFromDB.keywords)
            data.removeObserver(observer)
        }
    }
}
