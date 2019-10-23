package uk.co.bhojak.movielist

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import uk.co.bhojak.movielist.di.networkModule
import uk.co.bhojak.movielist.di.persistenceModule
import uk.co.bhojak.movielist.di.repositoryModule
import uk.co.bhojak.movielist.di.viewModelModule

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(networkModule)
            modules(persistenceModule)
            modules(repositoryModule)
            modules(viewModelModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Stetho.initializeWithDefaults(this)
    }
}
