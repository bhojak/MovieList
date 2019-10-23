package uk.co.bhojak.movielist.ui.main

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.SectionRow
import uk.co.bhojak.movielist.R
import uk.co.bhojak.movielist.data.model.entity.Movie


class MovieListAdapter(private val delegate: MovieListViewHolder.Delegate) :
    BaseAdapter() {

    init {
        addSection(ArrayList<Movie>())
    }

    fun addMovieList(movies: List<Movie>) {
        sections()[0].addAll(movies)
        notifyDataSetChanged()
    }

    override fun layout(sectionRow: SectionRow) = R.layout.item_poster

    override fun viewHolder(layout: Int, view: View) = MovieListViewHolder(view, delegate)
}