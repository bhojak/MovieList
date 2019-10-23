package uk.co.bhojak.movielist.data.model.network


import uk.co.bhojak.movielist.data.model.NetworkResponseModel
import uk.co.bhojak.movielist.data.model.entity.Movie

data class DiscoverMovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
) : NetworkResponseModel