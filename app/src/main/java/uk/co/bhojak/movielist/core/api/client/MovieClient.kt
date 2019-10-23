package uk.co.bhojak.movielist.core.api.client


import uk.co.bhojak.movielist.core.api.ApiResponse
import uk.co.bhojak.movielist.core.api.async
import uk.co.bhojak.movielist.core.api.service.MovieService
import uk.co.bhojak.movielist.data.model.network.KeywordListResponse

class MovieClient(private val service: MovieService) {

    fun fetchKeywords(
        id: Int,
        onResult: (response: ApiResponse<KeywordListResponse>) -> Unit
    ) {
        service.fetchKeywords(id).async(onResult)
    }

}
