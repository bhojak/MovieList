package uk.co.bhojak.movielist.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import uk.co.bhojak.movielist.data.room.AppDatabase

val persistenceModule = module {
    single {
        Room
            .databaseBuilder(androidApplication(), AppDatabase::class.java, "TheMovies.db")
            .allowMainThreadQueries()
            .build()
    }

    single { get<AppDatabase>().movieDao() }
}