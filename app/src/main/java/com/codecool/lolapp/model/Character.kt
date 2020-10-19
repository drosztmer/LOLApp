package com.codecool.lolapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Character(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    var name: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("blurb")
    val blurb: String?
) : Serializable

data class ResponseCharacter(
    @SerializedName("type")
    val type: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("version")
    val version: String,
    @SerializedName("data")
    val characterList: Map<String, Character>
) : Serializable
