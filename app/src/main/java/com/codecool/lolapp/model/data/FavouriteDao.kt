package com.codecool.lolapp.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites WHERE id = :id")
    fun getFavouriteById(id: String): Single<Favourite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(favourite: Favourite): Completable

    @Query("DELETE FROM favourites WHERE id = :id")
    fun deleteFavourite(id: String)
}