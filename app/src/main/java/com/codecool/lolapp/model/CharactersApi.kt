package com.codecool.lolapp.model

import io.reactivex.Single
import retrofit2.http.GET

interface CharactersApi {

    @GET("champion.json")
    fun getCharacters(): Single<Response>

}