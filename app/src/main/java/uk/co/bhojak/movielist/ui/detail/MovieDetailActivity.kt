package uk.co.bhojak.movielist.ui.detail
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import uk.co.bhojak.movielist.R
import uk.co.bhojak.movielist.core.compose.ViewModelActivity
import uk.co.bhojak.movielist.core.extension.applyToolbarMargin
import uk.co.bhojak.movielist.core.extension.simpleToolbarWithHome
import uk.co.bhojak.movielist.data.model.entity.Movie
import uk.co.bhojak.movielist.databinding.ActivityMovieDetailBinding


class MovieDetailActivity : ViewModelActivity() {

    private val vm by viewModel<MovieDetailViewModel>()
    private val binding by binding<ActivityMovieDetailBinding>(R.layout.activity_movie_detail)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getParcelableExtra(movieId) as Movie
        vm.postMovieId(getMovieFromIntent().id)
        with(binding) {
            lifecycleOwner = this@MovieDetailActivity
            viewModel = vm
            detailBody.viewModel = vm
            movie = getMovieFromIntent()
            detailHeader.movie = getMovieFromIntent()
            detailBody.movie = getMovieFromIntent()
        }
        initializeUI()
        observeMessages()
    }

    private fun initializeUI() {
        applyToolbarMargin(movie_detail_toolbar)
        simpleToolbarWithHome(movie_detail_toolbar, getMovieFromIntent().title)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) onBackPressed()
        return false
    }


    private fun getMovieFromIntent() =
        intent.getParcelableExtra(movieId) as Movie

    private fun observeMessages() =
        this.vm.toastLiveData.observe(this) { toast(it) }

    companion object {
        private const val movieId = "movie"
    }
}
