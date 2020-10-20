package com.codecool.lolapp.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CharacterFavouriteDao {

    @Query("SELECT * FROM favourites WHERE id = :id")
    fun getFavouriteById(id: String): Flowable<CharacterFavourite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(favourite: CharacterFavourite): Completable

    @Query("DELETE FROM favourites WHERE id = :id")
    fun deleteFavourite(id: String)
}