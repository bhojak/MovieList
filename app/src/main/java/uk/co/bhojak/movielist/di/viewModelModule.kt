package uk.co.bhojak.movielist.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import uk.co.bhojak.movielist.ui.detail.MovieDetailViewModel
import uk.co.bhojak.movielist.ui.main.MainActivityViewModel

val viewModelModule = module {
    viewModel { MainActivityViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }

}