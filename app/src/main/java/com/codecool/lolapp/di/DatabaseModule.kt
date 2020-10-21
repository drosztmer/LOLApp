package com.codecool.lolapp.di

import androidx.room.Room
import com.codecool.lolapp.model.data.FavouritesDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {

    single {
        Room.databaseBuilder(androidContext(), FavouritesDatabase::class.java,
            "favourites").build()
    }

    single { get<FavouritesDatabase>().favouriteDao() }

}