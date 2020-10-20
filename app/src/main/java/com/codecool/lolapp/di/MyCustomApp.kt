package com.codecool.lolapp.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyCustomApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyCustomApp)
            modules(listOf(charactersRetrofitModule, listingModule, detailsModule, favouritesModule, dbModule))
        }
    }
}