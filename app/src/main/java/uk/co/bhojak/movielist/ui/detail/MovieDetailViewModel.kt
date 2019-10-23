package uk.co.bhojak.movielist.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import timber.log.Timber
import uk.co.bhojak.movielist.core.compose.DispatchViewModel
import uk.co.bhojak.movielist.core.repository.MovieRepository
import uk.co.bhojak.movielist.data.model.Keyword

class MovieDetailViewModel
constructor(private val movieRepository: MovieRepository) : DispatchViewModel() {

    private val movieIdLiveData: MutableLiveData<Int> = MutableLiveData()
    val keywordListLiveData: LiveData<List<Keyword>>
    val toastLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        Timber.d("Injection MovieDetailViewModel")

        this.keywordListLiveData = movieIdLiveData.switchMap { id ->
            launchOnViewModelScope {
                movieRepository.loadKeywordList(id) { toastLiveData.postValue(it) }
            }
        }
    }

    fun postMovieId(id: Int) = movieIdLiveData.postValue(id)
}
