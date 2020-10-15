package com.codecool.lolapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Character(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("blurb")
    val blurb: String?,
    @SerializedName("gender")
    val gender: String?
) : Serializable {
    var image: String? = null
}