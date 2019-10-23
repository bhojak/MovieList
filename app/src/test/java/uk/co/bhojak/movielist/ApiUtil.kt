package uk.co.bhojak.movielist

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.bhojak.movielist.core.api.ApiResponse

object ApiUtil {
    fun <T : Any> successCall(data: T) = createCall(Response.success(data))

    private fun <T : Any> createCall(response: Response<T>): ApiResponse<T> = ApiResponse.of { response }

    fun <T> getCall(data: T) = object : Call<T> {
        override fun enqueue(callback: Callback<T>) = Unit
        override fun isExecuted() = false
        override fun clone(): Call<T> = this
        override fun isCanceled() = false
        override fun cancel() = Unit
        override fun request(): Request = Request.Builder().build()
        override fun execute(): Response<T> = Response.success(data)
    }
}