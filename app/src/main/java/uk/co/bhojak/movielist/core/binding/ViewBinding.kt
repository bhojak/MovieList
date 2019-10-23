package uk.co.bhojak.movielist.core.binding

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import co.lujun.androidtagview.TagContainerLayout
import com.bumptech.glide.Glide
import com.skydoves.whatif.whatIfNotNull
import uk.co.bhojak.movielist.core.api.Api
import uk.co.bhojak.movielist.core.extension.requestGlideListener
import uk.co.bhojak.movielist.core.extension.visible
import uk.co.bhojak.movielist.data.model.Keyword
import uk.co.bhojak.movielist.data.model.entity.Movie
import uk.co.bhojak.movielist.utils.KeywordListMapper

@BindingAdapter("visibilityByResource")
fun bindVisibilityByResource(view: View, anyList: List<Any>?) {
    anyList.whatIfNotNull {
        view.visible()
    }
}

@BindingAdapter("mapKeywordList")
fun bindMapKeywordList(view: TagContainerLayout, keywords: List<Keyword>?) {
    keywords.whatIfNotNull {
        view.tags = KeywordListMapper.mapToStringList(it)
        view.visible()
    }
}


@SuppressLint("SetTextI18n")
@BindingAdapter("bindReleaseDate")
fun bindReleaseDate(view: TextView, movie: Movie) {
    view.text = "Release Date : ${movie.release_date}"
}


@BindingAdapter("bindBackDrop")
fun bindBackDrop(view: ImageView, movie: Movie) {
    movie.backdrop_path.whatIfNotNull(
        whatIf = {
            Glide.with(view.context).load(Api.getBackdropPath(it))
                .listener(view.requestGlideListener())
                .into(view)
        },
        whatIfNot = {
            Glide.with(view.context).load(Api.getBackdropPath(movie.poster_path))
                .listener(view.requestGlideListener())
                .into(view)
        }
    )
}


