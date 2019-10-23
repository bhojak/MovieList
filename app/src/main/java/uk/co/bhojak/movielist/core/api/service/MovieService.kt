package uk.co.bhojak.movielist.core.api.service


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import uk.co.bhojak.movielist.data.model.network.KeywordListResponse

interface MovieService {
    /**
     * [Movie Keywords](https://developers.themoviedb.org/3/movies/get-movie-keywords)
     *
     * Get the keywords that have been added to a movie.
     *
     * @param [id] Specify the id of movie id.
     *
     * @return [KeywordListResponse] response
     */
    @GET("/3/movie/{movie_id}/keywords")
    fun fetchKeywords(@Path("movie_id") id: Int): Call<KeywordListResponse>

}
