package uk.co.bhojak.movielist.core.api.client

import uk.co.bhojak.movielist.core.api.ApiResponse
import uk.co.bhojak.movielist.core.api.async
import uk.co.bhojak.movielist.core.api.service.TheDiscoverService
import uk.co.bhojak.movielist.data.model.network.DiscoverMovieResponse


class TheDiscoverClient(private val service: TheDiscoverService) {

    fun fetchDiscoverMovie(
        page: Int,
        onResult: (response: ApiResponse<DiscoverMovieResponse>) -> Unit
    ) {
        service.fetchDiscoverMovie(page).async(onResult)
    }


}