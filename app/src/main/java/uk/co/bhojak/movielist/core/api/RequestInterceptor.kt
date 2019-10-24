package uk.co.bhojak.movielist.core.api


import android.os.Parcel
import android.os.Parcelable
import okhttp3.Interceptor
import okhttp3.Response
import uk.co.bhojak.movielist.BuildConfig

internal class RequestInterceptor() : Interceptor, Parcelable {

    constructor(parcel: Parcel) : this() {
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()
        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RequestInterceptor> {
        override fun createFromParcel(parcel: Parcel): RequestInterceptor {
            return RequestInterceptor(parcel)
        }

        override fun newArray(size: Int): Array<RequestInterceptor?> {
            return arrayOfNulls(size)
        }
    }
}