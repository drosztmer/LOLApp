package com.codecool.lolapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Details(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("lore")
    val lore: String? = "",
    @SerializedName("tags")
    val tags: Array<String?> = arrayOf<String?>(),
    @SerializedName("allytips")
    val allyTips: Array<String?> = arrayOf<String?>(),
    @SerializedName("enemytips")
    val enemyTips: Array<String?> = arrayOf<String?>(),
    @SerializedName("info")
    val info: DetailsInfo = DetailsInfo(),
    @SerializedName("stats")
    val stats: DetailsStats = DetailsStats(),
    @SerializedName("blurb")
    val blurb: String? = ""
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Details

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

@Parcelize
data class DetailsInfo(
    @SerializedName("attack")
    val attack: Double = 0.0,
    @SerializedName("defense")
    val defense: Double = 0.0,
    @SerializedName("magic")
    val magic: Double = 0.0,
    @SerializedName("difficulty")
    val difficulty: Double = 0.0
) : Parcelable

@Parcelize
data class DetailsStats(
    @SerializedName("hp")
    val hp: Double = 0.0,
    @SerializedName("mp")
    val mp: Double = 0.0,
    @SerializedName("movespeed")
    val moveSpeed: Double = 0.0,
    @SerializedName("attackrange")
    val attackRange: Double = 0.0,
    @SerializedName("attackdamage")
    val attackDamage: Double = 0.0
) : Parcelable

@Parcelize
data class ResponseDetails(
    @SerializedName("type")
    val type: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("version")
    val version: String,
    @SerializedName("data")
    val details: Map<String, Details>
) : Parcelable