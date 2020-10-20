package com.codecool.lolapp.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Favourite::class], version = 1)
abstract class FavouritesDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

    companion object {

        @Volatile
        private var INSTANCE: FavouritesDatabase? = null

        fun getInstance(context: Context): FavouritesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FavouritesDatabase::class.java,
                "favourites"
            )
                .build()

    }

}