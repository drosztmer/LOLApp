package com.codecool.lolapp.di

import com.codecool.lolapp.model.CharactersApi
import com.codecool.lolapp.util.BASE_URL_CHARACTER
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val charactersRetrofitModule = module {
    single { okHttp() }

    single { retrofit(BASE_URL_CHARACTER) }

    single {
        get<Retrofit>().create(CharactersApi::class.java)
    }
}

private fun okHttp() = OkHttpClient.Builder()
    .build()

private fun retrofit(baseUrl: String) = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()