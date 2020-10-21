package com.codecool.lolapp.model.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Favourite::class], version = 1)
abstract class FavouritesDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

}