package uk.co.bhojak.movielist.ui.main

import android.view.View
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.skydoves.baserecyclerviewadapter.BaseViewHolder

import kotlinx.android.synthetic.main.item_poster.view.*
import uk.co.bhojak.movielist.core.api.Api
import uk.co.bhojak.movielist.data.model.entity.Movie

class MovieListViewHolder(
    val view: View,
    private val delegate: Delegate
) : BaseViewHolder(view) {

    interface Delegate {
        fun onItemClick(movie: Movie)
    }

    private lateinit var movie: Movie

    @Throws(Exception::class)
    override fun bindData(data: Any) {
        if (data is Movie) {
            movie = data
            drawItem()
        }
    }

    private fun drawItem() {
        itemView.run {
            item_poster_title.text = movie.title
            movie.poster_path?.let {
                Glide.with(context)
                    .load(Api.getPosterPath(it))
                    .listener(GlidePalette.with(Api.getPosterPath(it))
                        .use(BitmapPalette.Profile.VIBRANT)
                        .intoBackground(item_poster_palette)
                        .crossfade(true))
                    .into(item_poster_post)
            }
        }
    }

    override fun onClick(v: View?) = delegate.onItemClick(movie)

    override fun onLongClick(v: View?) = false
}
