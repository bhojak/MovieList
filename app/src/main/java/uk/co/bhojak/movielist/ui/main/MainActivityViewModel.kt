package uk.co.bhojak.movielist.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import timber.log.Timber
import uk.co.bhojak.movielist.core.compose.DispatchViewModel
import uk.co.bhojak.movielist.core.repository.DiscoverRepository
import uk.co.bhojak.movielist.data.model.entity.Movie

class MainActivityViewModel
constructor(
    private val discoverRepository: DiscoverRepository

) : DispatchViewModel() {

    private var moviePageLiveData: MutableLiveData<Int> = MutableLiveData()
    val movieListLiveData: LiveData<List<Movie>>



    val toastLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        Timber.d("injection MainActivityViewModel")

        this.movieListLiveData = moviePageLiveData.switchMap { page ->
            launchOnViewModelScope {
                discoverRepository.loadMovies(page) { toastLiveData.postValue(it) }
            }
        }

    }

    fun postMoviePage(page: Int) = moviePageLiveData.postValue(page)

}
