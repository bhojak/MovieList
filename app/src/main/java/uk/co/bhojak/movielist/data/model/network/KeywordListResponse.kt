package uk.co.bhojak.movielist.data.model.network

import uk.co.bhojak.movielist.data.model.Keyword
import uk.co.bhojak.movielist.data.model.NetworkResponseModel


data class KeywordListResponse(
    val id: Int,
    val keywords: List<Keyword>
) : NetworkResponseModel