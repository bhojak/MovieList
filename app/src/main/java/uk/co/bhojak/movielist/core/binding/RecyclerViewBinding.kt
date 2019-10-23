package uk.co.bhojak.movielist.core.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.whatif.whatIfNotNull
import uk.co.bhojak.movielist.data.model.entity.Movie
import uk.co.bhojak.movielist.ui.main.MovieListAdapter

@BindingAdapter("adapterMovieList")
fun bindAdapterMovieList(view: RecyclerView, movies: List<Movie>?) {
    movies.whatIfNotNull {
        val adapter = view.adapter as? MovieListAdapter
        adapter?.addMovieList(it)
    }
}


