package com.codecool.lolapp.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class Favourite (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "blurb")
    val blurb: String?
)