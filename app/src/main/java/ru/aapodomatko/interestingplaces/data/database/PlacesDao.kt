package ru.aapodomatko.interestingplaces.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import ru.aapodomatko.interestingplaces.models.places.Result

@Dao
interface PlacesDao {

    @androidx.room.Query("SELECT * FROM places")
    suspend fun getAllPlaces(): List<Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: Result)

    @Delete
    suspend fun delete(place: Result)

    @Update
    suspend fun update(place: Result)
}