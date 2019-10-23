package uk.co.bhojak.movielist.core.api.service


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import uk.co.bhojak.movielist.data.model.network.DiscoverMovieResponse

interface TheDiscoverService {
    /**
     * [Movie Discover](https://developers.themoviedb.org/3/discover/movie-discover)
     *
     *  Discover movies by different types of data like average rating, number of votes, genres and certifications.
     *  You can get a valid list of certifications from the  method.
     *
     *  @param [page] Specify the page of results to query.
     *
     *  @return [DiscoverMovieResponse] response
     */
    @GET("/3/discover/movie?language=en&sort_by=popularity.desc")
    fun fetchDiscoverMovie(@Query("page") page: Int): Call<DiscoverMovieResponse>


}
