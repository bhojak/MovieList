package uk.co.bhojak.movielist.di

import org.koin.dsl.module
import uk.co.bhojak.movielist.core.repository.DiscoverRepository
import uk.co.bhojak.movielist.core.repository.MovieRepository

val repositoryModule = module {
    single { DiscoverRepository(get(), get()) }
    single { MovieRepository(get(), get()) }

}