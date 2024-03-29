package uk.co.bhojak.movielist

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import uk.co.bhojak.movielist.core.api.ApiResponse

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("foo")
        val apiResponse = ApiResponse.error<String>(exception)
        assertThat(apiResponse.message, `is`("foo"))
    }

    @Test
    fun success() {
        val apiResponse = ApiResponse.of { Response.success("foo") }
        if (apiResponse is ApiResponse.Success) {
            assertThat(apiResponse.data, `is`("foo"))
        }
    }
}
