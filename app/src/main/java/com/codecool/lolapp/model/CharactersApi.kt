package com.codecool.lolapp.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersApi {

    @GET("champion.json")
    fun getCharacters(): Single<ResponseCharacter>

    @GET("champion/{name}.json")
    fun getDetails(@Path("name") name: String): Single<ResponseDetails>

}