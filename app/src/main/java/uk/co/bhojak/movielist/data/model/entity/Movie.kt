package uk.co.bhojak.movielist.data.model.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import uk.co.bhojak.movielist.data.model.Keyword


@Parcelize
@Entity(primaryKeys = [("id")])
data class Movie(
    var page: Int,
    var keywords: List<Keyword>? = ArrayList(),
    val poster_path: String?,
    val adult: Boolean,
    val overview: String,
    val release_date: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val original_title: String,
    val original_language: String,
    val title: String,
    val backdrop_path: String?,
    val popularity: Float,
    val vote_count: Int,
    val video: Boolean,
    val vote_average: Float
): Parcelable
