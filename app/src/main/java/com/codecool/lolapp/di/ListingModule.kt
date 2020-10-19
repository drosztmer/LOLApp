package com.codecool.lolapp.di

import com.codecool.lolapp.viewmodels.DetailsViewModel
import com.codecool.lolapp.viewmodels.ListingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val listingModule = module {
    viewModel {
        ListingViewModel(get())
    }
}

val detailsModule = module {
    viewModel {
        DetailsViewModel(get())
    }
}