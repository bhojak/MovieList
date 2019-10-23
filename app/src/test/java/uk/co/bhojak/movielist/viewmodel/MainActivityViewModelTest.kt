package uk.co.bhojak.movielist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.bhojak.movielist.ApiUtil
import uk.co.bhojak.movielist.MainCoroutinesRule
import uk.co.bhojak.movielist.MockTestUtil.Companion.mockMovieList
import uk.co.bhojak.movielist.core.api.client.TheDiscoverClient
import uk.co.bhojak.movielist.core.api.service.TheDiscoverService
import uk.co.bhojak.movielist.core.repository.DiscoverRepository
import uk.co.bhojak.movielist.data.model.entity.Movie
import uk.co.bhojak.movielist.data.model.network.DiscoverMovieResponse
import uk.co.bhojak.movielist.data.room.MovieDao
import uk.co.bhojak.movielist.ui.main.MainActivityViewModel

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var discoverRepository: DiscoverRepository
    private val discoverService = mock<TheDiscoverService>()
    private val discoverClient = TheDiscoverClient(discoverService)
    private val movieDao = mock<MovieDao>()




    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        this.discoverRepository = DiscoverRepository(discoverClient, movieDao)
    }

    @Test
    fun loadMovieListFromNetwork() = runBlocking {
        val loadFromDB = emptyList<Movie>()
        whenever(movieDao.getMovieList(1)).thenReturn(loadFromDB)

        val mockResponse = DiscoverMovieResponse(1, mockMovieList(), 100, 10)
        whenever(discoverService.fetchDiscoverMovie(1)).thenReturn(ApiUtil.getCall(mockResponse))

        val data = viewModel.movieListLiveData
        val observer = mock<Observer<List<Movie>>>()
        data.observeForever(observer)

        viewModel.postMoviePage(1)
        viewModel.postMoviePage(1)

        verify(movieDao, atLeastOnce()).getMovieList(1)
        verify(discoverService, atLeastOnce()).fetchDiscoverMovie(1)
        verify(observer, atLeastOnce()).onChanged(loadFromDB)
        data.removeObserver(observer)
    }

}
