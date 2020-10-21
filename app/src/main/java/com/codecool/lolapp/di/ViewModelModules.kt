package com.codecool.lolapp.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.codecool.lolapp.model.CharactersApi
import com.codecool.lolapp.model.data.FavouriteDao
import com.codecool.lolapp.model.data.FavouritesDatabase
import com.codecool.lolapp.viewmodels.DetailsViewModel
import com.codecool.lolapp.viewmodels.FavouritesViewModel
import com.codecool.lolapp.viewmodels.ListingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.koinApplication
import org.koin.dsl.module

val listingModule = module {
    viewModel {
        ListingViewModel(get())
    }
}

val detailsModule = module {
    viewModel {
        DetailsViewModel(get() as CharactersApi, get() as FavouriteDao)
    }
}

val favouritesModule = module {
    viewModel {
        FavouritesViewModel(get())
    }
}

