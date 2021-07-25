package com.movies.nerses

import android.app.Application
import com.movies.nerses.di.appModule
import com.movies.nerses.di.repoModule
import com.movies.nerses.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}