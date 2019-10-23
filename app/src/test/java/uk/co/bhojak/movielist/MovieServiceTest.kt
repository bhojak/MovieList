package uk.co.bhojak.movielist


import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import uk.co.bhojak.movielist.core.api.ApiResponse
import uk.co.bhojak.movielist.core.api.async
import uk.co.bhojak.movielist.core.api.service.MovieService
import java.io.IOException

class MovieServiceTest : ApiAbstract<MovieService>() {

    private lateinit var service: MovieService

    @Before
    fun initService() {
        this.service = createService(MovieService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchMovieKeywordsTest() {
        enqueueResponse("/tmdb_movie_keywords.json")
        this.service.fetchKeywords(1).async {
            when (it) {
                is ApiResponse.Success -> {
                    assertThat(it.data?.id, `is`(550))
                    assertThat(it.data?.keywords?.get(0)?.id, `is`(825))
                    assertThat(it.data?.keywords?.get(0)?.name, `is`("support group"))
                }
            }
        }
    }

}
