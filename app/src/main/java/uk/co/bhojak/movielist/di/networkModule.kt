package uk.co.bhojak.movielist.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.bhojak.movielist.core.api.RequestInterceptor
import uk.co.bhojak.movielist.core.api.client.MovieClient
import uk.co.bhojak.movielist.core.api.client.TheDiscoverClient
import uk.co.bhojak.movielist.core.api.service.MovieService
import uk.co.bhojak.movielist.core.api.service.TheDiscoverService

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(TheDiscoverService::class.java)
    }

    single {
        get<Retrofit>().create(MovieService::class.java)
    }



    single { TheDiscoverClient(get()) }



    single { MovieClient(get()) }


}
