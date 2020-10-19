package com.codecool.lolapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Details(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    var name: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("lore")
    val lore: String?,
    @SerializedName("tags")
    val tags: Array<String?>,
    @SerializedName("allytips")
    val allyTips: Array<String?>,
    @SerializedName("enemytips")
    val enemyTips: Array<String?>,
    @SerializedName("info")
    val info: DetailsInfo,
    @SerializedName("stats")
    val stats: DetailsStats
) : Serializable {

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

data class DetailsInfo(
    @SerializedName("attack")
    val attack: Double,
    @SerializedName("defense")
    val defense: Double,
    @SerializedName("magic")
    val magic: Double,
    @SerializedName("difficulty")
    val difficulty: Double
) : Serializable

data class DetailsStats(
    @SerializedName("hp")
    val hp: Double,
    @SerializedName("mp")
    val mp: Double,
    @SerializedName("movespeed")
    val moveSpeed: Double,
    @SerializedName("attackrange")
    val attackRange: Double,
    @SerializedName("attackdamage")
    val attackDamage: Double
) : Serializable

data class ResponseDetails(
    @SerializedName("type")
    val type: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("version")
    val version: String,
    @SerializedName("data")
    val details: Map<String, Details>
) : Serializable